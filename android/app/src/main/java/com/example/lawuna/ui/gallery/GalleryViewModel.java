/*
 * Copyright 2020 The Lawuna Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lawuna.ui.gallery;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.lawuna.DetectorActivity;
import com.example.lawuna.env.Logger;


public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<String> imageList = new ArrayList<>();
    private String logData;
    private String imageLog;
    DetectorActivity obj = new DetectorActivity();
    private static String imageDataLog = "";
    private static String phoneNumber = "";
    private static String sent_date = "";
    private static final Logger LOGGER = new Logger();
    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        logData = "Daily Record of the captured images.\n";
        if(obj.currentImageLog().isEmpty()==false){

                imageDataLog = String.join("\n", obj.currentImageLog());
                imageLog = logData+imageDataLog;
                    LOGGER.i("IMAGES-SAVED: "+imageLog);
            }else{
                imageLog = "No image data Captured";
            }


    }
    public String getText() {
        return imageLog;
    }
    public void setText(String imageLog){
        this.imageLog = imageLog;
    }

    public ArrayList<String> getList(){ return imageList;}
    public void setList(String imageLog){
        imageList.add(imageLog);
    }

}