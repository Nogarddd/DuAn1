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

public class DealHorizontalAdapter extends RecyclerView.Adapter<ViewHolderDeal> {
    List<Deal> ds;
    Context c;
    View v;

    public DealHorizontalAdapter(Context c, List<Deal> ds)
    {
        this.ds=ds;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolderDeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v=LayoutInflater.from(c).inflate(R.layout.one_item_deal,parent,false);

        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.9);

        v.setLayoutParams(layoutParams);

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
                Toast.makeText(c, "Save", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Share", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Get code", Toast.LENGTH_SHORT).show();
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), (position+1)+"", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(v.getContext(),DealDetailActivity.class);
                i.putExtra("data", deal);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}

class ViewHolderDeal extends RecyclerView.ViewHolder{
    public TextView tvStoreName, tvFollower, tvTitle, tvPercentSale, tvNewPrice, tvOldPrice, tvSaved;
    public ImageView ivStoreLogo;
    public Button btnFollow;
    public LinearLayout btnSave, btnShare, btnGetCode;
    public ViewHolderDeal(View v)
    {
        super(v);
        tvStoreName=v.findViewById(R.id.tvStoreName);
        tvFollower=v.findViewById(R.id.tvFollower);
        tvTitle=v.findViewById(R.id.tvTitle);
        tvPercentSale=v.findViewById(R.id.tvPercentSale);
        tvNewPrice=v.findViewById(R.id.tvNewPrice);
        tvOldPrice=v.findViewById(R.id.tvOldPrice);
        tvSaved=v.findViewById(R.id.tvSaved);
        ivStoreLogo=v.findViewById(R.id.ivStoreLogo);
        btnFollow=v.findViewById(R.id.btnFollow);
        btnSave=v.findViewById(R.id.btnSave);
        btnShare=v.findViewById(R.id.btnShare);
        btnGetCode=v.findViewById(R.id.btnGetCode);
    }
}