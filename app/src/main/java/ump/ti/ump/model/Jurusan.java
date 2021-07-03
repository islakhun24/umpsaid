package ump.ti.ump.model;

public class Jurusan {
    private String jurusan;
    private String key;

    public Jurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public Jurusan() {
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
