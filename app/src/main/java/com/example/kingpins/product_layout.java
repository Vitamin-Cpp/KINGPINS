package com.example.kingpins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import connection_classes.PHPrequest;
import connection_classes.RequestHandler;
import constants_classes.Constants;
import okhttp3.HttpUrl;

public class product_layout extends RecyclerView.Adapter<product_layout.ViewHolder> {

    private List<prod> prodList;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }
    public product_layout(Context context, List<prod>list){
        prodList = list;
        activity = (ItemClicked )context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         ImageView imageview;
         TextView textProdName, textPrice, textProdCode, textSeller;
         Button btnAddToCard;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageView);
            textProdName = itemView.findViewById(R.id.textProdName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textProdCode = itemView.findViewById(R.id.textProdCode);
            textSeller = itemView.findViewById(R.id.textSeller);
            btnAddToCard = itemView.findViewById(R.id.btnAddToCard);
            btnAddToCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(prodList.indexOf((prod)v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public product_layout.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull product_layout.ViewHolder holder, int position) {
        holder.itemView.setTag(prodList.get(position));

        holder.textProdName.setText(prodList.get(position).getProd_name());
        holder.textPrice.setText(prodList.get(position).getPrice());
        holder.textSeller.setText(prodList.get(position).getSeller());
        holder.textProdCode.setText(prodList.get(position).getProdCode());
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }
}