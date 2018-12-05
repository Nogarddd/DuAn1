package com.example.ong.duan1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderStore extends RecyclerView.ViewHolder{
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
