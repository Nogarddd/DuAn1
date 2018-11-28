package com.example.ong.duan1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvTitle.setText(deal.getTitle());
        holder.tvDateStart.setText(deal.getDateStart());
        holder.tvDateEnd.setText(deal.getDateEnd());
        holder.tvQuantity.setText(deal.getQuantity()+"");
        holder.ivDealAvatar.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}