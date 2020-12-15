package com.example.sdaassign32020_Gerard_Cahill;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/*
 * @author Chris Coughlan 2019
 */
public class FlavorViewAdapter extends RecyclerView.Adapter<FlavorViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    //add array for each item\
    private ArrayList<FlavorAdapter> mFlavors;

    FlavorViewAdapter(Context mNewContext, ArrayList<FlavorAdapter> mflavor) {
        this.mNewContext = mNewContext;
        this.mFlavors = mflavor;
    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        //viewHolder.productText.setText(mFlavors.get(position).getDesignType());
        viewHolder.designText.setText(mFlavors.get(position).getDesignName());
        viewHolder.sizeText.setText(mFlavors.get(position).getDesignSize());
        viewHolder.imageItem.setImageResource(mFlavors.get(position).getImageResourceId());
        viewHolder.itemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: this was clicked");
                Toast.makeText(mNewContext, mFlavors.get(position).getDesignName() + " jersey selected", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFlavors.size();
    }

    //viewholder class
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageItem;
        TextView designText;
        TextView sizeText;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.imageItem);
            designText = itemView.findViewById(R.id.flavorText);
            sizeText = itemView.findViewById(R.id.flavorVers);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }
    }
}