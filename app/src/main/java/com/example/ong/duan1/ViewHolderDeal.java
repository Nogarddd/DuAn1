package com.example.ong.duan1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHolderDeal extends RecyclerView.ViewHolder{
    public TextView tvTitle, tvDateStart, tvDateEnd, tvQuantity;
    public ImageView ivDealAvatar;
    public ViewHolderDeal(View v)
    {
        super(v);
        tvTitle=v.findViewById(R.id.tvTitle);
        tvDateStart=v.findViewById(R.id.tvDateStart);
        tvDateEnd=v.findViewById(R.id.tvDateEnd);
        tvQuantity=v.findViewById(R.id.tvQuantity);
        ivDealAvatar=v.findViewById(R.id.ivDealAvatar);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getLayoutPosition();
                Toast.makeText(v.getContext(), (position+1)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
