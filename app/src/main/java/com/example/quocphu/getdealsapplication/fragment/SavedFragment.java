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
public class SavedFragment extends Fragment {


    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saved, container, false);
        getActivity().setTitle("Saved");
        return v;
    }

}
