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

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;

public class RegistrationActivity extends AppCompatActivity {
    EditText firstname;
    EditText lastname;
    EditText email;
    EditText password;
    //EditText confirmpassowrd;
    Button Register;
    boolean blnValidateInput=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstname = findViewById(R.id.first_name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        //confirmpassword = findViewById(R.id.loginconfirmPassword);
        Register = findViewById(R.id.btnregister2);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateInformation();
                //This checks if our validation passed and sends the data to the database
//                if(blnValidateInput==true){
//                    Toast.makeText(RegistrationActivity.this, "Lets send them to the DB", Toast.LENGTH_SHORT).show();
//                    PHPrequest phPrequest=new PHPrequest();
//                    phPrequest.doRequest(RegistrationActivity.this, "register.php", new RequestHandler(){
//
//                        // do request
//                        @Override
//                        public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
//                            // override method to pass relevant parameters
//                            //Phumlani
//                            urlBuilder.addQueryParameter("email",
//                                    email.getText().toString().trim());
//                            urlBuilder.addQueryParameter("password",
//                                    password.getText().toString().trim());
//
//                            return urlBuilder;
//                        }
//
//                        @Override
//                        public void processResponse(String responseFromRequest) {
//                            // override method to process response from server
//                            useResponse(responseFromRequest);
//                        }
//                    });
//                }
            }
        });
    }
    private void useResponse(String response) {
        // php successfully connected to db and found user with correct detail
        if (response.equals("Registration successful! :) ")) {
            // alert user
            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();

            // finish current activity
            finish();

            // create and intent and goto activity
            Intent i = new Intent(RegistrationActivity.this, Homepage.class);
            startActivity(i);
        }
        else {
            // alert user about error
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
    //This function checks if there is anything left blank or not.
    private void ValidateInformation() {
        int errorcounter=0;
        if (isEmpty(firstname)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            errorcounter++;
        }
        if (isEmpty(lastname)) {
            lastname.setError("Last name is required!");
            errorcounter++;
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            errorcounter++;
        }
        if (isEmpty(password)) {
            password.setError("Please create your log in password");
            errorcounter++;
        }
        if(errorcounter==0) {
            blnValidateInput = true;
        }

    }
    //This checks if there is any text edit has been left blank
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    //this one helps us to check for a valid email
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}