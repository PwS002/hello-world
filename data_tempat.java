package com.iqbalproject.aplikasigis.Pojo;

/**
 * Created by IQBAL-PC on 07-Apr-17.
 */

public class data_tempat {

    private String id, nama, alamat, kelurahan, status, jenis_sekolah;
    private Double latitude, longitude;

    public data_tempat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getJenis_sekolah() {
        return jenis_sekolah;
    }

    public void setJenis_sekolah(String jenis_sekolah) {
        this.jenis_sekolah = jenis_sekolah;
    }
}
