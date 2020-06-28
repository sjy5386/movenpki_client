package com.sysbot32.movenpki;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sysbot32.movenpki.databinding.ActivityConnectionBinding;

import java.util.Objects;

public class ConnectionActivity extends AppCompatActivity {
    private ActivityConnectionBinding binding;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("com.sysbot32.movenpki", MODE_PRIVATE);
        binding.editText.setText(sharedPreferences.getString("address", ""));

        client = Objects.isNull(Client.getClient()) ? new Client() : Client.getClient();

        binding.button3.setOnClickListener(v -> {
            String address = binding.editText.getText().toString();
            client.connect(address);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, client.isConnected() ? "PC에 연결되었습니다." : "PC에 연결하지 못했습니다.", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = getSharedPreferences("com.sysbot32.movenpki", MODE_PRIVATE).edit();
            editor.putString("address", address);
            editor.apply();

            if (client.isConnected()) {
                client.start();
                finish();
            }
        });

        if (!client.isConnected() && !binding.editText.getText().toString().equals("")) {
            binding.button3.callOnClick();
        }
    }
}
