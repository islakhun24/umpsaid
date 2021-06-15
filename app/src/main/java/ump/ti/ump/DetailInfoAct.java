package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import ump.ti.ump.model.Informasi;

public class DetailInfoAct extends AppCompatActivity {
    private String key;
    private ImageView image;
    private TextView tanggal, judul, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        image = findViewById(R.id.image);
        tanggal = findViewById(R.id.tanggal);
        judul = findViewById(R.id.judul);
        content = findViewById(R.id.content);
        key = getIntent().getStringExtra("key");
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

    }
}