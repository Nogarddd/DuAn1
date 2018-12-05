package com.example.ong.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ong.duan1.Model.Store;
import com.example.ong.duan1.R;
import com.example.ong.duan1.StoreDetailActivity;
import com.example.ong.duan1.ViewHolderStore;

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
        v= LayoutInflater.from(c).inflate(R.layout.viewholderstore_layout,parent,false);
        return new ViewHolderStore(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStore holder, final int position) {
        final Store store=ds.get(position);
        holder.tvStoreName.setText(store.getStoreName());
        holder.ivStoreLogo.setImageResource(R.drawable.kfc_logo);
        holder.tvFollower.setText(store.getFollower()+"");
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