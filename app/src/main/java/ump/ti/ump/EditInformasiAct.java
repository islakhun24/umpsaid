package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import ump.ti.ump.model.Informasi;
import ump.ti.ump.model.Mahasiswa;

public class EditInformasiAct extends AppCompatActivity {
    private EditText content, judul;
    RelativeLayout image;
    ImageView images;
    FloatingActionButton btnImage;
    Button btnSimpan, btnKembali;
    private Uri mImageUri;

    private ProgressBar uploadProgressBar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    FirebaseStorage storage;
    StorageReference storageReference;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;
    String key, contentS, judulS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_informasi);

        content = findViewById(R.id.content);
        judul = findViewById(R.id.judul);
        image = findViewById(R.id.image);
        images = findViewById(R.id.images);
        btnImage = findViewById(R.id.btnImage);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnKembali = findViewById(R.id.btnKembali);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        uploadProgressBar = findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference("informasi");

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(EditInformasiAct.this, "Sedang memproses ...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        key = getIntent().getStringExtra("key");
        DatabaseReference ref = mDatabaseRef.child("informasi").child(key);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            Informasi informasi = snapshot.getValue(Informasi.class);
                            Picasso.get()
                                    .load(informasi.getImage())
                                    .placeholder(R.drawable.empty)
                                    .fit()
                                    .centerCrop()
                                    .into(images);
                            content.setText(informasi.getContent());
                            judul.setText(informasi.getJudul());
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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(images);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            //next work with URL
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                                    uploadProgressBar.setIndeterminate(false);
                                                    uploadProgressBar.setProgress(0);
                                                }
                                            }, 500);
                                            Date date = new Date(); // Th
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                                            Toast.makeText(EditInformasiAct.this, "Data  Upload successful", Toast.LENGTH_LONG).show();
                                            Informasi informasi = new Informasi(judul.getText().toString().trim(),
                                                    fileLink,
                                                    content.getText().toString().trim(), formatter.format(date));
//                                            String uploadId = mDatabaseRef.push().getKey();
//                                            mDatabaseRef.child(uploadId).setValue(informasi);
                                            mDatabaseRef.child("informasi")
                                                    .child(key)
                                                    .setValue(informasi)
                                                    .addOnSuccessListener(EditInformasiAct.this, new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(EditInformasiAct.this, "data berhasil di edit", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                            uploadProgressBar.setVisibility(View.INVISIBLE);
                                            finish();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditInformasiAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
        }
    }
}