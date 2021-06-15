package ump.ti.ump.model;

public class Informasi {
    private String judul;
    private String image;
    private String key;
    private String content;
    private String tanggal;
    public Informasi() {
    }

    public Informasi(String judul, String image, String key, String content, String tanggal) {
        this.judul = judul;
        this.image = image;
        this.key = key;
        this.content = content;
        this.tanggal = tanggal;
    }

    public Informasi(String judul, String image, String content, String tanggal) {
        this.judul = judul;
        this.image = image;
        this.content = content;
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
