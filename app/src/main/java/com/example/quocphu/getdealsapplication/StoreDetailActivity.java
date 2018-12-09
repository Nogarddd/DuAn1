package com.example.quocphu.getdealsapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.quocphu.getdealsapplication.model.Deal;
import com.example.quocphu.getdealsapplication.model.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StoreDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv;
    List<Deal> ds=new ArrayList<Deal>();
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    TextView tvStoreName, tvAddress;
    Button btnSeeOnMap;
    Store store;
    TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        rv=findViewById(R.id.recycler);

        toolbar=findViewById(R.id.toolbar);
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Store Detail");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        tvStoreName=findViewById(R.id.tvStoreName);
        tvAddress=findViewById(R.id.tvAddress);
        btnSeeOnMap=findViewById(R.id.btnSeeOnMap);

        /*store=(Store) getIntent().getSerializableExtra("data");
        tvStoreName.setText(store.getNameStore());
        tvAddress.setText(store.getAddress());
        btnSeeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        database.getReference("Deals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ds.clear();
                for(DataSnapshot deal:dataSnapshot.getChildren()) {
                    Deal d=deal.getValue(Deal.class);
                    ds.add(d);
                }
                DealHorizontalAdapter adapter=new DealHorizontalAdapter(StoreDetailActivity.this,ds);
                LinearLayoutManager lmanager=new LinearLayoutManager(StoreDetailActivity.this);
                rv.setLayoutManager(lmanager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}


