package com.example.responsi_417;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeUser extends AppCompatActivity {

    Button isi, lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        isi = findViewById(R.id.isiData);
        isi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(homeUser.this, formPelanggan.class);
                startActivity(login);
            }
        });

        lihat = findViewById(R.id.lihatData);
        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(homeUser.this, dataUser.class);
                startActivity(login);
            }
        });
    }
}