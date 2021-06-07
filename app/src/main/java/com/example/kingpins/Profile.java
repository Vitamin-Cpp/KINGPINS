package com.example.kingpins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button  btnChangePassword, btnChangeEmail, btnAddImage, btnSaveChanges;
    private TextView tvFirstName, tvLastName, tvFunds, tvEmail;
    private String newPassword = "", newEmail = "",profilePicUrl="";
    ImageView profilePic;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    //Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        profilePicUrl=Constants.URL_LINK+"images/"+Constants.USER_FIRST_NAME+Constants.USER_LAST_NAME+".JPG";
        btnChangeEmail = findViewById(R.id.btnChangeEmail);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnAddImage=findViewById(R.id.addImage);
        btnSaveChanges=findViewById(R.id.saveChanges);

        tvFirstName = findViewById(R.id.txtFirstName);
        tvLastName = findViewById(R.id.txtLastName);
        tvFunds = findViewById(R.id.txtFunds);
        tvEmail = findViewById(R.id.tvEmail);

        profilePic=findViewById(R.id.profilePic);
       // myDialog = new Dialog(this);

        String value = "R " + Constants.USER_FUNDS;
        tvFunds.setText(value);
        tvFirstName.setText(Constants.USER_FIRST_NAME);
        tvLastName.setText(Constants.USER_LAST_NAME);
        tvEmail.setText(Constants.USER_EMAIL);

        Glide.with(Profile.this)
                  .load("https://lamp.ms.wits.ac.za/home/s2280727/kingpins/images/abcabc.JPG")
                  .into(profilePic);


        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Profile.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                }else {
                    selectImage();
                }
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();

                //Toast.makeText(Profile.this,"image saved",Toast.LENGTH_LONG).show();

            }
        });

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


    private void selectImage() {
        //clear previous data
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length==PackageManager.PERMISSION_GRANTED && grantResults.length > 0){
            //when permission is granted
            //call method
            selectImage();
        }
        else{
            //when permission is denied
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check condition
        if(requestCode==REQUEST_CODE_SELECT_IMAGE && resultCode==RESULT_OK && data!= null){
            //When result is ok
            //Initialize uri
            Uri selectedImageUri= data.getData();
            if(selectedImageUri != null){
                try {
                    InputStream inputStream= getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    profilePic.setImageBitmap(bitmap);
                }catch (Exception exception){
                    Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private String imageToString()
    {
        profilePic.buildDrawingCache();
        Bitmap bitmap=((BitmapDrawable) profilePic.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] image=stream.toByteArray();
        return Base64.encodeToString(image,Base64.DEFAULT);
    }

    public void addImage()
    {
        // make new php request object
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_LINK + "add_image.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Constants.USER_IMAGE="yes";
                    Toast.makeText(Profile.this, "image Successfully saved", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("email", Constants.USER_EMAIL);
                params.put("firstname", Constants.USER_FIRST_NAME);
                params.put("lastname", Constants.USER_LAST_NAME);
                params.put("image", imageToString());
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close the drawer
        Homepage.closeDrawer(drawerLayout);
    }

}