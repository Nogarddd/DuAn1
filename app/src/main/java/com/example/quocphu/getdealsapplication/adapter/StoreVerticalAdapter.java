package com.example.quocphu.getdealsapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocphu.getdealsapplication.R;
import com.example.quocphu.getdealsapplication.StoreDetailActivity;
import com.example.quocphu.getdealsapplication.model.Store;

import java.util.List;

public class StoreVerticalAdapter extends RecyclerView.Adapter<ViewHolderStore> {
    List<Store> ds;
    Context c;
    View v;

    public StoreVerticalAdapter(Context c, List<Store> ds)
    {
        this.ds=ds;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolderStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v= LayoutInflater.from(c).inflate(R.layout.one_item_store,parent,false);
        return new ViewHolderStore(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStore holder, final int position) {
        final Store store=ds.get(position);
        holder.tvStoreName.setText(store.getStoreName());
        holder.ivStoreLogo.setImageResource(R.drawable.kfc_logo);
        holder.tvFollower.setText("");
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Followed", Toast.LENGTH_SHORT).show();
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), StoreDetailActivity.class);
                i.putExtra("data", store);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}

class ViewHolderStore extends RecyclerView.ViewHolder{
    public TextView tvStoreName;
    public ImageView ivStoreLogo;
    public Button btnFollow;
    public TextView tvFollower;
    public ViewHolderStore(View v)
    {
        super(v);
        tvStoreName=v.findViewById(R.id.tvStoreName);
        ivStoreLogo=v.findViewById(R.id.ivStoreLogo);
        btnFollow=v.findViewById(R.id.btnFollow);
        tvFollower=v.findViewById(R.id.tvFollower);
    }
}