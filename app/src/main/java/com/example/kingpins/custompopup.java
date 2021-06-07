package com.example.kingpins;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import okhttp3.HttpUrl;


public class custompopup extends AppCompatActivity {

    private ImageView selectedImage;
    private TextView txtclose;
    public EditText productName, productPrice, category,Email;
    private Button btnUpload;
    private RadioButton radProduct,radService;
    // Activity getApplicationContext();

    private Bitmap bitmap;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "productName";
    private String KEY_PRICE = "productPrice";
    private String KEY_CATEGORY = "category";


    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    View rootView;
    private String UPLOAD_URL ="https://lamp.ms.wits.ac.za/home/s2280727/kingpins/upload_product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custompopup);
        selectedImage = findViewById(R.id.selectediImage);
        txtclose = (TextView) findViewById(R.id.txtclose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        category = findViewById(R.id.category);
        radProduct = (RadioButton) findViewById(R.id.rdProduct);
        radService = (RadioButton)findViewById(R.id.rdService);
        Email= findViewById(R.id.Email);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homepage.redirectActivity(custompopup.this, Profile.class);
            }
        });

        //this is the button we will use to select an image

        findViewById(R.id.button_choose_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(custompopup.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_SELECT_IMAGE
                    );
                }else {
                    selectImage();
                }


            }
        });
        //this is the button we use to send the image and all the contents of our add to the database
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //we send stuff for storage on the database
//                if (bitmap==null)
//                {
//                    Toast.makeText(getApplicationContext(),"Please Upload Image",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    PHPrequest phPrequest=new PHPrequest();
//                    phPrequest.doRequest(custompopup.this, "upload_product.php", new RequestHandler() {
//
//                        // do request
//                        @Override
//                        public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
//                        {
//
//                            String image = getStringImage(bitmap);
//
//                            //Getting Image Name
//                            String name1 = productName.getText().toString().trim();
//                            String category1 = category.getText().toString().trim();
//                            String producprice1 = productPrice.getText().toString().trim();
//                            // override method to pass relevant parameters
//                            urlBuilder.addQueryParameter(KEY_IMAGE, image);
//                            urlBuilder.addQueryParameter(KEY_NAME, name1);
//                            urlBuilder.addQueryParameter(KEY_PRICE,producprice1);
//                            urlBuilder.addQueryParameter(KEY_CATEGORY,category1);
//                            urlBuilder.addQueryParameter("seller", "Phumlani@Ntini.com");
//
//                            return urlBuilder;
//                        }
//
//                        @Override
//                        public void processResponse(String responseFromRequest)
//                        {
//                            // override method to process response from server
//                            useResponse(responseFromRequest);
//                        }
//                    });
//
//                }
//
//
//
//            }
//
//
//        });


    }

    public void doUpload(View view)
    {
        if(radProduct.isChecked()){
        if (bitmap==null)
        {
            Toast.makeText(getApplicationContext(),"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        else {
            PHPrequest phPrequest=new PHPrequest();
            phPrequest.doRequest(custompopup.this, "upload_product.php", new RequestHandler() {

                // do request
                @Override
                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
                {

                   // String image =getStringImage(bitmap);

                    //Getting Image Name
                    String name1 = productName.getText().toString().trim();
                    String category1 = category.getText().toString().trim();
                    String producprice1 = productPrice.getText().toString().trim();
                    // override method to pass relevant parameters
                    //urlBuilder.addQueryParameter(KEY_IMAGE, image);
                    urlBuilder.addQueryParameter(KEY_NAME, name1);
                    urlBuilder.addQueryParameter(KEY_PRICE,producprice1);
                    urlBuilder.addQueryParameter(KEY_CATEGORY,category1);
                    Intent intent=getIntent();

                    urlBuilder.addQueryParameter("seller", Email.getText().toString().trim());

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
        }else if(radService.isChecked()){
            PHPrequest phPrequest=new PHPrequest();
            phPrequest.doRequest(custompopup.this, "upload_service.php", new RequestHandler() {

                // do request
                @Override
                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
                {

                    // String image =getStringImage(bitmap);

                    //Getting Image Name
                    String name1 = productName.getText().toString().trim();
                    String category1 = category.getText().toString().trim();
                    String producprice1 = productPrice.getText().toString().trim();
                    // override method to pass relevant parameters
                    //urlBuilder.addQueryParameter(KEY_IMAGE, image);
                    urlBuilder.addQueryParameter(KEY_NAME, name1);
                    urlBuilder.addQueryParameter(KEY_PRICE,producprice1);
                    urlBuilder.addQueryParameter(KEY_CATEGORY,category1);
                    Intent intent=getIntent();

                    urlBuilder.addQueryParameter("seller",  Email.getText().toString().trim());

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE &&  resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        selectedImage.setImageBitmap(bitmap);


                        //the file path of the selected image
                        //File selectedImagefile= new File(getPathFromUri(selectedImageUri));

                    }catch (Exception exception){
                        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void selectImage(){
        selectedImage.setImageBitmap(null);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }
    }

    private void useResponse(String response) {
        // php successfully connected to db and found user with correct detail
        if (response.equals("Successfully Uploaded")) {
            // alert user
            Toast.makeText(custompopup.this, response, Toast.LENGTH_LONG).show();

            // finish current activity
            finish();

            // create and intent and goto activity
            Intent i = new Intent(custompopup.this, Profile.class);
            startActivity(i);
        }else if(response.equals("Error")){
            Toast.makeText(custompopup.this, "Please try again", Toast.LENGTH_LONG).show();
            finish();

        }
        else {
            // alert user about error
            Toast.makeText(custompopup.this, response, Toast.LENGTH_LONG).show();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(custompopup.this);

            alertDialogBuilder.setTitle("Unknown Error!");
            alertDialogBuilder.setMessage("Something unexpected happened, you can try again.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //etPassword.setText("");
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();


        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }




}