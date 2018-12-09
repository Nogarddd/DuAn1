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
import com.example.quocphu.getdealsapplication.adapter.StoreVerticalAdapter;
import com.example.quocphu.getdealsapplication.model.Store;
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
public class StoresFragment extends Fragment {
    RecyclerView rv;
    List<Store> ds=new ArrayList<Store>();
    FirebaseDatabase database;


    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stores, container, false);
        rv=v.findViewById(R.id.recycler);
        database=FirebaseDatabase.getInstance();
        database.getReference("Store").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ds.clear();
                for(DataSnapshot store:dataSnapshot.getChildren()) {
                    Store s = store.getValue(Store.class);
                    ds.add(s);
                }
                StoreVerticalAdapter adapter=new StoreVerticalAdapter(getContext(),ds);
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