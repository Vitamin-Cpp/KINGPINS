package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class product_layout extends AppCompatActivity {

    private Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
//        System.err.println("Clicked");
//        addToCart = findViewById(R.id.btnAddToCard);
//        addToCart.setOnClickListener(this::addToCart);
    }
//
//    void addToCart(View v){
//        System.err.println("Clicked");
//        PHPrequest phPrequest = new PHPrequest();
//        phPrequest.doRequest(this, "add_to_cart.php",new RequestHandler() {
//            @Override
//            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
//                // override method to pass relevant parameters
//                urlBuilder.addQueryParameter("buyerEmail",
//                        Constants.USER_EMAIL);
//                urlBuilder.addQueryParameter("sellerEmail",
//                        "jay.sum@email.com");
//                urlBuilder.addQueryParameter("productId",
//                        "1");
//
//                return urlBuilder;
//            }
//
//            @Override
//            public void processResponse(String responseFromRequest) {
//                Toast.makeText(product_layout.this,responseFromRequest,Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}