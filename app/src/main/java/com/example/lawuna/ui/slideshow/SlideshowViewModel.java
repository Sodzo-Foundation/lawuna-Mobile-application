package com.example.lawuna.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String lawuna_privacy;
    private String lawuna_content;
    private String lawuna_tc;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        lawuna_privacy = "Privacy Policy";
        lawuna_content = "Content";
        lawuna_tc = "Terms and Conditions";
    }


//    public LiveData<String> getText() {
//        return mText;
//    }
    public String getPrivacy(){ return lawuna_privacy; }
    public String getContent(){ return lawuna_content; }
    public String getTC(){ return lawuna_tc; }
}