package com.example.responsi_417;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class formUpdate extends AppCompatActivity {

    protected Cursor cursor;
    EditText nama, alamat, berat;
    RadioGroup mode;
    RadioButton cuci, setrika;
    Button selesai;
    int berat2, harga, total;

    String uNama, uAlamat, uMode, uBerat;
    String sNama, sAlamat, pil, sBerat, sName;

    dataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_update);

        dbHelper = new dataHelper(this);

        Bundle terima = getIntent().getExtras();
        Intent intent = getIntent();
        String name = terima.getString("nama");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from pelanggan where nama = '" + name + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            uNama = cursor.getString(0);
            uAlamat = cursor.getString(1);
            uBerat = cursor.getString(2);
            uMode = cursor.getString(3);
        }

        EditText etNama = findViewById(R.id.etNama);
        EditText etAlamat = findViewById(R.id.etAlamat);
        EditText etBerat = findViewById(R.id.etBerat);
        RadioButton xCuci = findViewById(R.id.rbCuci);
        RadioButton xSetrika = findViewById(R.id.rbSetrika);

        etNama.setText(uNama);
        etAlamat.setText(uAlamat);
        etBerat.setText(uBerat);
        if(uMode.equals("Cuci")){
            xCuci.setChecked(true);
            xSetrika.setChecked(false);
        } else if(uMode.equals("Cuci + Setrika")){
            xCuci.setChecked(false);
            xSetrika.setChecked(true);
        }

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

                dbH.execSQL("UPDATE pelanggan SET nama = '" + sNama + "'," + "alamat = '" + sAlamat + "'," + "berat_pakaian = '" + sBerat + "'," + "mode = '" + pil + "' WHERE nama = '" + name + "'");
                dbH.execSQL("UPDATE transaksi SET nama = '" + sNama + "'," + "alamat = '" + sAlamat + "'," + "berat_pakaian = '" + sBerat + "'," + "mode = '" + pil + "'," + "total_bayar = '" + total + "' WHERE nama = '" + name + "'");


                Toast.makeText(formUpdate.this, "Anda berhasil mengupdate data Laundry :)", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(formUpdate.this, pelanggan.class);
                startActivity(i);
                finish();
            }
        });
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.infoSewa);
        toolbar.setTitle("Update Pesanan Laundry");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}