package com.example.responsi_417;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class formPelanggan extends AppCompatActivity {

    EditText nama, alamat, berat;
    RadioGroup mode;
    RadioButton cuci, setrika;
    Button selesai;
    int berat2, harga, total;

    String sNama, sAlamat, pil, sBerat;

    dataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pelanggan);

        dbHelper = new dataHelper(this);

        nama = findViewById(R.id.etNama);
        alamat = findViewById(R.id.etAlamat);
        berat = findViewById(R.id.etBerat);
        mode = findViewById(R.id.modeGroup);
        cuci = findViewById(R.id.rbCuci);
        setrika = findViewById(R.id.rbSetrika);
        selesai = findViewById(R.id.selesai);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNama = nama.getText().toString();
                sAlamat = alamat.getText().toString();
                sBerat = berat.getText().toString();
                if (sNama.isEmpty() || sAlamat.isEmpty() || sBerat.isEmpty()) {
                    Toast.makeText(formPelanggan.this, "Masih ada data yang kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cuci.isChecked()) {
                    pil = "Cuci";
                    harga = 6000;
                } else if (setrika.isChecked()) {
                    pil = "Cuci + Setrika";
                    harga = 10000;
                }

                berat2 = Integer.parseInt(sBerat);
                total = (harga * berat2);

                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                dbH.execSQL("INSERT INTO pelanggan (nama, alamat, berat_pakaian, mode) VALUES ('" +
                        sNama + "','" +
                        sAlamat + "','" +
                        sBerat + "','" +
                        pil + "');");
                dbH.execSQL("INSERT INTO transaksi (nama, alamat, berat_pakaian, mode, total_bayar) VALUES ('" +
                        sNama + "','" +
                        sAlamat + "','" +
                        sBerat + "','" +
                        pil + "','" +
                        total + "');");
//                PenyewaActivity.m.RefreshList();
                Toast.makeText(formPelanggan.this, "Anda berhasil memesan Laundry :)", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.infoSewa);
        toolbar.setTitle("Pesan Laundry");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}