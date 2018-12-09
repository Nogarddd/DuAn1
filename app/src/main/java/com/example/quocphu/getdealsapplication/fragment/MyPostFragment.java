package com.example.quocphu.getdealsapplication.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quocphu.getdealsapplication.R;
import com.example.quocphu.getdealsapplication.adapter.DealHorizontalAdapter;
import com.example.quocphu.getdealsapplication.adapter.PostAdapter;
import com.example.quocphu.getdealsapplication.model.Deal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference node_deal;
    List<Deal> ds=new ArrayList<Deal>();
    RecyclerView rv;
    FirebaseAuth mAuth;



    public MyPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_post, container, false);
        rv=v.findViewById(R.id.recycler);

        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();

        node_deal = database.getReference("Deal").child(user.getUid());
        node_deal.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ds.clear();
                for(DataSnapshot deal:dataSnapshot.getChildren()) {
                    Deal d=deal.getValue(Deal.class);
                    ds.add(d);
                }

                PostAdapter adapter=new PostAdapter(getContext(),ds);
                LinearLayoutManager lmanager=new LinearLayoutManager(getContext());

                rv.setLayoutManager(lmanager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

}
