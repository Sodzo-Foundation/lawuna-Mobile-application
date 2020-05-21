package com.example.lawuna.ui.od_camera;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetectorActivity extends ViewModel {

    private MutableLiveData<String> mText;

    public DetectorActivity() {
        mText = new MutableLiveData<>();
        mText.setValue("This is od fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}