package com.example.ong.duan1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ong.duan1.Adapter.DealAdapter;
import com.example.ong.duan1.Model.Deal;
import com.example.ong.duan1.Model.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    RecyclerView rv;
    List<Deal> ds=new ArrayList<Deal>();
    View v;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    TextView tvStoreName, tvAddress;
    Button btnSeeOnMap;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        rv=findViewById(R.id.recycler);
        tvStoreName=findViewById(R.id.tvStoreName);
        tvAddress=findViewById(R.id.tvAddress);
        btnSeeOnMap=findViewById(R.id.btnSeeOnMap);

        store=(Store) getIntent().getSerializableExtra("data");
        tvStoreName.setText(store.getStoreName());
        tvAddress.setText(store.getAddress());
        btnSeeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreActivity.this, MainActivity.class);
                i.putExtra("data", store);
                startActivity(i);
            }
        });


        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        database.getReference("Deals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot deal:dataSnapshot.getChildren()) {
                    Deal d=deal.getValue(Deal.class);
                    ds.add(d);
                }
                DealAdapter adapter=new DealAdapter(StoreActivity.this,ds);
                LinearLayoutManager lmanager=new LinearLayoutManager(StoreActivity.this);
                rv.setLayoutManager(lmanager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
