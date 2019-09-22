package com.example.hw9;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoingFragment extends Fragment {


    public DoingFragment() {
        // Required empty public constructor
    }

    public static DoingFragment newInstance() {

        Bundle args = new Bundle();

        DoingFragment fragment = new DoingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_doing, container, false);


        return v;
    }

}
