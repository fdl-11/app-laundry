package com.example.responsi_417;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class dataPelanggan extends AppCompatActivity {

    protected Cursor cursor;
    String sNama, sAlamat, sMode, sBerat;
    dataHelper dbHelper;
    Button map;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tes);

        map = findViewById(R.id.btnMaps);

        Bundle terima = getIntent().getExtras();

        dbHelper = new dataHelper(this);
        Intent intent = getIntent();

        String nama = terima.getString("nama");


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from pelanggan where nama = '" + nama + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sNama = cursor.getString(0);
            sAlamat = cursor.getString(1);
            sBerat = cursor.getString(2);
            sMode = cursor.getString(3);
        }

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dataPelanggan.this, MapsActivity.class);
                i.putExtra("nama",sNama);
                startActivity(i);
            }
        });

        TextView tvNama = findViewById(R.id.JNama);
        TextView tvAlamat = findViewById(R.id.JAlamat);
        TextView tvBerat = findViewById(R.id.JBerat);
        TextView tvMode = findViewById(R.id.JMode);

        tvNama.setText(sNama);
        tvAlamat.setText(sAlamat);
        tvBerat.setText(sBerat + " kg");
        tvMode.setText(sMode);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.detailPelanggan);
        toolbar.setTitle("Detail Pelanggan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}