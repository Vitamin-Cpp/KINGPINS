package com.example.kingpins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class Deposit extends AppCompatActivity {
    private EditText etFunds;
    private Button btnDeposit;
    private TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        etFunds=findViewById(R.id.et_Deposit);
        btnDeposit=findViewById(R.id.btnDeposit);
        txtView=findViewById(R.id.txtFunds);

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validInput(etFunds.getText().toString().trim())){
                    updateBalance();
                    
                }
                if(!validInput(etFunds.getText().toString().trim())){
                    etFunds.setError("Invalid input");
                }

                if(etFunds.getText().toString().trim().equals(""))
                {
                    etFunds.setError("Required field!");
                }
            }
        });
    }

    public void updateBalance()
    {
        // make new php request object
        PHPrequest phPrequest=new PHPrequest();
        phPrequest.doRequest(Deposit.this, "add_funds.php", new RequestHandler() {

            // do request
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder)
            {
                // override method to pass relevant parameters
                urlBuilder.addQueryParameter("email",
                        Constants.USER_EMAIL);
                String funds= etFunds.getText().toString().trim();
                urlBuilder.addQueryParameter("funds",funds);

                return urlBuilder;
            }

            @Override
            public void processResponse(String responseFromRequest)
            {
                Toast.makeText(Deposit.this,"Your deposit is successful",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Deposit.this,Homepage.class));

            }
        });
    }
    public static boolean validInput(String str){
        if(str.matches("[0-9]+")){
            return true;
        }
        else {
            return false;
        }
    }
}