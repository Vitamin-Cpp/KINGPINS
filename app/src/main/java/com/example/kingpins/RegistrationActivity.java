package com.example.kingpins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import okhttp3.HttpUrl;

public class RegistrationActivity extends AppCompatActivity {
    EditText etfirstname;
    EditText etlastname;
    EditText etEmail;
    EditText etPassword;
    private TextInputLayout ilEmail , ilPassword,ilFirstname, ilLastname;
    //EditText confirmpassowrd;
    Button Register;
    boolean blnValidateInput=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ilEmail = findViewById(R.id.layout_loginEmail);
        ilPassword = findViewById(R.id.layout_loginPassword);
        ilFirstname=findViewById(R.id.layout_firstname);
        ilLastname=findViewById(R.id.layout_lastname);
        etfirstname = findViewById(R.id.first_name);
        etlastname = findViewById(R.id.lastname);
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        //confirmpassword = findViewById(R.id.loginconfirmPassword);
        Register = findViewById(R.id.btnRegister);


    }
    // onclick for register button
    public void doRegister(View view)
    {
        // count error from user input
        int errors = 0;

        // validate email
        if(TextUtils.isEmpty(etEmail.getText()))
        {
            etEmail.setError("Required field!");
            //etEmail.addTextChangedListener(new OurTextWatcher(ilEmail));
            errors++;
        }
        //validate names
        if(TextUtils.isEmpty(etfirstname.getText()))
        {
            etfirstname.setError("Required field!");
            //etEmail.addTextChangedListener(new OurTextWatcher(ilEmail));
            errors++;
        }

        if(TextUtils.isEmpty(etlastname.getText()))
        {
            etlastname.setError("Required field!");
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

        // if mo errors were detected
        if(errors == 0)
        {
            // make new php request object
            PHPrequest phPrequest=new PHPrequest();
            phPrequest.doRequest(RegistrationActivity.this, "Registration.php", new RequestHandler() {

                // do request
                @Override
                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
                {
                    // override method to pass relevant parameters
                    urlBuilder.addQueryParameter("email",
                            etEmail.getText().toString().trim());
                    urlBuilder.addQueryParameter("password",
                            etPassword.getText().toString().trim());
                    urlBuilder.addQueryParameter("firstname",
                            etfirstname.getText().toString().trim());
                    urlBuilder.addQueryParameter("lastname",
                            etlastname.getText().toString().trim());

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

    private void useResponse(String response) {
        // php successfully connected to db and found user with correct detail
        if (response.equals("You are registered")) {
            // alert user
            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();

            // finish current activity
            finish();

            // create and intent and goto activity
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
        }else if(response.equals("User already exist")){
            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
            finish();
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
        }
        else {
            // alert user about error
            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);

            alertDialogBuilder.setTitle("Unknown Error!");
            alertDialogBuilder.setMessage("Something unexpected happened, you can try again.");
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

}