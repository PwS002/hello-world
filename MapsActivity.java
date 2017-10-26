package com.iqbalproject.aplikasigis;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.iqbalproject.aplikasigis.Pojo.data_tempat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private data_tempat mData_tempat;
    private List<data_tempat> listTempat = new ArrayList<data_tempat>();  //untuk memasukkan data_tempat ke array
    int nomorData;
    Boolean [] markerIntent;
    LatLng mLatLng[];
    AlertDialog.Builder mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLokasi();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true); // untuk menampilkan button Current Location
        mMap.getUiSettings().setZoomControlsEnabled(true); // menampilkan button zoom in & zoom out
        mMap.getUiSettings().setMapToolbarEnabled(true); // menampilkan button map & navigasi
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // setting type map

        // permission untuk dapat mengaktifkan button Current Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MapsActivity.this, "Mencari Lokasi ...", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void getLokasi(){
        String url = Server.URL + "read_lokasi.php"; // baca data ke server

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                nomorData = response.length();
                // Untuk menampilkan JSON dari databasenya(tampil pada Android Monitor saat aplikasi dirunning)
                Log.d(MapsActivity.class.getSimpleName(), response.toString());

                // menentukan indeks arraynya
                mLatLng = new LatLng[nomorData];
                markerIntent = new Boolean[nomorData];

                for (int i = 0; i < nomorData; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        mData_tempat = new data_tempat();

                        // menyimpan data tiap field ke variabel id nama alamat dll
                        mData_tempat.setId(data.getString("id"));
                        mData_tempat.setNama(data.getString("nama"));
                        mData_tempat.setAlamat(data.getString("alamat"));
                        mData_tempat.setKelurahan(data.getString("kelurahan"));
                        mData_tempat.setStatus(data.getString("status"));
                        mData_tempat.setJenis_sekolah(data.getString("jenis_sekolah"));
                        mData_tempat.setLatitude(data.getDouble("latitude"));
                        mData_tempat.setLongitude(data.getDouble("longitude"));
                        mLatLng[i] = new LatLng(mData_tempat.getLatitude(), mData_tempat.getLongitude());

                        markerIntent[i] = false; // hanya untuk tanda

                        listTempat.add(mData_tempat); //memasukkan mData_tempat ke array listTempat

                        String jnsSekolah = listTempat.get(i).getJenis_sekolah().toString();

                        if (jnsSekolah.equals("SD") || jnsSekolah.equals("sd")){
                            mMap.addMarker(new MarkerOptions()
                                    .position(mLatLng[i]) //posisi latitude & longitude
                                    .title(listTempat.get(i).getNama())
                                    .snippet(listTempat.get(i).getAlamat()) //subtitle marker
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_sd))); // icon pada marker map
                        }else if (jnsSekolah.equals("SMP") || jnsSekolah.equals("smp")){
                            mMap.addMarker(new MarkerOptions()
                                    .position(mLatLng[i])
                                    .title(listTempat.get(i).getNama())
                                    .snippet(listTempat.get(i).getAlamat())
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_smp)));
                        }else if (jnsSekolah.equals("SMA") || jnsSekolah.equals("sma")){
                            mMap.addMarker(new MarkerOptions()
                                    .position(mLatLng[i])
                                    .title(listTempat.get(i).getNama())
                                    .snippet(listTempat.get(i).getAlamat())
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_sma)));
                        }else{
                            mMap.addMarker(new MarkerOptions()
                                    .position(mLatLng[i])
                                    .title(listTempat.get(i).getNama())
                                    .snippet(listTempat.get(i).getAlamat())
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_tanya)));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng[i], 15));  // agar ketika map dibuka, kamera langsung tertuju pada posisi yang dituju
                }

                // marker klik
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Log.d("DEBUG_", "Marker Clicked");
                        for (int i = 0; i < nomorData; i++){
                            if (marker.getTitle().equals(listTempat.get(i).getNama())){
                                if (markerIntent[i] == true){
                                    DetailTempat.id = listTempat.get(i).getId();
                                    DetailTempat.namaTempat = listTempat.get(i).getNama();
                                    DetailTempat.Alamat = listTempat.get(i).getAlamat();
                                    DetailTempat.kelurahan = listTempat.get(i).getKelurahan();
                                    DetailTempat.status = listTempat.get(i).getStatus();
                                    DetailTempat.jenis_sekolah = listTempat.get(i).getJenis_sekolah();
                                    DetailTempat.latitude = listTempat.get(i).getLatitude().toString();
                                    DetailTempat.longitude = listTempat.get(i).getLongitude().toString();

                                    startActivity(new Intent(MapsActivity.this, DetailTempat.class));
                                    markerIntent[i] = false;
                                }else {
                                    markerIntent[i] = true;
                                    Toast.makeText(MapsActivity.this, "Tekan sekali lagi untuk melihat detail tempat", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                markerIntent[i] = false;
                            }
                        }
                        return false;
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //menampilkan alert ketika tidak terhubung ke internet
                mAlertDialog = new AlertDialog.Builder(MapsActivity.this);
                mAlertDialog.setTitle("Perhatian..");
                mAlertDialog.setMessage("Mohon periksa koneksi internet Anda !");
                mAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                mAlertDialog.setCancelable(false); // agar tidak dpt di cancel saat terjadi error

                mAlertDialog.setPositiveButton("Mulai Lagi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLokasi();
                        dialog.dismiss();
                    }
                });

                mAlertDialog.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mAlertDialog.show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
