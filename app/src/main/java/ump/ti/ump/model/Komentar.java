package ump.ti.ump.model;

public class Komentar {
    public String keyInfo;
    public String pengirim;
    public String komentar;

    public Komentar(String keyInfo, String pengirim, String komentar) {
        this.keyInfo = keyInfo;
        this.pengirim = pengirim;
        this.komentar = komentar;
    }

    public Komentar() {
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
