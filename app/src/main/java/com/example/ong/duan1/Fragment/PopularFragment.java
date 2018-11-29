package com.example.ong.duan1.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ong.duan1.Adapter.DealAdapter;
import com.example.ong.duan1.Adapter.StoreAdapter;
import com.example.ong.duan1.Model.Deal;
import com.example.ong.duan1.Model.Store;
import com.example.ong.duan1.R;
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
public class PopularFragment extends Fragment {
    RecyclerView rv;
    List<Deal> ds=new ArrayList<Deal>();
    View v;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_popular, container, false);
        rv=v.findViewById(R.id.recycler);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        database.getReference("Deals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot deal:dataSnapshot.getChildren()) {
                    Deal d=deal.getValue(Deal.class);
                    ds.add(d);
                }
                DealAdapter adapter=new DealAdapter(v.getContext(),ds);
                LinearLayoutManager lmanager=new LinearLayoutManager(v.getContext());
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
