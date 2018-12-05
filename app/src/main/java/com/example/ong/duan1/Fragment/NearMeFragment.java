package com.example.ong.duan1.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ong.duan1.Adapter.StoreHorizontalAdapter;
import com.example.ong.duan1.Model.Store;
import com.example.ong.duan1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class NearMeFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mMap;
    MapView mapView;

    RecyclerView rv;
    List<Store> ds=new ArrayList<Store>();
    View v;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public NearMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_near_me, container, false);
        mapView=v.findViewById(R.id.map);
        getActivity().setTitle("Near me");

        rv=v.findViewById(R.id.recycler);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        database.getReference("Stores").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot store : dataSnapshot.getChildren()) {
                    Store s = store.getValue(Store.class);
                    ds.add(s);
                    LatLng location=new LatLng(s.getLat(), s.getLng());



                    mMap.addMarker(
                            new MarkerOptions()
                                    .position(location)
                                    .title(s.getStoreName())
                                    .snippet(s.getAddress())
                                    .icon(BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_ROSE)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                }
                StoreHorizontalAdapter adapter = new StoreHorizontalAdapter(v.getContext(), ds);
                LinearLayoutManager lmanager = new LinearLayoutManager(v.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);
                rv.setLayoutManager(lmanager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
