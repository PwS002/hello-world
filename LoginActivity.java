package com.iqbalproject.aplikasigis;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgTambah, imgEdit, imgHapus;
    private LinearLayout linearLayoutAdmin; //berisi objek-objek yang muncul setelah login
    private RelativeLayout rlTambah, rlHapus, rlEdit;
    private Button mButtonLogin;
    private EditText etUsername, etPassword, etNamaTempat, etAlamat, etLatitude, etLongitude, etKelurahan, etStatus, etJenis;
    private String[] username, password; // deklarasi array
    private ProgressDialog mProgressDialog;
    int nomorData;

    private AlertDialog.Builder dialogForm;
    private LayoutInflater inflater;
    private View dialogView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Mode Administrator");
        getSupportActionBar().setSubtitle("NVGapps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mProgressDialog = new ProgressDialog(LoginActivity.this);

        rlTambah = (RelativeLayout) findViewById(R.id.rlTambahData);
        rlEdit = (RelativeLayout) findViewById(R.id.rlEditData);
        rlHapus = (RelativeLayout) findViewById(R.id.rlHapusData);
        linearLayoutAdmin = (LinearLayout) findViewById(R.id.linearLayoutAdmin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        imgTambah = (ImageView) findViewById(R.id.imgTambahData);
        imgEdit = (ImageView) findViewById(R.id.imgEditData);
        imgHapus = (ImageView) findViewById(R.id.imgHapusData);
        mButtonLogin = (Button) findViewById(R.id.btnLogin);

        rlTambah.setBackgroundResource(android.R.color.transparent);
        rlHapus.setBackgroundResource(android.R.color.transparent);
        rlEdit.setBackgroundResource(android.R.color.transparent);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekAkun();
            }
        });

        imgTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlTambah.setBackgroundResource(R.color.colorBayang);
                DialogFormTambahData();
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlEdit.setBackgroundResource(R.color.colorBayang);
                Server.Status_Proses = "Edit";
                startActivity(new Intent(LoginActivity.this, DataTempat.class));
            }
        });

        imgHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHapus.setBackgroundResource(R.color.colorBayang);
                Server.Status_Proses = "Hapus";
                startActivity(new Intent(LoginActivity.this, DataTempat.class));
            }
        });

        if (Server.STATUS_LOGIN.equals(true)) {
            etUsername.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            mButtonLogin.setVisibility(View.GONE);
            linearLayoutAdmin.setVisibility(View.VISIBLE);
        }else{
            etUsername.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.VISIBLE);
            mButtonLogin.setVisibility(View.VISIBLE);
            linearLayoutAdmin.setVisibility(View.GONE);
        }

        mProgressDialog.dismiss();
    }

    //untuk proses login
    private void cekAkun() {
        String url = Server.URL + "read_akun.php"; // baca data ke server
        final String mUsername = etUsername.getText().toString().trim();
        final String mPassword = etPassword.getText().toString().trim();
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Masuk Mode Admin ...");
        mProgressDialog.setTitle("Mohon Tunggu");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                nomorData = response.length();
                // Untuk menampilkan JSON dari databasenya(tampil pada Android Monitor saat aplikasi dirunning)
                Log.d(LoginActivity.class.getSimpleName(), response.toString());

                // menentukan indeks arraynya
                username = new String[nomorData];
                password = new String[nomorData];

                for (int i = 0; i < nomorData; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);

                        // menyimpan data tiap field database ke variabel username & password
                        username[i] = data.getString("username");
                        password[i] = data.getString("password");

                        if (TextUtils.isEmpty(mUsername)){
                            etUsername.setError("Username Harus Diisi !");
                            mProgressDialog.dismiss();
                        }else if (TextUtils.isEmpty(mPassword)){
                            etPassword.setError("Password Harus Diisi !");
                            mProgressDialog.dismiss();
                        }else{
                            if (username[i].equals(mUsername) && password[i].equals(mPassword)){
                                Toast.makeText(LoginActivity.this, "Mode Administrator Aktif", Toast.LENGTH_SHORT).show();
                                Server.STATUS_LOGIN = true;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                mProgressDialog.dismiss();
                            }else {
                                if (Server.STATUS_LOGIN == false){
                                    Toast.makeText(LoginActivity.this, "Username dan Password tidak valid", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //respon ketika tidak dapat terhubung ke server database
                Toast.makeText(LoginActivity.this, "Tidak dapat terhubung ke server database", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void DialogFormTambahData() {
        dialogForm = new AlertDialog.Builder(LoginActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_tambah_data, null);  //memasukkan tampilan form_tambah_data ke alertDialog
        dialogForm.setView(dialogView);
        dialogForm.setCancelable(false);
        dialogForm.setTitle("Tambah Data Baru");
        dialogForm.setIcon(R.mipmap.ic_input);

        //deklarasi objek yg ada didalam dialog form
        etNamaTempat = (EditText) dialogView.findViewById(R.id.etNamaTempat);
        etAlamat = (EditText) dialogView.findViewById(R.id.etAlamat);
        etLatitude = (EditText) dialogView.findViewById(R.id.etLatitude);
        etLongitude = (EditText) dialogView.findViewById(R.id.etLongitude);
        etKelurahan = (EditText) dialogView.findViewById(R.id.etKelurahan);
        etStatus = (EditText) dialogView.findViewById(R.id.etStatus);
        etJenis = (EditText) dialogView.findViewById(R.id.etJenis);

        dialogForm.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tambahData();
                rlTambah.setBackgroundResource(android.R.color.transparent);
                dialog.dismiss();
            }
        });

        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                rlTambah.setBackgroundResource(android.R.color.transparent);
            }
        });

        dialogForm.show();
    }

    //untuk tambah data ke database
    private void tambahData() {
        String url = Server.URL + "create.php";
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Mohon Tunggu ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String queryResult = jObj.getString("result");

                    //cek JSon result yang dihasilkan dari create.php
                    if (queryResult.equals("berhasil")){
                        Toast.makeText(LoginActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }else {
                        Toast.makeText(LoginActivity.this, "Proses Gagal !", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Gagal ! Mohon Periksa Koneksi ke Database Anda.", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // untuk mengirim inputan ke create.php
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", etNamaTempat.getText().toString());
                params.put("alamat", etAlamat.getText().toString());
                params.put("latitude", etLatitude.getText().toString());
                params.put("longitude", etLongitude.getText().toString());
                params.put("kelurahan", etKelurahan.getText().toString());
                params.put("status", etStatus.getText().toString());
                params.put("jenis_sekolah", etJenis.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }


    // untuk tombol back pada hp
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (Server.STATUS_LOGIN == true){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Mohon Tunggu");
            mProgressDialog.setMessage("Keluar Mode Admin ...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            //membuat delay loading saat logout selama 2 detik
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Server.STATUS_LOGIN = false;
                    Toast.makeText(LoginActivity.this, "Mode Administrator Tidak Aktif", Toast.LENGTH_SHORT).show();
                }
            }, 2000);

        }else if (id == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
