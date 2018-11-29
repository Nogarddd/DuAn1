package com.example.ong.duan1;

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

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getLayoutPosition();
                Toast.makeText(v.getContext(), (position+1)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
