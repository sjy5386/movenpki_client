package com.sysbot32.movenpki;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sysbot32.movenpki.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = new Client();

        binding.button.setOnClickListener(v -> {
            if (!client.getSocketChannel().isConnected()) {
                connectToServer();
                if (!client.getSocketChannel().isConnected()) {
                    return;
                }
            }
        });
        binding.button2.setOnClickListener(v -> {
            if (!client.getSocketChannel().isConnected()) {
                connectToServer();
                if (!client.getSocketChannel().isConnected()) {
                    return;
                }
            }
        });
    }

    public void connectToServer() {
        String address = binding.editText.getText().toString();
        Toast.makeText(this, client.connect(address) ? "PC에 연결되었습니다." : "PC의 IP 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
    }
}