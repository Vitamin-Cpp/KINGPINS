package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class peripherals extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    private List<prod> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripherals);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //here we are about to add objects to our product list
        products = new ArrayList<>();
        products.add(new prod("Mouse","R100","katlehojnr3520@gmail.com",R.drawable.mouse));
        products.add(new prod("Reading Glasses","R100","katlehojnr3520@gmail.com",R.drawable.glasses));
        //we specify an adapter for our recycler view
        mAdapter = new MyAdapter(this,products);
        recyclerView.setAdapter(mAdapter);

    }
}