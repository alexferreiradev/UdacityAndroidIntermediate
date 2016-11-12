package com.alex.sunshineapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alex on 11/11/2016.
 */
public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String detail = getArguments().getString(Intent.EXTRA_TEXT);
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        TextView tvDetail = (TextView) view.findViewById(R.id.tvDetail);
        tvDetail.setText(detail);
        return view;
    }

}
