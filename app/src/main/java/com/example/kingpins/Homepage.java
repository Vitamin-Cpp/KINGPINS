package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        drawerLayout = findViewById(R.id.drawer_layout);

    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void clickHome(View view){
        recreate();
    }
    public void clickDashboard(View view){
        //here we redirect the activity to the dashboard
        redirectActivity(this,Dashboard.class);
    }
    public void clickAboutUs(View view){
        //redirect activity to about us
        redirectActivity(this,AboutUs.class);
    }
    public void clickLogOut(View view){
        //close the app
        logOut(this);
    }
    public void clickProfile(View view){
        redirectActivity(this,Profile.class);
    }
    public void clickMarketPlace(View view){
        redirectActivity(this, MarketPlace.class);
    }
    public void clickWishList(View view){
        redirectActivity(this, WishList.class);
    }
    public static void logOut(Activity activity) {
        //Initialize logout message
        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set the title of the message
        builder.setTitle("Logout");
        //set the message
        builder.setMessage("Are you sure you want to logout ?");
        //positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close the activity
                activity.finishAffinity();
                //close the app
                System.exit(0);
            }
        });
        //negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //showing the dialog
        builder.show();
    }
    public static void redirectActivity(Activity activity, Class aClass) {
        //initialize intents
        Intent intent = new Intent(activity,aClass);
        //set a flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start a new activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close the drawer
        closeDrawer(drawerLayout);
    }
}