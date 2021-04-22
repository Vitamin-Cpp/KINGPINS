package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout = findViewById(R.id.drawer_layout);
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
        recreate();
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