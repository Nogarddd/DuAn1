package com.example.ong.duan1.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ong.duan1.Model.User;
import com.example.ong.duan1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    TextView tvFullname, tvEmail, tvPhone, tvGender;
    ImageView ivAvatar;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference mStorage;


    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_account, container, false);
        tvFullname=v.findViewById(R.id.tvFullName);
        ivAvatar=v.findViewById(R.id.ivAvatar);
        tvEmail=v.findViewById(R.id.tvEmail);
        tvPhone=v.findViewById(R.id.tvPhone);
        tvGender=v.findViewById(R.id.tvGender);

        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user=mAuth.getCurrentUser();

        String user_id= user.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        DatabaseReference currentUserReference=databaseReference.child("User").child(user_id);
        mStorage=FirebaseStorage.getInstance().getReference();

        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                tvFullname.setText(currentUser.getFullName());
                tvEmail.setText("Email: "+currentUser.getEmail());
                tvPhone.setText("Phone: "+currentUser.getPhone());
                tvGender.setText("Gender: "+currentUser.getGender());
                String url=mStorage.child(currentUser.getAvatarUrl()).getDownloadUrl().toString();
                Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
                Glide.with(getActivity())
                        .load(url)
                        .into(ivAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;


    }

}
