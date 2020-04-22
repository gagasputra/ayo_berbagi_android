package com.example.ayoberbagi_mysql.donatur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ayoberbagi_mysql.R;

public class ProcessedFragment extends Fragment {
    public ProcessedFragment(){

    }

    public static ProcessedFragment newInstance(String param1, String param2) {
        ProcessedFragment fragment = new ProcessedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_processed, container, false);

        return view;
    }

}
