package com.example.kingpins;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class office extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<prod> products;
    private static  final String BASE_URL = "https://lamp.ms.wits.ac.za/home/s2280727/kingpins/getProduct.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView = findViewById(R.id.recycler_view);


        layoutManager = new GridLayoutManager(office.this, 2);
        recyclerView.setLayoutManager(layoutManager);

        products = new ArrayList<>();

        getProducts();

    }

    private void getProducts (){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String product_name = object.getString("description");
                                double price = object.getDouble("price");
                                String seller = object.getString("seller");
                                String image = object.getString("image");
                                String category = object.getString("category");
                                Integer id = object.getInt("id");

                                if(category.equals("OFFICE CONSUMABLES")) {
                                         prod product = new prod(product_name,price, seller,image,id);


                                         products.add(product);
                                    }
                            }

                        }catch (Exception e){

                        }

                        mAdapter = new MyAdapter(office.this,products);
                        recyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(office.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(office.this).add(stringRequest);

    }


}