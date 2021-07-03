package ump.ti.ump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ump.ti.ump.adapter.InformasiAdapter;
import ump.ti.ump.adapter.JurusanAdapter;
import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;

public class JurusanAct extends AppCompatActivity implements JurusanAdapter.FirebaseDataListener{
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Jurusan> jurusans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);
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
        database.child("jurusan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                jurusans = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    Jurusan jurusan = noteDataSnapshot.getValue(Jurusan.class);
                    jurusan.setKey(noteDataSnapshot.getKey());
                    jurusan.setJurusan(dataSnapshot.child(noteDataSnapshot.getKey()).child("jurusan").getValue().toString());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    jurusans.add(jurusan);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new JurusanAdapter(jurusans, JurusanAct.this);
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
        return new Intent(activity, JurusanAct.class);
    }

    @Override
    public void onDeleteData(Jurusan jurusan, int position) {
//        Log.d("ASW", "onDeleteData: "+jurusan.getKey());
        if(database!=null){
            database.child("jurusan").child(jurusan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){

                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(JurusanAct.this, "success delete", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}