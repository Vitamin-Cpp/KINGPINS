package com.example.kingpins;
//This is now known as cart
//any modifications of the cart will be made on this java class
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    LinearLayout mainLinearLayout;
    Button btnCheckout;
    String jsonCart;
    int totalDue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Resources resources = this.getResources();
        final int rId = resources.getIdentifier("my_edit_text","drawable", this.getPackageName());
        Drawable greyBG = resources.getDrawable(rId);

        btnCheckout = findViewById(R.id.btnCheckout);
        drawerLayout = findViewById(R.id.drawer_layout);
        mainLinearLayout = findViewById(R.id.mainLinearLayout);

        PHPrequest phPrequest = new PHPrequest();
        phPrequest.doRequest(Dashboard.this, "load_cart.php", new RequestHandler() {
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
                // override method to pass relevant parameters
                urlBuilder.addQueryParameter("buyerEmail",
                        Constants.USER_EMAIL);
                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest) {
                jsonCart = responseFromRequest;
                try{
                    JSONArray jsonArray = new JSONArray(responseFromRequest);
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String desc = jsonObject.get("description").toString();
                        String price = jsonObject.get("price").toString();
                        totalDue += Integer.parseInt(price);
                        //
                        LinearLayout l = new LinearLayout(Dashboard.this);
                        l.setOrientation(LinearLayout.VERTICAL);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(700,120);
                        layoutParams.setMargins(0,0,0,20);
                        l.setLayoutParams(layoutParams);
                        l.setBackground(greyBG);


                        mainLinearLayout.addView(l);
                        TextView t = new TextView(Dashboard.this);
                        t.setWidth(650);
                        t.setHeight(60);
                        t.setGravity(Gravity.CENTER);
                        t.setTextSize(22);
                        t.setTextColor(Color.parseColor("#000000"));
                        String text = desc;
                        t.setText(text);
                        l.addView(t);

                        TextView t1 = new TextView(Dashboard.this);
                        t1.setWidth(650);
                        t1.setHeight(60);
                        t1.setGravity(Gravity.CENTER);
                        t1.setTextSize(22);
                        t1.setTextColor(Color.parseColor("#000000"));
                        text ="R "+price+".00";
                        t1.setTypeface(null, Typeface.BOLD);
                        t1.setText(text);
                        l.addView(t1);
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }
    public void doCheckout(View view){
        PHPrequest phPrequest = new PHPrequest();
        phPrequest.doRequest(Dashboard.this, "checkout.php", new RequestHandler() {
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
                urlBuilder.addQueryParameter("buyerEmail",
                        Constants.USER_EMAIL);
                urlBuilder.addQueryParameter("jsonString",
                        jsonCart);
                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest) {
                // clear current UI cart
                if(responseFromRequest.equals("Cart cleared.")){
                    mainLinearLayout.removeAllViews();
                    int newFunds = (int)(Double.parseDouble(Constants.USER_FUNDS) - totalDue);
                    Constants.USER_FUNDS = newFunds +".00";
                }
                else{
                    Toast.makeText(Dashboard.this,responseFromRequest,Toast.LENGTH_LONG).show();
                }
                // goto generate receipt
                // pass json string if necessary(called it jsonCart)
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
        recreate();
    }
    public void clickAboutUs(View view){
        //here we redirect the activity to about us
        Homepage.redirectActivity(this,AboutUs.class);
    }

    public void clickProfile(View view){
        Homepage.redirectActivity(this,Profile.class);
    }
    public void clickBalance(View view){
        Homepage.redirectActivity(this,Balance.class);
    }//redirect the activity to about us
    public void clickMarketPlace(View view){
        Homepage.redirectActivity(this, MarketPlace.class);
    }
    public void clickWishList(View view){
        Homepage.redirectActivity(this, WishList.class);
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