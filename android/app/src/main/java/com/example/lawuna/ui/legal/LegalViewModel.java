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

package com.example.lawuna.ui.legal;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LegalViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String logData;
    private String lawuna_privacy;
    private String lawuna_content;
    private String lawuna_tc;

    public LegalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        logData = "Daily Record of the captured images.\n"+
                    "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n"+
                "Date: 7/05/2020 12:15:12 => IMG_7/05/2020_12:15:12_01234196.jpg\n"+
                "Date: 7/05/2020 12:15:33 => IMG_7/05/2020_12:15:33_01234123.jpg\n";
        lawuna_privacy = "Privacy Policy"+
                "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n"+
                "Date: 7/05/2020 12:15:12 => IMG_7/05/2020_12:15:12_01234196.jpg\n"+
                "Date: 7/05/2020 12:15:33 => IMG_7/05/2020_12:15:33_01234123.jpg\n"+
                "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n"+
                "Date: 7/05/2020 12:15:12 => IMG_7/05/2020_12:15:12_01234196.jpg\n"+
                "Date: 7/05/2020 12:15:33 => IMG_7/05/2020_12:15:33_01234123.jpg\n"+
                "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n"+
                "Date: 7/05/2020 12:15:12 => IMG_7/05/2020_12:15:12_01234196.jpg\n"+
                "Date: 7/05/2020 12:15:33 => IMG_7/05/2020_12:15:33_01234123.jpg\n"+
                "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n";
        lawuna_content = "Content";
        lawuna_tc = "Terms and Conditions";

    }

//    public LiveData<String> getText() {
//        return mText;
//    }
    public String getText() {
        return logData;
    }
    public String getPrivacy(){ return lawuna_privacy; }
    public String getContent(){ return lawuna_content; }
    public String getTC(){ return lawuna_tc; }
}