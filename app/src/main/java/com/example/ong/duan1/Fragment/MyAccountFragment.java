package com.example.ong.duan1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ong.duan1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    FirebaseAuth mAuth;
    TextView tvFullname;
    ImageView ivUserAvatar;


    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_account, container, false);
        tvFullname=v.findViewById(R.id.tvFullName);
        ivUserAvatar=v.findViewById(R.id.ivUserAvatar);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        if (user != null){
            if (user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(ivUserAvatar);
            }
            if(user.getDisplayName() != null){
                tvFullname.setText(user.getDisplayName());
            }
        }
        return v;


    }

}
