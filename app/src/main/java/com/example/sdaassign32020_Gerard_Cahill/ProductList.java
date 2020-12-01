package com.example.sdaassign32020_Gerard_Cahill;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/*
 * A simple {@link Fragment} subclass.
 * this fragment class setups the product list fragment.
 * It provides the data to be grabbed by the recycler view and displayed in the arrayList format
 * The following code was taken from the downloaded assignment 3 project @author Chris Coughlan 2019
 */
public class ProductList extends Fragment {

    private static final String TAG = "RecyclerViewActivity";
    private ArrayList<FlavorAdapter> mFlavor = new ArrayList<>();

    public ProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Create an ArrayList of AndroidFlavor objects
        mFlavor.add(new FlavorAdapter("liverpool", "S, M, L, XL", R.drawable.liverpool));
        mFlavor.add(new FlavorAdapter("Man Utd", "S, M, L, XL",R.drawable.man_utd));
        mFlavor.add(new FlavorAdapter("Spurs", "S, M, L, XL", R.drawable.spurs));
        mFlavor.add(new FlavorAdapter("Arsenal", "S, M, L, XL", R.drawable.arsenal));
        mFlavor.add(new FlavorAdapter("Juventus", "S, M, L, XL", R.drawable.juventas));
        mFlavor.add(new FlavorAdapter("Ireland", "S, M, L, XL", R.drawable.ireland));
        mFlavor.add(new FlavorAdapter("England", "S, M, L, XL", R.drawable.england));
        mFlavor.add(new FlavorAdapter("Scotland", "S, M, L, XL", R.drawable.scotland));
        mFlavor.add(new FlavorAdapter("Wales", "S, M, L, XL",R.drawable.wales));
        mFlavor.add(new FlavorAdapter("France", "S, M, L, XL", R.drawable.france));
        mFlavor.add(new FlavorAdapter("Dublin", "S, M, L, XL", R.drawable.dublin));
        mFlavor.add(new FlavorAdapter("Carlow", "S, M, L, XL", R.drawable.carlow));
        mFlavor.add(new FlavorAdapter("Cork", "S, M, L, XL",R.drawable.cork));
        mFlavor.add(new FlavorAdapter("Tipp", "S, M, L, XL",R.drawable.tipp));
        mFlavor.add(new FlavorAdapter("Kilkenny", "S, M, L, XL", R.drawable.kilkenny));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        FlavorViewAdapter recyclerViewAdapter = new FlavorViewAdapter(getContext(), mFlavor);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
