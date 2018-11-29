package com.example.ong.duan1;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ong.duan1.Fragment.NearMeFragment;
import com.example.ong.duan1.Fragment.PopularFragment;
import com.example.ong.duan1.Fragment.StoresFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tab;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager=findViewById(R.id.viewPager);
        tab=findViewById(R.id.tabLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.addTab(tab.newTab().setText("Popular"));
        tab.addTab(tab.newTab().setText("Stores"));
        tab.addTab(tab.newTab().setText("Near me"));
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
                    fragment=new PopularFragment();
                    break;
                case 1:
                    fragment=new StoresFragment();
                    break;
                case 2:
                    fragment=new NearMeFragment();
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
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
            Toast.makeText(this, "Notify", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
