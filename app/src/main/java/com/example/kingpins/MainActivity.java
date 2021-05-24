package com.example.kingpins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity {
    private Button Btnlogin;
    private static final int RC_SIGN_IN =9001 ;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    private TextInputEditText etEmail , etPassword;
    private TextInputLayout ilEmail , ilPassword;
    private Button btnLogin , btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        ilEmail = findViewById(R.id.layout_loginEmail);
        ilPassword = findViewById(R.id.layout_loginPassword);
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        Btnlogin = findViewById(R.id.btnGoogleLogin);
        mAuth=FirebaseAuth.getInstance();

        // login with google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        //go to registration activity
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    // validate input
    public int validationErrors(){
        // count error from user input
        int errors = 0;

        // validate email
        if(TextUtils.isEmpty(etEmail.getText()))
        {
            etEmail.setError("Required field!");
            //etEmail.addTextChangedListener(new OurTextWatcher(ilEmail));
            errors++;
        }

        // validate password
        if(TextUtils.isEmpty(etPassword.getText()))
        {
            etPassword.setError("Required field!");
            //etPassword.addTextChangedListener(new OurTextWatcher(ilPassword));
            errors++;
        }
        return errors;
    }

    // onclick for login button
    public void doLogin(View view)
    {

        int errors = validationErrors();
        // if no errors were detected
        if(errors == 0)
        {
            // make new php request object
            PHPrequest phPrequest=new PHPrequest();
            phPrequest.doRequest(MainActivity.this, "login.php", new RequestHandler() {

                // do request
                @Override
                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
                {
                    // override method to pass relevant parameters
                    urlBuilder.addQueryParameter("email",
                            etEmail.getText().toString().trim());
                    urlBuilder.addQueryParameter("password",
                            etPassword.getText().toString().trim());

                    return urlBuilder;
                }

                @Override
                public void processResponse(String responseFromRequest)
                {
                    // override method to process response from server
                    useResponse(responseFromRequest);
                }
            });

        }
    }

    // use response from server
    // do something with response from server
    private void useResponse(String response)
    {
        // php successfully connected to db and found user with correct detail
        if(!response.equals("Invalid login details!"))
        {

            // store user data
            try{
                JSONObject json = new JSONObject(response);
                Constants.USER_EMAIL = json.getString("email");
                Constants.USER_FIRST_NAME = json.getString("firstname");
                Constants.USER_LAST_NAME = json.getString("lastname");
                Constants.USER_FUNDS = json.getString("funds");

                // alert user
                Toast.makeText(MainActivity.this,"Login Approved",Toast.LENGTH_LONG).show();

                // finish current activity
                finish();

                // create and intent and goto activity
                Intent i = new Intent(MainActivity.this,Homepage.class);
                startActivity(i);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        // user does not exits or provided incorrect details
        else {
            // alert user about input error
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle(response);
            alertDialogBuilder.setMessage("Email or Password was incorrect.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    etPassword.setText("");
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,user.getEmail() +" "+ user.getDisplayName(),Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }







    private void updateUI(FirebaseUser user) {
        finish();
        Intent intent=new Intent(MainActivity.this,Homepage.class);
        startActivity(intent);
    }
}