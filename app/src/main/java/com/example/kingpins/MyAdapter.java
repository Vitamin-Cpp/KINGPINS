package com.example.kingpins;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<prod> products = new ArrayList<>();

    public MyAdapter (Context context, List<prod> products){
        this.products = products;
        this.mContext = context;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView prodName,price,seller;
        private ImageView mResource;
        private Button addToCart;

        public MyViewHolder(View view){
            super(view);

            prodName = view.findViewById(R.id.textProdName);
            price = view.findViewById(R.id.textPrice);
            seller = view.findViewById(R.id.textSeller);
            mResource = view.findViewById(R.id.imageView);
            addToCart = view.findViewById(R.id.btnAddToCard);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PHPrequest phPrequest = new PHPrequest();
                    phPrequest.doRequest((Activity) view.getContext(), "add_to_cart.php",new RequestHandler() {
                        @Override
                        public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
                            // override method to pass relevant parameters
                            urlBuilder.addQueryParameter("buyerEmail",
                                    Constants.USER_EMAIL);
                            urlBuilder.addQueryParameter("sellerEmail",
                                    "kingpins@gmail.com");
                            urlBuilder.addQueryParameter("productId",
                                    "45");

                            return urlBuilder;
                        }

                        @Override
                        public void processResponse(String responseFromRequest) {
                            Toast.makeText(view.getContext(), responseFromRequest,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }


    }

    public MyAdapter() {
        super();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_product_layout,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {

        prod product = products.get(position);

        holder.prodName.setText(product.getProd_name());
        holder.price.setText(String.valueOf( "R"+product.getPrice()+"0"));
        holder.seller.setText("Owner: "+product.getSeller());
        Glide.with(mContext).load(product.getImage()).into(holder.mResource);

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PHPrequest phPrequest = new PHPrequest();
                phPrequest.doRequest((Activity) v.getContext(), "add_to_cart.php",new RequestHandler() {
                    @Override
                    public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
                        // override method to pass relevant parameters
                        urlBuilder.addQueryParameter("buyerEmail",
                                Constants.USER_EMAIL);
                        urlBuilder.addQueryParameter("sellerEmail",
                                product.getSeller());
                        urlBuilder.addQueryParameter("productId",
                                String.valueOf(product.getId()));

                        return urlBuilder;
                    }

                    @Override
                    public void processResponse(String responseFromRequest) {
                        Toast.makeText(v.getContext(), responseFromRequest,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
