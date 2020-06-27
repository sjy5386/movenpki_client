package com.sysbot32.movenpki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sysbot32.movenpki.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = Objects.isNull(Client.getClient()) ? new Client() : Client.getClient();

        binding.button.setOnClickListener(v -> {
        });
        binding.button2.setOnClickListener(v -> {
        });

        if (!client.isConnected()) {
            startActivity(new Intent(this, ConnectionActivity.class));
        }
    }
}
