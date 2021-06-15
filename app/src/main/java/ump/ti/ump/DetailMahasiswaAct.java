package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import ump.ti.ump.model.Informasi;
import ump.ti.ump.model.Mahasiswa;

public class DetailMahasiswaAct extends AppCompatActivity {
    EditText tvKeterangan,etJurusan,etAgama, tvNim,tvNama_mahasiswa,etTempat_lahir,etKewarganegaraan,etEmail, etTelepon, etAlamat, etNamaSekolah, etTahunLulus, etAlamatSekolah, etNamaOrangTua, etTeleponOrtu, etAlamatOrtu,etPassword, tvNoItas, tvNopaspor ;
    private String key;
    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);
        tvNim = findViewById(R.id.tvNim);
        tvNama_mahasiswa = findViewById(R.id.tvNama_mahasiswa);
        etTempat_lahir = findViewById(R.id.etTempat_lahir);
        etKewarganegaraan = findViewById(R.id.etKewarganegaraan);
        etEmail = findViewById(R.id.etEmail);
        etTelepon = findViewById(R.id.etTelepon);
        etAlamat = findViewById(R.id.etAlamat);
        etNamaSekolah = findViewById(R.id.etNamaSekolah);
        etTahunLulus = findViewById(R.id.etTahunLulus);
        etAlamatSekolah = findViewById(R.id.etAlamatSekolah);
        etNamaOrangTua = findViewById(R.id.etNamaOrangTua);
        etTeleponOrtu = findViewById(R.id.etTeleponOrtu);
        etAlamatOrtu = findViewById(R.id.etAlamatOrtu);
        etJurusan = findViewById(R.id.etJurusan);
        imgView = findViewById(R.id.imgView);
        etPassword = findViewById(R.id.etPassword);
        tvKeterangan = findViewById(R.id.tvKeterangan);
        tvNopaspor = findViewById(R.id.tvNopaspor);
        key = getIntent().getStringExtra("key");
        tvNoItas = findViewById(R.id.tvNoItas);
        etAgama = findViewById(R.id.etAgama);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("mahasiswa").child(key);
        DatabaseReference refs = database.child("user").child(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            Mahasiswa mahasiswa =snapshot.getValue(Mahasiswa.class);
                            Picasso.get()
                                    .load(mahasiswa.getPhoto())
                                    .placeholder(R.drawable.empty)
                                    .fit()
                                    .centerCrop()
                                    .into(imgView);
                            tvNim.setText(mahasiswa.getNim());
                            tvNama_mahasiswa.setText(mahasiswa.getNama_mahasiswa());
                            etTempat_lahir.setText(mahasiswa.getTempat_lahir());
                            etKewarganegaraan.setText(mahasiswa.getKewarganegaraan());
//                            etEmail.setText(mahasiswa.get);
                            etTelepon.setText(mahasiswa.getTelepon());
                            etAlamat.setText(mahasiswa.getAlamat());
                            etNamaSekolah.setText(mahasiswa.getNama_sekolah());
                            etTahunLulus.setText(mahasiswa.getTahun_lulus());
                            etAlamatSekolah.setText(mahasiswa.getAlamat_sekolah());
                            etNamaOrangTua.setText(mahasiswa.getNama_orang_tua());
                            etTeleponOrtu.setText(mahasiswa.getTelp_orang_tua());
                            etAlamatOrtu.setText(mahasiswa.getAlamat_orang_tua());
                            etJurusan.setText(mahasiswa.getJurusan());
//                            imgView.setText(mahasiswa.);
//                            etPassword.setText(mahasiswa.);
                            tvKeterangan.setText(mahasiswa.getKeterangan());
                            tvNopaspor.setText(mahasiswa.getNo_paspor());
//                            key.setText(mahasiswa.);
                            tvNoItas.setText(mahasiswa.getNo_itas());
                            etAgama.setText(mahasiswa.getAgama());
                            // your name values you will get here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}