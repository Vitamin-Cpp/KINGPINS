package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button btnSaveChanges, btnChangePassword, btnChangeEmail;
    private TextView tvFirstName, tvLastName, tvFunds;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        btnSaveChanges = findViewById(R.id.btnSave);
        btnChangeEmail = findViewById(R.id.btnChangeEmail);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvFirstName = findViewById(R.id.txtFirstName);
        tvLastName = findViewById(R.id.txtLastName);
        tvFunds = findViewById(R.id.txtFunds);

        tvFirstName.setText(Constants.USER_FIRST_NAME);
        tvLastName.setText(Constants.USER_LAST_NAME);
        tvFunds.setText(Constants.USER_FUNDS);
        etEmail.setText(Constants.USER_EMAIL);

        btnChangeEmail.setOnClickListener(v -> {
            etEmail.setEnabled(true);
            etEmail.requestFocus();
        });

        btnChangePassword.setOnClickListener(v -> {
            etPassword.setEnabled(true);
            etPassword.requestFocus();
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().trim().equals(""))
                {
                    etEmail.setError("Required field!");
                }
                else
                {
                    updateEmail();
                }
                if(!etPassword.getText().toString().trim().equals(""))
                {
                    updatePassword();
                }

            }
        });
    }

    public void updateEmail()
    {
        // make new php request object
        PHPrequest phPrequest=new PHPrequest();
        phPrequest.doRequest(Profile.this, "update_email.php", new RequestHandler() {

            // do request
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
            {
                // override method to pass relevant parameters
                urlBuilder.addQueryParameter("email",
                        Constants.USER_EMAIL);
                urlBuilder.addQueryParameter("emailNew",
                        etEmail.getText().toString().trim());

                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest)
            {
                // override method to process response from server
                Toast.makeText(Profile.this,responseFromRequest,Toast.LENGTH_LONG).show();
                if(responseFromRequest.equals("Email Updated"))
                {
                    Constants.USER_EMAIL = etEmail.getText().toString().trim();
                }
            }
        });
    }

    public void updatePassword()
    {
        // make new php request object
        PHPrequest phPrequest=new PHPrequest();
        phPrequest.doRequest(Profile.this, "update_password.php", new RequestHandler() {

            // do request
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
            {
                // override method to pass relevant parameters
                urlBuilder.addQueryParameter("email",
                        Constants.USER_EMAIL);
                urlBuilder.addQueryParameter("passwordNew",
                        etPassword.getText().toString().trim());

                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest)
            {
                // override method to process response from server
                Toast.makeText(Profile.this,responseFromRequest,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ClickMenu(View view){
        //open the drawer
        Homepage.openDrawer(drawerLayout);
    }
    public void clickLogo(View view){
        // clicking the logo closes the drawer
        Homepage.closeDrawer(drawerLayout);
    }
    public void clickHome(View view){
        //redirect the activity to the homepage
        Homepage.redirectActivity(this, Homepage.class);
    }
    public void clickProfile(View view){
        recreate();
    }
    public void clickMarketPlace(View view){
        Homepage.redirectActivity(this,MarketPlace.class);
    }
    public void clickDashboard(View view){
        Homepage.redirectActivity(this, Dashboard.class);
    }
    public void clickWishList(View view){
        Homepage.redirectActivity(this, WishList.class);
    }
    public void clickAboutUs(View view){
        //here we redirect the activity to about us
        Homepage.redirectActivity(this,AboutUs.class);
    }
    public void clickLogOut(View view){
        //we log out
        Homepage.logOut(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //close the drawer
        Homepage.closeDrawer(drawerLayout);
    }

}