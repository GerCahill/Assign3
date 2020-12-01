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


/**
 * {@link FlavorAdapter} represents a single Android platform release.
 * Each object has 3 properties: design name, sizes available, and image resource ID.
 * This is a basic arrayAdapter
 */
public class FlavorAdapter {

    // Name of the design name (e.g. Liverpool, Ireland, Dublin)
    private String designName;

    // product size (e.g. s, m, l, xl)
    private String designSize;

    // Drawable resource ID
    private int mImageResourceId;

    /**
     * Create a new FlavorAdapter object.
     * @param dName is the name of the design (e.g. Liverpool, Ireland, Dublin)
     * @param dSize is sizes available (e.g. s/m/l/xl)
     * @param imageResourceId is drawable reference ID that corresponds to the design
     *
     * */
    public FlavorAdapter( String dName, String dSize, int imageResourceId)
    {
        designName = dName;
        designSize = dSize;
        mImageResourceId = imageResourceId;
    }


    /**
    * Get the name of the design
     */
    public String getDesignName() {
        return designName;
    }

    /**
     * Get the product size
     */
    public String getDesignSize() {
        return designSize;
    }

    /**
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

}

