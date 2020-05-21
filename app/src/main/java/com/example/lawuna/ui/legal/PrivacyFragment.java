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

        //placing toolbar in place of actionbar
//        root.setSupportActionBar(toolbar);
        final TextView lawuna_privacy = root.findViewById(R.id.privacy_content);
//        final TextView lawuna_content = root.findViewById(R.id.lawuna_content);
//        final TextView lawuna_tc = root.findViewById(R.id.lawuna_tc);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                lawuna_privacy.setText(s);
//            }
//        });

        String privacy = legalViewModel.getPrivacy();
//        String content = legalViewModel.getContent();
//        String tc = legalViewModel.getTC();

        lawuna_privacy.setText(privacy);
//        lawuna_content.setText(content);
//        lawuna_tc.setText(tc);
        return root;
    }
}
