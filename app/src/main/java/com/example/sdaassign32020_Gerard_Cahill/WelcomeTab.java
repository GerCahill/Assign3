package com.example.sdaassign32020_Gerard_Cahill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * This fragment class provides a welcome screen for the user as they begin navigating the app
 * It was adapted and created by delving into the other fragment classes that were provided in this project
 */
public class WelcomeTab extends Fragment {

    private static final String TAG = "WelcomeTab";
    public WelcomeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_tab, container, false);
        Log.i(TAG, "onCreateView: welcome tab created.");
        return view;
    }

}
