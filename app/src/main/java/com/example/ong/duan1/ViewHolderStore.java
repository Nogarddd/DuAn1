package com.example.ong.duan1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHolderStore extends RecyclerView.ViewHolder{
    public TextView tvStoreName, tvAddress;
    public ImageView ivStoreAvatar;
    public ViewHolderStore(View v)
    {
        super(v);
        tvStoreName=v.findViewById(R.id.tvStoreName);
        tvAddress=v.findViewById(R.id.tvAddress);
        ivStoreAvatar=v.findViewById(R.id.ivStoreAvatar);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getLayoutPosition();
                Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
