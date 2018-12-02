package com.example.ong.duan1;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHolderDeal extends RecyclerView.ViewHolder{
    public TextView tvStoreName, tvFollower, tvTitle, tvPercentSale, tvNewPrice, tvOldPrice, tvSaved;
    public ImageView ivStoreLogo;
    public Button btnFollow;
    public Button btnSave, btnShare, btnGetCode;
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
