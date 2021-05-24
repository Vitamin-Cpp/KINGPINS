package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import constants_classes.Constants;

public class Balance extends AppCompatActivity {
    private Button addfunds;
    private TextView tvFunds;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        drawerLayout = findViewById(R.id.drawer_layout);

        tvFunds=findViewById(R.id.textView5);
        String value = "R " + Constants.USER_FUNDS;
        tvFunds.setText(value);

        //takes you to deposit activity
        addfunds=findViewById(R.id.add_funds);
        addfunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Balance.this, Deposit.class);
                startActivity(intent);
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
    public void clickDashboard(View view){
        //here we redirect the activity to the dashboard
        Homepage.redirectActivity(this,Dashboard.class);

    }

    public void clickBalance(View view){recreate();;}
    public void clickProfile(View view){
        Homepage.redirectActivity(this,Profile.class);
    }
    public void clickMarketPlace(View view){
        Homepage.redirectActivity(this, MarketPlace.class);
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