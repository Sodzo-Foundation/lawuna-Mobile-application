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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lawuna.R;

public class LegalFragment extends Fragment {

    private LegalViewModel legalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        legalViewModel =
                ViewModelProviders.of(this).get(LegalViewModel.class);
        View root = inflater.inflate(R.layout.legal_terms_layout, container, false);
        final TextView lawuna_privacy = root.findViewById(R.id.tos_text);

        String privacy = legalViewModel.getPrivacy();

        lawuna_privacy.setText(privacy);
        return root;
    }
}
