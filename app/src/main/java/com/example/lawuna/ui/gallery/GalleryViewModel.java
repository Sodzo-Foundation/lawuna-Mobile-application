package com.example.lawuna.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String logData;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        logData = "Daily Record of the captured images.\n"+
                    "Date: 7/05/2020 12:15:01 => IMG_7/05/2020_12:15:01_01234121.jpg\n"+
                "Date: 7/05/2020 12:15:06 => IMG_7/05/2020_12:15:06_01234144.jpg\n"+
                "Date: 7/05/2020 12:15:12 => IMG_7/05/2020_12:15:12_01234196.jpg\n"+
                "Date: 7/05/2020 12:15:33 => IMG_7/05/2020_12:15:33_01234123.jpg\n";

    }

//    public LiveData<String> getText() {
//        return mText;
//    }
    public String getText() {
        return logData;
    }
}