package ump.ti.ump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ump.ti.ump.adapter.MahasiswaAdapter;
import ump.ti.ump.adapter.MahasiswaJurusanAdapter;
import ump.ti.ump.model.Mahasiswa;

public class MahasiswaJurusanAct extends AppCompatActivity {

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mahasiswa> mahasiswas;
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_jurusan);
        tvHeader = (TextView) findViewById(R.id.header);
        rvView = (RecyclerView) findViewById(R.id.list);
        String name = getIntent().getStringExtra("title");

        tvHeader.setText("Mahasiswa Jurusan "+name);

        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("mahasiswa").orderByChild("jurusan").equalTo(name).addValueEventListener(new ValueEventListener() {
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
                adapter = new MahasiswaJurusanAdapter(mahasiswas, MahasiswaJurusanAct.this);
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
}