package com.example.quocphu.getdealsapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quocphu.getdealsapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWalletFragment extends Fragment {


    public MyWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_wallet, container, false);
    }

}
