package com.example.quocphu.getdealsapplication;

import android.content.Intent;
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
import com.example.quocphu.getdealsapplication.fragment.MyAccountFragment;
import com.example.quocphu.getdealsapplication.fragment.MyPostFragment;

public class StoreManagerMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    TabLayout tab;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.addTab(tab.newTab().setText("My Post"));
        tab.addTab(tab.newTab().setText("My Account"));
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
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreManagerMainActivity.this, AddDealActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_storemanager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_noti){
            Intent i=new Intent(StoreManagerMainActivity.this, NotificationActivity.class);
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
                    fragment=new MyAccountFragment();
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
}
