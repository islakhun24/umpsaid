package ump.ti.ump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ump.ti.ump.adapter.JurusanAdapter;
import ump.ti.ump.adapter.MahasiswaAdapter;
import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;

public class MahasiswaAct extends AppCompatActivity {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mahasiswa> mahasiswas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        rvView = (RecyclerView) findViewById(R.id.list);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data barang dari Firebase Realtime DB
         */
        database.child("mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                mahasiswas = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    Mahasiswa mahasiswa = noteDataSnapshot.getValue(Mahasiswa.class);
                    mahasiswa.setKey(noteDataSnapshot.getKey());
                    mahasiswa.setJurusan(dataSnapshot.child(noteDataSnapshot.getKey()).child("jurusan").getValue().toString());
                    mahasiswa.setJenis_kelamin(dataSnapshot.child(noteDataSnapshot.getKey()).child("jenis_kelamin").getValue().toString());
                    mahasiswa.setKewarganegaraan(dataSnapshot.child(noteDataSnapshot.getKey()).child("kewarganegaraan").getValue().toString());
                    mahasiswa.setNim(dataSnapshot.child(noteDataSnapshot.getKey()).child("nim").getValue().toString());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    mahasiswas.add(mahasiswa);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new MahasiswaAdapter(mahasiswas, MahasiswaAct.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, MahasiswaAct.class);
    }

}