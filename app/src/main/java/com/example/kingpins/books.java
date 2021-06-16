package com.example.kingpins;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class books extends AppCompatActivity {
//    private String userEmail;
//    private TextView textView;
//    private Toolbar mToolbar;
//    private ActionBar mActionBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    private List<prod> products;
    DrawerLayout drawerLayout;
//    private ProgressBar progressBar;
        //the location where we can find the php file
    //private static  final String BASE_URL = "http://192.168.100.16/android/getProducts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

//        mToolbar = findViewById(R.id.dashboard_toolbar);
//        progressBar = findViewById(R.id.progressbar);
//        setSupportActionBar(mToolbar);
//        mActionBar = getSupportActionBar();

        //drawerLayout = findViewById(R.id.drawer_layout);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //here we are about to add objects to our product list
        products = new ArrayList<>();


        products.add(new prod("Principles and Practice by Eric Mazur","R600","katlehojnr3520@gmail.com",R.drawable.principlesandpracticeofphysics));
        products.add(new prod("Calculus","R400","katlehojnr3520@gmail.com",R.drawable.calculus));
        //we specify an adapter for our recycler view
        mAdapter = new MyAdapter(this,products);
        recyclerView.setAdapter(mAdapter);

//        manager = new GridLayoutManager(books.this, 2);
//        recyclerView.setLayoutManager(manager);
//        products = new ArrayList<>();
//
//        getProducts();
    }
//    private void getProducts (){
//        progressBar.setVisibility(View.VISIBLE);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//
//                            JSONArray array = new JSONArray(response);
//                            for (int i = 0; i<array.length(); i++){
//
//                                JSONObject object = array.getJSONObject(i);
//
//                                String product_name = object.getString("product_name");
//                                double price = object.getDouble("price");
//                                double seller = object.getDouble("seller");
//                                String image = object.getString("image");
//
//                                String vendor = String.valueOf(seller);
//
//
//                                prod product = new prod(title,price, vendor,image);
//                                products.add(product);
//                            }
//
//                        }catch (Exception e){
//
//                        }
//
//                        mAdapter = new RecyclerAdapter(HomeActivity.this,products);
//                        recyclerView.setAdapter(mAdapter);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(HomeActivity.this, error.toString(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        Volley.newRequestQueue(HomeActivity.this).add(stringRequest);
//
//    }

}