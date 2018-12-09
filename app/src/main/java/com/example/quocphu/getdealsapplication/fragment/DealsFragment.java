package com.example.quocphu.getdealsapplication.fragment;

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
import com.example.quocphu.getdealsapplication.R;
import com.example.quocphu.getdealsapplication.SeeAllActivity;
import com.example.quocphu.getdealsapplication.adapter.DealHorizontalAdapter;
import com.example.quocphu.getdealsapplication.model.Deal;
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
    private FirebaseDatabase database;
    private DatabaseReference node_deal;
    TextView seeAll_popular, seeAll_ending;
    RecyclerView rv_popular, rv_ending, rv_new;
    List<Deal> ds=new ArrayList<Deal>();

    public DealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deals, container, false);
        seeAll_popular=v.findViewById(R.id.seeAll_popular);
        seeAll_ending=v.findViewById(R.id.seeAll_ending);
        rv_popular=v.findViewById(R.id.rv_popular);
        rv_ending=v.findViewById(R.id.rv_ending);
        rv_new=v.findViewById(R.id.rv_new);

        database=FirebaseDatabase.getInstance();
        node_deal = database.getReference("Deal");
        node_deal.addValueEventListener(new ValueEventListener() {
            String key_store;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemStore:dataSnapshot.getChildren()) {
                    key_store = itemStore.getKey();
                    node_deal.child(key_store).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ds.clear();
                            for(DataSnapshot itemDeal:dataSnapshot.getChildren()) {
                                Deal d = itemDeal.getValue(Deal.class);
                                ds.add(d);
                            }
                            DealHorizontalAdapter adapter=new DealHorizontalAdapter(getContext(),ds);
                            LinearLayoutManager lmanager=new LinearLayoutManager(getContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false);
                            LinearLayoutManager lmanager2=new LinearLayoutManager(getContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false);
                            LinearLayoutManager lmanager3=new LinearLayoutManager(getContext());

                            rv_popular.setLayoutManager(lmanager);
                            rv_popular.setAdapter(adapter);
                            rv_ending.setLayoutManager(lmanager2);
                            rv_ending.setAdapter(adapter);
                            rv_new.setLayoutManager(lmanager3);
                            rv_new.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seeAll_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "See all", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), SeeAllActivity.class);
                startActivity(i);
            }
        });
        seeAll_ending.setOnClickListener(new View.OnClickListener() {
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
