package com.example.kingpins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class serviceAdapter extends  RecyclerView.Adapter<serviceAdapter.MyViewHolder>  {
    private Context mContxt;
    private List<Service> services =new ArrayList<>();

    public serviceAdapter (Context context,List<Service> services){
        this.services= services;
        this.mContxt= context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle,mPrice,mSeller;


        public MyViewHolder(View view){
            super(view);

            mTitle =view.findViewById(R.id.txTitle);
            mPrice =view.findViewById(R.id.txPrice);
            mSeller =view.findViewById(R.id.txSeller);

        }


    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContxt).inflate(R.layout.service_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MyViewHolder holder, int position) {
        Service pro=services.get(position);

        holder.mTitle.setText(pro.getTitle());
        holder.mPrice.setText(String.valueOf(pro.getPrice()));
        holder.mSeller.setText(pro.getSeller());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }


}
