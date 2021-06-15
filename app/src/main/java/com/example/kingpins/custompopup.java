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
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.*;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

//import static constants_classes.Constants.USER_EMAIL;


public class custompopup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView imageView;
    private Spinner category;
    private TextView txtclose;
    public EditText productName, productPrice;
    private Button btnUpload;
    private RadioButton radProduct,radService;
    // Activity getApplicationContext();

    private String choiceOfCategory="";



    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    View rootView;
    private String UPLOAD_URL ="https://lamp.ms.wits.ac.za/home/s2280727/kingpins/upload_product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custompopup);
        imageView=  findViewById(R.id.selectediImage);
        txtclose = (TextView) findViewById(R.id.txtclose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        category = findViewById(R.id.spCategory);
        radProduct = (RadioButton) findViewById(R.id.rdProduct);
        radService = (RadioButton)findViewById(R.id.rdService);



        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homepage.redirectActivity(custompopup.this, Profile.class);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        //this is the button we will use to select an image

        findViewById(R.id.button_choose_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    //When permission denied
                    //request permission
                    ActivityCompat.requestPermissions(custompopup.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }
                else {
                    //when permission grated
                    //create method
                    selectImage();
                }


            }
        });
        Spinner spinner = findViewById(R.id.spCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



    }


    private void selectImage() {
        //clear previous data
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choiceOfCategory= parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length== PackageManager.PERMISSION_GRANTED && grantResults.length > 0){
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
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
                    imageView.setImageBitmap(bitmap);
                }catch (Exception exception){
                    Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private void upload(){
        StringRequest request= new StringRequest(Request.Method.POST, Constants.URL_LINK + "upload_product.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("image", imageToString());
                params.put("productName",productName.getText().toString());
                params.put("category", choiceOfCategory);
                params.put("seller", Constants.USER_EMAIL);
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private String imageToString()
    {
        imageView.buildDrawingCache();
        Bitmap bitmap=imageView.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] image=stream.toByteArray();
        return Base64.encodeToString(image,0);
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

    //    public void doUpload(View view)
//    {
//        if(choiceOfCategory.isEmpty()||choiceOfCategory.equals("Category")) {
//            Toast.makeText(getApplicationContext(), "Please select category", Toast.LENGTH_LONG).show();
//        }else if(!radService.isChecked()&&!radProduct.isChecked())
//        {
//            Toast.makeText(getApplicationContext(), "Please select Service or Product", Toast.LENGTH_LONG).show();
//        }
//        else{
//        if (radProduct.isChecked()) {
//
//            PHPrequest phPrequest = new PHPrequest();
//            phPrequest.doRequest(custompopup.this, "upload_product.php", new RequestHandler() {
//
//                // do request
//                @Override
//                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
//
//                    String image = imageToString();
//
//
//                    String name1 = productName.getText().toString().trim();
//
//                    String producprice1 = productPrice.getText().toString().trim();
//                    // override method to pass relevant parameters
//                    urlBuilder.addQueryParameter(KEY_IMAGE, image);
//                    urlBuilder.addQueryParameter(KEY_NAME, name1);
//                    urlBuilder.addQueryParameter(KEY_PRICE, producprice1);
//                    urlBuilder.addQueryParameter(KEY_CATEGORY,choiceOfCategory);
//                    Intent intent = getIntent();
//
//                    urlBuilder.addQueryParameter("seller",Constants.USER_EMAIL);
//
//                    return urlBuilder;
//                }
//
//                @Override
//                public void processResponse(String responseFromRequest) {
//                    // override method to process response from server
//                    useResponse(responseFromRequest);
//                }
//            });
//            //saveChanges();
//
//
//        } else if (radService.isChecked()) {
//            PHPrequest phPrequest = new PHPrequest();
//            phPrequest.doRequest(custompopup.this, "upload_service.php", new RequestHandler() {
//
//                // do request
//                @Override
//                public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
//
//                    // String image =getStringImage(bitmap);
//
//                    //Getting Image Name
//                    String name1 = productName.getText().toString().trim();
//                    //String category1 = category.getText().toString().trim();
//                    String producprice1 = productPrice.getText().toString().trim();
//                    // override method to pass relevant parameters
//                    //urlBuilder.addQueryParameter(KEY_IMAGE, image);
//                    urlBuilder.addQueryParameter(KEY_NAME, name1);
//                    urlBuilder.addQueryParameter(KEY_PRICE, producprice1);
//                    //   urlBuilder.addQueryParameter(KEY_CATEGORY,category1);
//                    Intent intent = getIntent();
//
//                    urlBuilder.addQueryParameter("seller", Constants.USER_EMAIL);
//
//                    return urlBuilder;
//                }
//
//                @Override
//                public void processResponse(String responseFromRequest) {
//                    // override method to process response from server
//                    useResponse(responseFromRequest);
//                }
//            });
//        }
//    }
//
//        }





}