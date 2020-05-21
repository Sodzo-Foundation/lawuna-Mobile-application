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
