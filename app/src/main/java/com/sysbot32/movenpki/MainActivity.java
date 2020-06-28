package com.sysbot32.movenpki;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.sysbot32.movenpki.databinding.ActivityMainBinding;

import java.nio.ByteBuffer;
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
            ByteBuffer receivedData = client.getReceivedData();
            if (Objects.isNull(receivedData)) {
                Toast.makeText(this, "PC에서 < PC → 스마트폰 > 버튼을 클릭하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            byte[] npki = receivedData.array();
            new FileManager().write(getFilesDir(), "NPKI.zip", npki);
            new Archiver().unzip(getFilesDir().getPath() + "/NPKI.zip", Environment.getExternalStorageDirectory().getPath() + "/NPKI");
            Toast.makeText(this, "완료되었습니다.", Toast.LENGTH_SHORT).show();
        });
        binding.button2.setOnClickListener(v -> {
        });

        if (!client.isConnected()) {
            startActivity(new Intent(this, ConnectionActivity.class));
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItem) {
            startActivity(new Intent(this, ConnectionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
