package com.example.ong.duan1.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ong.duan1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    ViewPager pager;
    TabLayout tab;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        pager = v.findViewById(R.id.viewPager);
        tab = v.findViewById(R.id.tabLayout);
        getActivity().setTitle("Account");

        MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tab.addTab(tab.newTab().setText("My Account"));
        tab.addTab(tab.newTab().setText("My Wallet"));
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
        return v;
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
                    fragment=new MyAccountFragment();
                    break;
                case 1:
                    fragment=new MyWalletFragment();
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
