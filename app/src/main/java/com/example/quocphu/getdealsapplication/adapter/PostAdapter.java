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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocphu.getdealsapplication.DealDetailActivity;
import com.example.quocphu.getdealsapplication.R;
import com.example.quocphu.getdealsapplication.model.Deal;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<ViewHolderPost> {
    List<Deal> ds;
    Context c;
    View v;

    public PostAdapter(Context c, List<Deal> ds)
    {
        this.ds=ds;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v=LayoutInflater.from(c).inflate(R.layout.one_item_post,parent,false);
        return new ViewHolderPost(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPost holder, final int position) {
        final Deal deal=ds.get(position);
        holder.tvTitle.setText(deal.getTitle());
        holder.tvPercentSale.setText("%");
        holder.tvNewPrice.setText(deal.getNewPrice());
        holder.tvOldPrice.setText(deal.getOldPrice());
        holder.tvSaved.setText("");
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}

class ViewHolderPost extends RecyclerView.ViewHolder{
    public TextView tvTitle, tvPercentSale, tvNewPrice, tvOldPrice, tvSaved;
    public LinearLayout btnEdit, btnDelete;
    public ViewHolderPost(View v)
    {
        super(v);
        tvTitle=v.findViewById(R.id.tvTitle);
        tvPercentSale=v.findViewById(R.id.tvPercentSale);
        tvNewPrice=v.findViewById(R.id.tvNewPrice);
        tvOldPrice=v.findViewById(R.id.tvOldPrice);
        tvSaved=v.findViewById(R.id.tvSaved);
        btnEdit=v.findViewById(R.id.btnEdit);
        btnDelete=v.findViewById(R.id.btnDelete);
    }
}