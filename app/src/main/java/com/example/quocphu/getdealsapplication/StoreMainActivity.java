package com.example.quocphu.getdealsapplication;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocphu.getdealsapplication.fragment.DealsFragment;
import com.example.quocphu.getdealsapplication.fragment.HomeFragment;
import com.example.quocphu.getdealsapplication.fragment.MyPostFragment;
import com.example.quocphu.getdealsapplication.fragment.MyStoreFragment;
import com.example.quocphu.getdealsapplication.fragment.StoresFragment;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class StoreMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    TabLayout tab;
    private FloatingActionButton fab_newpost,fab_storedetail,fab_3,fab_main;
    private boolean actionFab = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.addTab(tab.newTab().setText("My Post"));
        tab.addTab(tab.newTab().setText("My Store"));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fab_main = findViewById(R.id.fab_main);
        fab_newpost = findViewById(R.id.fab_newpost);
        fab_storedetail = findViewById(R.id.fab_storedetail);
        fab_3 = findViewById(R.id.fab_3);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionFab==false){
                    hideFab();
                    actionFab = true;
                }else {
                    showFab();
                    actionFab=false;
                }
            }
        });
        fab_newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreMainActivity.this,PostDealActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_noti){
            Intent i=new Intent(StoreMainActivity.this, NotificationActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    class MyFragmentAdapter extends FragmentStatePagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position){
                case 0:
                    fragment=new MyPostFragment();
                    break;
                case 1:
                    fragment=new MyStoreFragment();
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
    private void showFab(){
        fab_newpost.show();
        fab_storedetail.show();
        fab_3.show();
    }
    private void hideFab(){
        fab_3.hide();
        fab_newpost.hide();
        fab_storedetail.hide();
    }
}
