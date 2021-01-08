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

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lawuna.R;

public class PrivacyFragment extends Fragment {

    private PrivacyViewModel legalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        legalViewModel =
                ViewModelProviders.of(this).get(PrivacyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar2);

        //setting the title
        toolbar.setTitle("Legal");
        toolbar.setTitleTextColor(Color.WHITE);

        final TextView lawuna_privacy = root.findViewById(R.id.privacy_content);

        String privacy = legalViewModel.getPrivacy();

        lawuna_privacy.setText(privacy);
        return root;
    }
}
