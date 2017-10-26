package com.iqbalproject.aplikasigis;

//class ini digunakan untuk proses Edit & Hapus data

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iqbalproject.aplikasigis.Adapter.ListViewAdapter;
import com.iqbalproject.aplikasigis.Pojo.data_tempat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTempat extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvTotalLokasi, tvNama, tvAlamat, tvKel, tvStatus, tvJenis, tvLat, tvLong;
    private ListView lvTempat;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<data_tempat> listTempat = new ArrayList<data_tempat>();  //untuk memasukkan data_tempat ke array
    private ListViewAdapter adapterListView;
    private EditText etNamaTempat, etAlamat, etLatitude, etLongitude, etKelurahan, etStatus, etJenis;
    private String idTem, namTem, alamat, kelurahan, status, jenis;
    private Double latitude, longitude;

    private AlertDialog.Builder dialogForm;
    private LayoutInflater inflater;
    private View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tempat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvTotalLokasi = (TextView) findViewById(R.id.tvTotalLokasi);
        lvTempat = (ListView) findViewById(R.id.lvDataTempat);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        if (Server.Status_Proses.equals("Edit")){
            getSupportActionBar().setTitle("Klik Untuk Edit Lokasi");

            //event saat item list diklik
            lvTempat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idTem = listTempat.get(position).getId();
                    namTem = listTempat.get(position).getNama();
                    alamat = listTempat.get(position).getAlamat();
                    latitude = listTempat.get(position).getLatitude();
                    longitude = listTempat.get(position).getLongitude();
                    kelurahan = listTempat.get(position).getKelurahan();
                    status = listTempat.get(position).getStatus();
                    jenis = listTempat.get(position).getJenis_sekolah();

                    DialogFormEdit(idTem, namTem, alamat, latitude, longitude, kelurahan, status, jenis);
                }
            });

        }else if (Server.Status_Proses.equals("Hapus")){
            getSupportActionBar().setTitle("Klik Untuk Hapus Lokasi");

            lvTempat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idTem = listTempat.get(position).getId();
                    namTem = listTempat.get(position).getNama();
                    alamat = listTempat.get(position).getAlamat();
                    latitude = listTempat.get(position).getLatitude();
                    longitude = listTempat.get(position).getLongitude();
                    kelurahan = listTempat.get(position).getKelurahan();
                    status = listTempat.get(position).getStatus();
                    jenis = listTempat.get(position).getJenis_sekolah();

                    DialogFormHapus(idTem, namTem, alamat, latitude, longitude, kelurahan, status, jenis);
                }
            });
        }

        mSwipeRefresh.setOnRefreshListener(this);

        callData();
    }

    //Dialog Form Hapus Data
    private void DialogFormHapus(final String idTem, String namaTempat, String alamat, Double latitude, Double longitude, String kelurahan, String status, String jenis) {
        dialogForm = new AlertDialog.Builder(DataTempat.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_detail_tempat, null);
        dialogForm.setView(dialogView);
        dialogForm.setCancelable(false);
        dialogForm.setTitle("Hapus Data");
        dialogForm.setIcon(R.mipmap.ic_delete);

        tvNama = (TextView) dialogView.findViewById(R.id.tvNama);
        tvAlamat = (TextView) dialogView.findViewById(R.id.tvAlamat);
        tvKel = (TextView) dialogView.findViewById(R.id.tvKel);
        tvStatus = (TextView) dialogView.findViewById(R.id.tvStatus);
        tvJenis = (TextView) dialogView.findViewById(R.id.tvJenis);
        tvLat = (TextView) dialogView.findViewById(R.id.tvLatitude);
        tvLong = (TextView) dialogView.findViewById(R.id.tvLongitude);

        tvNama.setText(namaTempat);
        tvAlamat.setText(alamat);
        tvKel.setText(kelurahan);
        tvStatus.setText(status);
        tvJenis.setText(jenis);
        tvLat.setText(String.valueOf(latitude));
        tvLong.setText(String.valueOf(longitude));

        dialogForm.setPositiveButton("Hapus Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String url = Server.URL + "delete_lokasi.php"; // baca data ke server

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Hapus Data", response.toString());

                        try{
                            JSONObject objekData = new JSONObject(response);
                            String queryResult = objekData.getString("result");

                            if (queryResult.equals("berhasil")){
                                dialog.dismiss();
                                callData();
                                Toast.makeText(DataTempat.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                adapterListView.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DataTempat.this, "Gagal Menghapus Data !", Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataTempat.this, "Gagal Terhubung ke Database !", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // posting parameter ke delete_lokasi.php untuk dieksekusi
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", idTem);
                        return params;
                    }
                };

                Volley.newRequestQueue(DataTempat.this).add(stringRequest);
            }
        });

        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogForm.show();
    }

    //Dialog Form Edit Data
    private void DialogFormEdit(final String idTem, String namaTempat, String alamat, Double latitude, Double longitude, String kelurahan, String status, String jenis) {
        dialogForm = new AlertDialog.Builder(DataTempat.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_tambah_data, null);
        dialogForm.setView(dialogView);
        dialogForm.setCancelable(false);
        dialogForm.setTitle("Edit Data");
        dialogForm.setIcon(R.mipmap.ic_edit);

        //deklarasi objek yg ada didalam dialog form
        etNamaTempat = (EditText) dialogView.findViewById(R.id.etNamaTempat);
        etAlamat = (EditText) dialogView.findViewById(R.id.etAlamat);
        etLatitude = (EditText) dialogView.findViewById(R.id.etLatitude);
        etLongitude = (EditText) dialogView.findViewById(R.id.etLongitude);
        etKelurahan = (EditText) dialogView.findViewById(R.id.etKelurahan);
        etStatus = (EditText) dialogView.findViewById(R.id.etStatus);
        etJenis = (EditText) dialogView.findViewById(R.id.etJenis);

        etNamaTempat.setText(namaTempat);
        etAlamat.setText(alamat);
        etLatitude.setText(String.valueOf(latitude));
        etLongitude.setText(String.valueOf(longitude));
        etKelurahan.setText(kelurahan);
        etStatus.setText(status);
        etJenis.setText(jenis);

        dialogForm.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String url = Server.URL + "update_lokasi.php"; // baca data ke server

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Edit Data", response.toString());

                        try {
                            JSONObject objekData = new JSONObject(response);
                            String queryResult = objekData.getString("result");

                            //cek JSon result yang dihasilkan dari update_lokasi.php
                            if (queryResult.equals("berhasil")){
                                dialog.dismiss();
                                callData();
                                Toast.makeText(DataTempat.this, "Data Berhasil Di Perbarui", Toast.LENGTH_LONG).show();
                                adapterListView.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DataTempat.this, "Gagal Memperbarui Data !", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataTempat.this, "Gagal Terhubung ke Database !", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // posting parameter ke update_lokasi.php untuk dieksekusi
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", idTem);
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

                Volley.newRequestQueue(DataTempat.this).add(stringRequest);

            }
        });

        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogForm.show();
    }

    //mengambil data dari server
    private void callData() {
        String url = Server.URL + "read_lokasi.php"; // baca data ke server
        listTempat.clear();
        mSwipeRefresh.setRefreshing(true);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Data Lokasi", response.toString());

                tvTotalLokasi.setText(String.valueOf(response.length()));

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject objekData = response.getJSONObject(i);

                        data_tempat mData_tempat = new data_tempat();

                        mData_tempat.setId(objekData.getString("id"));
                        mData_tempat.setNama(objekData.getString("nama"));
                        mData_tempat.setAlamat(objekData.getString("alamat"));
                        mData_tempat.setKelurahan(objekData.getString("kelurahan"));
                        mData_tempat.setStatus(objekData.getString("status"));
                        mData_tempat.setJenis_sekolah(objekData.getString("jenis_sekolah"));
                        mData_tempat.setLatitude(objekData.getDouble("latitude"));
                        mData_tempat.setLongitude(objekData.getDouble("longitude"));

                        listTempat.add(mData_tempat);
                        adapterListView = new ListViewAdapter(DataTempat.this, listTempat);
                        lvTempat.setAdapter(adapterListView);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                adapterListView.notifyDataSetChanged(); //memberikan efek ketika data berubah/sedang di refresh
                mSwipeRefresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DataTempat.this, "Gagal terhubung ke database !", Toast.LENGTH_SHORT).show();
                tvTotalLokasi.setText("0");
                mSwipeRefresh.setRefreshing(false);
            }
        });
        Volley.newRequestQueue(this).add(arrayRequest);
    }

    //untuk refresh listview dengan cara di swipe layar
    @Override
    public void onRefresh() {
        listTempat.clear();
        adapterListView.notifyDataSetChanged();
        callData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            Intent intent = new Intent(DataTempat.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataTempat.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
