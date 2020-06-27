package com.sysbot32.movenpki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sysbot32.movenpki.databinding.ActivityConnectionBinding;

public class ConnectionActivity extends AppCompatActivity {
    private ActivityConnectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}