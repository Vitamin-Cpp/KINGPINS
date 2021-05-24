package com.example.kingpins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
    private Context mContext;
    private List<prod> products = new ArrayList<>();

    public MyAdapter (Context Context, List<prod>products){
        this.products = products;
        this.mContext = Context;

    }
    public class myViewHolder extends RecyclerView.ViewHolder{

        private TextView prodName,price,seller;
        private ImageView mResource;

        public myViewHolder(View view){
            super(view);

            prodName = view.findViewById(R.id.textProdName);
            price = view.findViewById(R.id.textPrice);
            seller = view.findViewById(R.id.textSeller);
            mResource = view.findViewById(R.id.imageView);
        }


    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_product_layout,parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        prod product = products.get(position);
        holder.mResource.setImageResource(product.getImage());
        holder.prodName.setText(product.getProd_name());
        holder.price.setText(product.getPrice());
        holder.seller.setText(product.getSeller());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
