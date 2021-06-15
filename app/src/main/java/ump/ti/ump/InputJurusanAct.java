package ump.ti.ump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ump.ti.ump.model.Jurusan;

public class InputJurusanAct extends AppCompatActivity {
    EditText etJurusan;
    private DatabaseReference database;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_jurusan);
        btnAdd = findViewById(R.id.btnAdd);
        etJurusan = findViewById(R.id.etJurusan);
        database = FirebaseDatabase.getInstance().getReference();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(etJurusan.getText().toString()))
                    submitJurusan(new Jurusan(etJurusan.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.btnAdd), "Data jurusan tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etJurusan.getWindowToken(), 0);
            }
        });
    }
    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateJurusang(Jurusan jurusan) {
        // kodingan untuk next tutorial ya :p
    }

    private void submitJurusan(Jurusan jurusan) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("jurusan").push().setValue(jurusan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etJurusan.setText("");

                Snackbar.make(findViewById(R.id.btnAdd), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });

        finish();
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, InputJurusanAct.class);
    }
}