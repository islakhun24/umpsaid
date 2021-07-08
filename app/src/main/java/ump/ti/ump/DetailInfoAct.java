package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ump.ti.ump.adapter.KomentarAdapter;
import ump.ti.ump.adapter.MahasiswaJurusanAdapter;
import ump.ti.ump.model.Informasi;
import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Komentar;
import ump.ti.ump.model.Mahasiswa;
import ump.ti.ump.model.User;

public class DetailInfoAct extends AppCompatActivity {
    private String key;
    private ImageView image;
    private TextView tanggal, judul, content;
    private EditText etKomentar;
    private Button btnKomentar;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String id;
    public String pengirim;

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Komentar> komentars;

    private DatabaseReference database2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        image = findViewById(R.id.image);
        tanggal = findViewById(R.id.tanggal);
        judul = findViewById(R.id.judul);
        content = findViewById(R.id.content);
        etKomentar = findViewById(R.id.etKomentar);
        btnKomentar = findViewById(R.id.btnKomentar);


        rvView = (RecyclerView) findViewById(R.id.listKomentar);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        key = getIntent().getStringExtra("key");
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        id = currentFirebaseUser.getUid();
        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("informasi").child(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            Informasi informasi =snapshot.getValue(Informasi.class);
                            Picasso.get()
                                    .load(informasi.getImage())
                                    .placeholder(R.drawable.empty)
                                    .fit()
                                    .centerCrop()
                                    .into(image);
                            tanggal.setText(informasi.getTanggal());
                            judul.setText(informasi.getJudul());
                            content.setText(informasi.getContent());
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

        database2 = FirebaseDatabase.getInstance().getReference();
        database2.child("komentar").orderByChild("keyInfo").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                komentars = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    Komentar komentar = noteDataSnapshot.getValue(Komentar.class);
                    komentar.setPengirim(snapshot.child(noteDataSnapshot.getKey()).child("pengirim").getValue().toString());
                    komentar.setKomentar(snapshot.child(noteDataSnapshot.getKey()).child("komentar").getValue().toString());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    komentars.add(komentar);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new KomentarAdapter(komentars, DetailInfoAct.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnKomentar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference ref = database.child("admins").child(id);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        try {
                            if (snapshot.getValue() != null) {
                                try {
                                    User user =snapshot.getValue(User.class);
                                    pengirim = user.getLevel();

                                    if (pengirim.equals("admin") ){
                                        Komentar komentar = new Komentar(key,
                                                pengirim,
                                                etKomentar.getText().toString().trim());
                                        submitKomentar(komentar);
//                                        Toast.makeText(DetailInfoAct.this, key+" "+pengirim+" "+etKomentar.getText().toString(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        DatabaseReference ref1 = database.child("mahasiswa").child(id);
                                        ref1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                try {
                                                    if (snapshot.getValue() != null) {
                                                        try {
                                                            Mahasiswa mahasiswa =snapshot.getValue(Mahasiswa.class);
                                                            pengirim = mahasiswa.getNama_mahasiswa();
                                                            Komentar komentar = new Komentar(key,
                                                                    pengirim,
                                                                    etKomentar.getText().toString().trim());
                                                            submitKomentar(komentar);
//                                                            Toast.makeText(DetailInfoAct.this, ""+pengirim, Toast.LENGTH_SHORT).show();
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
        });

    }

    private void submitKomentar(Komentar komentar) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference();
        database1.child("komentar").push().setValue(komentar).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etKomentar.setText("");
            }
        });
    }
}