package com.example.ong.duan1.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ong.duan1.Adapter.DealHorizontalAdapter;
import com.example.ong.duan1.Adapter.DealVerticalAdapter;
import com.example.ong.duan1.Model.Deal;
import com.example.ong.duan1.R;
import com.example.ong.duan1.SeeAllActivity;
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
public class DealsFragment extends Fragment {
    TextView seeAllPopular, seeAllEnding;
    RecyclerView rv1, rv2, rv3;
    List<Deal> ds=new ArrayList<Deal>();
    View v;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    public DealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_deals, container, false);
        seeAllPopular=v.findViewById(R.id.seeAllPopular);
        seeAllEnding=v.findViewById(R.id.seeAllEnding);
        rv1=v.findViewById(R.id.recyclerNew);
        rv2=v.findViewById(R.id.recyclerPopular);
        rv3=v.findViewById(R.id.recyclerEnding);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        database.getReference("Deals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot deal:dataSnapshot.getChildren()) {
                    Deal d=deal.getValue(Deal.class);
                    ds.add(d);
                }
                DealHorizontalAdapter adapter=new DealHorizontalAdapter(v.getContext(),ds);
                DealVerticalAdapter adapter2=new DealVerticalAdapter(v.getContext(),ds);
                LinearLayoutManager lmanager=new LinearLayoutManager(v.getContext());
                LinearLayoutManager lmanager2=new LinearLayoutManager(v.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);
                LinearLayoutManager lmanager3=new LinearLayoutManager(v.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

                rv1.setLayoutManager(lmanager);
                rv1.setAdapter(adapter2);
                rv2.setLayoutManager(lmanager2);
                rv2.setAdapter(adapter);
                rv3.setLayoutManager(lmanager3);
                rv3.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seeAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "See all", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), SeeAllActivity.class);
                startActivity(i);
            }
        });
        seeAllEnding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "See all", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), SeeAllActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

}
