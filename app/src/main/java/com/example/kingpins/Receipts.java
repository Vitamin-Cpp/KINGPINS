package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Receipts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        TextView items = findViewById(R.id.items);
        TextView totalDue = findViewById(R.id.totalDue);
        Button gotoHome = findViewById(R.id.gotoHome);

        Intent i = getIntent();
        String strItems = i.getStringExtra("items");
        int intTotalDue = i.getIntExtra("totalDue", 0);
        String strTotalDue = "R "+ intTotalDue +".00";

        items.setText(strItems);
        totalDue.setText(strTotalDue);
        gotoHome.setOnClickListener(v -> Homepage.redirectActivity(Receipts.this, Homepage.class));
    }
}