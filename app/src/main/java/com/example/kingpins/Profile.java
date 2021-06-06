package com.example.kingpins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button  btnChangePassword, btnChangeEmail;
    private TextView tvFirstName, tvLastName, tvFunds, tvEmail;
    private String newPassword = "", newEmail = "";
    //Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        btnChangeEmail = findViewById(R.id.btnChangeEmail);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        tvFirstName = findViewById(R.id.txtFirstName);
        tvLastName = findViewById(R.id.txtLastName);
        tvFunds = findViewById(R.id.txtFunds);
        tvEmail = findViewById(R.id.tvEmail);

       // myDialog = new Dialog(this);

        String value = "R " + Constants.USER_FUNDS;
        tvFunds.setText(value);
        tvFirstName.setText(Constants.USER_FIRST_NAME);
        tvLastName.setText(Constants.USER_LAST_NAME);
        tvEmail.setText(Constants.USER_EMAIL);

        btnChangeEmail.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter new email address");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            builder.setView(input);
            builder.setCancelable(false);
            // Set up the buttons
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newEmail = input.getText().toString().trim();
                    if(newEmail.equals(""))
                    {
                        input.setError("Required field!");
                    }
                    else
                    {
                        updateEmail();
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });


        btnChangePassword.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter new password");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);
            builder.setCancelable(false);
            // Set up the buttons
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newPassword = input.getText().toString().trim();
                    if(newPassword.equals(""))
                    {
                        input.setError("Required field!");
                    }
                    else
                    {
                        updatePassword();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

    }
    public void showPopup(View v) {
        Intent intent =new Intent(getBaseContext(),custompopup.class);
        intent.putExtra("Seller",Constants.USER_EMAIL);
        startActivity(intent);

        Homepage.redirectActivity(this,custompopup.class);

//        TextView txtclose;
//        Button btnUpload;
//        //myDialog.setContentView(R.layout.activity_custompopup);
//        txtclose =(TextView) custompopup.findViewById(R.id.txtclose);
//        txtclose.setText("X");
//        btnUpload = (Button) myDialog.findViewById(R.id.btnUpload);
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//     myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
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
                        newEmail);

                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest)
            {
                if(responseFromRequest.equals("Email Updated"))
                {
                    tvEmail.setText(newEmail);
                    Constants.USER_EMAIL = newEmail;
                    Toast.makeText(Profile.this,responseFromRequest,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Profile.this,"Failed to update email!",Toast.LENGTH_LONG).show();
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
                        newPassword);

                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest)
            {
                if(responseFromRequest.trim().equals("Password Updated"))
                {
                    Toast.makeText(Profile.this,responseFromRequest.trim(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Profile.this,"Failed to update password!",Toast.LENGTH_LONG).show();
                }
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
    public void clickBalance(View view){
        Homepage.redirectActivity(this,Balance.class);
    }//redirect the activity to about us
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