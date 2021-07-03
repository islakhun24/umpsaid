package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;

public class EditJurusanAct extends AppCompatActivity {
    EditText etJurusan;
    private DatabaseReference database;
    Button btnAdd;
    private String key, namaJurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jurusan);
        btnAdd = findViewById(R.id.btnAdd);
        etJurusan = findViewById(R.id.etJurusan);
        database = FirebaseDatabase.getInstance().getReference();
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!isEmpty(etJurusan.getText().toString())){
                    namaJurusan = etJurusan.getText().toString();

                    Jurusan jurusan = new Jurusan(namaJurusan);
                    database.child("jurusan")
                            .child(key)
                            .setValue(jurusan)
                            .addOnSuccessListener(EditJurusanAct.this, new OnSuccessListener<Void>(){

                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(EditJurusanAct.this, "data berhasil di edit", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                } else {
                    etJurusan.setError("nama jurusan tidak boleh kosong");
                }
            }
        });

        key = getIntent().getStringExtra("key");
        DatabaseReference ref = database.child("jurusan").child(key);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            Jurusan jurusan = snapshot.getValue(Jurusan.class);
                            etJurusan.setText(jurusan.getJurusan());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
}