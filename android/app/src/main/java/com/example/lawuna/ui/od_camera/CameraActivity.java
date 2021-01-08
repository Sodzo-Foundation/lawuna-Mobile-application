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

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lawuna.R;
import com.squareup.picasso.Picasso;

public class CameraActivity extends Fragment {

    private DetectorActivity homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DetectorActivity.class);
        View root = inflater.inflate(R.layout.od_activity_camera, container, false);
        final TextView textView = root.findViewById(R.id.text_od);
        final TextView more_info = root.findViewById(R.id.more_info);
        String url = "http://41.221.87.42:8080/36by36_new.png";
        ImageView imageView = (ImageView) root.findViewById(R.id.shore);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(700, 900)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error_img);

        Glide.with(this).load(url).apply(options).into(imageView);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(Html.fromHtml(s));
            }
        });
        String project_info  = homeViewModel.getInfo();
        more_info.setMovementMethod(LinkMovementMethod.getInstance());
        more_info.setText(Html.fromHtml(project_info));
        return root;
    }
}