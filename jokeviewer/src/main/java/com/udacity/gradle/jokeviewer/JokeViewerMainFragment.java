package com.udacity.gradle.jokeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeViewerMainFragment extends Fragment {

    public JokeViewerMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jokeviewer, container, false);
        Intent intent = getActivity().getIntent();
        String joke = "";
        if (intent.hasExtra(JokeViewerMainActivity.EXTRA_JOKE)) {
            joke = intent.getStringExtra(JokeViewerMainActivity.EXTRA_JOKE);
        }
        TextView jokeView = root.findViewById(R.id.tvJokeView);
        if (!TextUtils.isEmpty(joke)) {
            jokeView.setText(joke);
        }
        return root;
    }
}
