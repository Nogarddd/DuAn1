package com.example.ong.duan1.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ong.duan1.Model.Deal;
import com.example.ong.duan1.R;
import com.example.ong.duan1.ViewHolderDeal;
import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<ViewHolderDeal> {
    List<Deal> ds;
    Context c;

    public DealAdapter(Context c, List<Deal> ds)
    {
        this.ds=ds;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolderDeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.viewholderdeal_layout,parent,false);
        return new ViewHolderDeal(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDeal holder, final int position) {
        final Deal deal=ds.get(position);
        holder.tvStoreName.setText(deal.getStoreName());
        holder.tvFollower.setText(deal.getFollower()+"");
        holder.tvTitle.setText(deal.getTitle());
        holder.tvPercentSale.setText(deal.getPercentSale()+"%");
        holder.tvNewPrice.setText(deal.getNewPrice()+" đ");
        holder.tvOldPrice.setText(deal.getOldPrice()+" đ");
        holder.tvOldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvSaved.setText(deal.getSaved()+"");
        holder.ivStoreLogo.setImageResource(R.drawable.kfc_logo);
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Followed", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Shared", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Get code", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}