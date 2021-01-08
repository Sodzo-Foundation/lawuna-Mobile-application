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

        final Button lawuna_privacy = root.findViewById(R.id.lawuna_privacy);
        final int lawunap = R.id.lawuna_privacy;
        final int lawunac = R.id.lawuna_content;
        final int lawunatc = R.id.lawuna_tc;
        final Button lawuna_content = root.findViewById(R.id.lawuna_content);
        final Button lawuna_tc = root.findViewById(R.id.lawuna_tc);

        Log.d(TAG, "onCreateView: Started.");

        //        Text redirect to the Privacy screen
        lawuna_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PrivacyFragment();
                replaceFragment(fragment);

            }
        });

        lawuna_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ContentFragment();
                replaceFragment(fragment);
            }
        });

        lawuna_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


}
