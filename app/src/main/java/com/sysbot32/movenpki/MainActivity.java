package com.sysbot32.movenpki;

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

        SharedPreferences sharedPreferences = getSharedPreferences("com.sysbot32.movenpki", MODE_PRIVATE);
        binding.editText.setText(sharedPreferences.getString("address", ""));

        if (Objects.isNull(Client.getClient())) {
            client = new Client();
        }

        binding.button.setOnClickListener(v -> {
            connectToServer();
            if (client.isConnected()) {
            }
        });
        binding.button2.setOnClickListener(v -> {
            connectToServer();
            if (client.isConnected()) {
            }
        });
    }

    public void connectToServer() {
        if (client.isConnected()) {
            return;
        }

        String address = binding.editText.getText().toString();
        client.connect(address);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, client.isConnected() ? "PC에 연결되었습니다." : "PC의 IP 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = getSharedPreferences("com.sysbot32.movenpki", MODE_PRIVATE).edit();
        editor.putString("address", address);
        editor.apply();
    }
}
