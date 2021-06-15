package ump.ti.ump.model;

public class Mahasiswa {
    private String no_itas, no_paspor, masa_berlaku, keterangan, photo, nim, nama_mahasiswa, tempat_lahir, tanggal_lahir, agama, jurusan, kewarganegaraan, jenis_kelamin,telepon, alamat;
    private  String nama_sekolah, tahun_lulus, alamat_sekolah;
    private String nama_orang_tua, telp_orang_tua, alamat_orang_tua;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    public Mahasiswa() {
    }

    public Mahasiswa(String no_itas, String no_paspor, String masa_berlaku, String keterangan, String photo, String nim, String nama_mahasiswa, String tempat_lahir, String tanggal_lahir, String agama, String jurusan, String kewarganegaraan, String jenis_kelamin, String telepon, String alamat, String nama_sekolah, String tahun_lulus, String alamat_sekolah, String nama_orang_tua, String telp_orang_tua, String alamat_orang_tua, String key) {
        this.no_itas = no_itas;
        this.no_paspor = no_paspor;
        this.masa_berlaku = masa_berlaku;
        this.keterangan = keterangan;
        this.photo = photo;
        this.nim = nim;
        this.nama_mahasiswa = nama_mahasiswa;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.agama = agama;
        this.jurusan = jurusan;
        this.kewarganegaraan = kewarganegaraan;
        this.jenis_kelamin = jenis_kelamin;
        this.telepon = telepon;
        this.alamat = alamat;
        this.nama_sekolah = nama_sekolah;
        this.tahun_lulus = tahun_lulus;
        this.alamat_sekolah = alamat_sekolah;
        this.nama_orang_tua = nama_orang_tua;
        this.telp_orang_tua = telp_orang_tua;
        this.alamat_orang_tua = alamat_orang_tua;
        this.key = key;
    }

    public Mahasiswa(String no_itas, String no_paspor, String masa_berlaku, String keterangan, String photo, String nim, String nama_mahasiswa, String tempat_lahir, String tanggal_lahir, String agama, String jurusan, String kewarganegaraan, String jenis_kelamin, String telepon, String alamat, String nama_sekolah, String tahun_lulus, String alamat_sekolah, String nama_orang_tua, String telp_orang_tua, String alamat_orang_tua) {
        this.no_itas = no_itas;
        this.no_paspor = no_paspor;
        this.masa_berlaku = masa_berlaku;
        this.keterangan = keterangan;
        this.photo = photo;
        this.nim = nim;
        this.nama_mahasiswa = nama_mahasiswa;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.agama = agama;
        this.jurusan = jurusan;
        this.kewarganegaraan = kewarganegaraan;
        this.jenis_kelamin = jenis_kelamin;
        this.telepon = telepon;
        this.alamat = alamat;
        this.nama_sekolah = nama_sekolah;
        this.tahun_lulus = tahun_lulus;
        this.alamat_sekolah = alamat_sekolah;
        this.nama_orang_tua = nama_orang_tua;
        this.telp_orang_tua = telp_orang_tua;
        this.alamat_orang_tua = alamat_orang_tua;
    }

    public String getNo_itas() {
        return no_itas;
    }

    public void setNo_itas(String no_itas) {
        this.no_itas = no_itas;
    }

    public String getNo_paspor() {
        return no_paspor;
    }

    public void setNo_paspor(String no_paspor) {
        this.no_paspor = no_paspor;
    }

    public String getMasa_berlaku() {
        return masa_berlaku;
    }

    public void setMasa_berlaku(String masa_berlaku) {
        this.masa_berlaku = masa_berlaku;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama_mahasiswa() {
        return nama_mahasiswa;
    }

    public void setNama_mahasiswa(String nama_mahasiswa) {
        this.nama_mahasiswa = nama_mahasiswa;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }


    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_sekolah() {
        return nama_sekolah;
    }

    public void setNama_sekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }

    public String getTahun_lulus() {
        return tahun_lulus;
    }

    public void setTahun_lulus(String tahun_lulus) {
        this.tahun_lulus = tahun_lulus;
    }

    public String getAlamat_sekolah() {
        return alamat_sekolah;
    }

    public void setAlamat_sekolah(String alamat_sekolah) {
        this.alamat_sekolah = alamat_sekolah;
    }

    public String getNama_orang_tua() {
        return nama_orang_tua;
    }

    public void setNama_orang_tua(String nama_orang_tua) {
        this.nama_orang_tua = nama_orang_tua;
    }

    public String getTelp_orang_tua() {
        return telp_orang_tua;
    }

    public void setTelp_orang_tua(String telp_orang_tua) {
        this.telp_orang_tua = telp_orang_tua;
    }

    public String getAlamat_orang_tua() {
        return alamat_orang_tua;
    }

    public void setAlamat_orang_tua(String alamat_orang_tua) {
        this.alamat_orang_tua = alamat_orang_tua;
    }

}
