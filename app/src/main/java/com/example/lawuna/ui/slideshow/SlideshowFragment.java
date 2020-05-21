package com.example.lawuna.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.lawuna.HomeActivity;
import com.example.lawuna.R;
import com.example.lawuna.ui.gallery.GalleryFragment;
import com.example.lawuna.ui.legal.ContentFragment;
import com.example.lawuna.ui.legal.LegalFragment;
import com.example.lawuna.ui.legal.PrivacyFragment;
import com.example.lawuna.ui.od_camera.CameraActivity;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private static final String TAG = "SlideshowFragment";
    Fragment fragment = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.od_activity_camera, container, false);
//        View rootView = inflater.inflate(R.layout.fragment_tc_content, container, false);

        final Button lawuna_privacy = root.findViewById(R.id.lawuna_privacy);
        final int lawunap = R.id.lawuna_privacy;
        final int lawunac = R.id.lawuna_content;
        final int lawunatc = R.id.lawuna_tc;
        final Button lawuna_content = root.findViewById(R.id.lawuna_content);
        final Button lawuna_tc = root.findViewById(R.id.lawuna_tc);

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.child_fragment, new SlideshowFragment());
        Log.d(TAG, "onCreateView: Started.");
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                lawuna_privacy.setText(s);
//            }
//        });

        //        Text redirect to the Privacy screen
        lawuna_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ((HomeActivity)getActivity()).setViewPager(1);
//                Fragment fragment = null;
                fragment = new PrivacyFragment();
                replaceFragment(fragment);

            }
        });

        lawuna_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.fragment_privacy_policy);
//                ((HomeActivity)getActivity()).setViewPager(2);
                fragment = new ContentFragment();
                replaceFragment(fragment);
            }
        });

        lawuna_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.fragment_privacy_policy);
//                ((HomeActivity)getActivity()).setViewPager(3);
//                Fragment fragment = null;
                fragment = new LegalFragment();
                replaceFragment(fragment);
            }
        });

        String privacy = slideshowViewModel.getPrivacy();
        String content = slideshowViewModel.getContent();
        String tc = slideshowViewModel.getTC();

        lawuna_privacy.setText(Html.fromHtml("<bold>" + privacy + "</bold>"));
        lawuna_content.setText(content);
        lawuna_tc.setText(tc);
        return root;
    }


    //        Replace Fragment
    public void replaceFragment(Fragment someFragment) {
//        Transition
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        Replace whatever is in the Legal fragment view with this fragment and transition to the back stack
        transaction.replace(R.id.child_fragment, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
//        Commit transition
        transaction.commit();
    }

//    public void selectFragment(View view) {
//        Fragment fragment = null;
//        switch (view.getId()) {
//            case R.id.lawuna_privacy:
//            case R.id.lawuna_tc:
////                        Create new Fragment
//                fragment = new LegalFragment();
//                replaceFragment(fragment);
//                break;
//            case R.id.lawuna_content:
//                fragment = new CameraActivity();
//                replaceFragment(fragment);
//                break;
//        }
////        replaceFragment(fragment);
//    }




}
