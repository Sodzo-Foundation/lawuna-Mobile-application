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

package com.example.lawuna.ui.od_camera;

import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetectorActivity extends ViewModel {

    private MutableLiveData<String> mText;
    private String more_info;

    public DetectorActivity() {
        mText = new MutableLiveData<>();
        mText.setValue(
                "<p><b>Goal:</b><br/>To establish a  freshwater contaminant monitoring system and " +
                        "empower communities with rich, open environmental data</p>\n"
                +"<p><b>Mission:</b><br/>To improve the quality of water and the lives of " +
                        "communities surrounding fresh water bodies by use of technology through " +
                        "detecting, identifying and monitoring contaminants thus creating a sustainanble " +
                        "& friendly ecosystem</p>\n"
                +"<p><b>Vision:</b><br/>To have contaminant free fresh water bodies</p>\n"
        );
        more_info = "We are currently operating in Africa/ Uganda<br/>"
                +"Find Us: <a href='https://www.sodzofoundation.org/projects'>here</a>";
        setInfo(more_info);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public String getInfo() {
        return more_info;
    }
    public void setInfo(String info){
        this.more_info = info;
    }
}