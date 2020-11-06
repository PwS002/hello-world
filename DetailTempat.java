package com.iqbalproject.aplikasigis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailTempat extends AppCompatActivity {

    private TextView tvNama, tvAlamat, tvKel, tvStatus, tvJenis, tvLat, tvLong;
    public static String id, namaTempat, Alamat, kelurahan, status, jenis_sekolah, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tempat);
        getSupportActionBar().setTitle("Detail Tempat");
        getSupportActionBar().setSubtitle("NVGapps");

        //menampilkan tombol back di action bar activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvNama = (TextView) findViewById(R.id.tvNama);
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);
        tvKel = (TextView) findViewById(R.id.tvKel);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvJenis = (TextView) findViewById(R.id.tvJenis);
        tvLat = (TextView) findViewById(R.id.tvLatitude);
        tvLong = (TextView) findViewById(R.id.tvLongitude);

        tvNama.setText(namaTempat);
        tvAlamat.setText(Alamat);
        tvKel.setText(kelurahan);
        tvStatus.setText(status);
        tvJenis.setText(jenis_sekolah);
        tvLat.setText(latitude);
        tvLong.setText(longitude);
    }

    // untuk tombol back pada hp
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailTempat.this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // jika button back pada action bar di klik maka akan kembali ke MapsActivity
    // flag digunakan agar tidak akan kembali ke activity detail tempat(lagi) setelah button back pada action bar diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DetailTempat.this, MapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
