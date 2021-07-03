package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import ump.ti.ump.fragment.DatePickerFragment;
import ump.ti.ump.model.Mahasiswa;

public class EditMahasiswaEditAct extends AppCompatActivity {

    TextView etTanggal_lahir;
    private FloatingActionButton btnSelect;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    private ImageView btnMasaBerlaku;
    ProgressDialog progressDialog ;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView btnDate;
    Button btnSimpan, btnKembali;
    private TextView tvMasaBerlaku;
    EditText tvKeterangan, tvNim,tvNama_mahasiswa,etTempat_lahir,etKewarganegaraan,etEmail, etTelepon, etAlamat, etNamaSekolah, etTahunLulus, etAlamatSekolah, etNamaOrangTua, etTeleponOrtu, etAlamatOrtu,etPassword, tvNoItas, tvNopaspor ;
    private String nim, nama_mahasiswa, tempat_lahir, tanggal_lahir, agama, jurusan, kewarganegaraan, jenis_kelamin, email,telepon, alamat;
    private  String nama_sekolah, tahun_lulus, alamat_sekolah;
    private String nama_orang_tua, telp_orang_tua, alamat_orang_tua;
    private String no_itas, no_paspor, masa_berlaku, keterangan, photo;
    private String password;
    Spinner spJurusan,spAgama;
    private ArrayList<String> jurusans;
    private DatabaseReference database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa_edit);

        etTanggal_lahir = findViewById(R.id.etTanggal_lahir);
        btnDate = findViewById(R.id.btnDate);
        database = FirebaseDatabase.getInstance().getReference();
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
        spJurusan = findViewById(R.id.spJurusan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnKembali = findViewById(R.id.btnKembali);
        etPassword = findViewById(R.id.etPassword);
        tvKeterangan = findViewById(R.id.tvKeterangan);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tvNopaspor = findViewById(R.id.tvNopaspor);
        btnMasaBerlaku = findViewById(R.id.btnMasaBerlaku);
        tvMasaBerlaku = findViewById(R.id.tvMasaBerlaku);
        tvNoItas = findViewById(R.id.tvNoItas);
        spAgama = findViewById(R.id.spAgama);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSelect = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgView);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath != null){
                    progressDialog = new ProgressDialog(EditMahasiswaEditAct.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference ref = storageReference.child("mahasiswa/"+ key);
                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>(){

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            EditMahasiswa(fileLink);

                                        }
                                    }
                            );
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(EditMahasiswaEditAct.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }else{
                    Toast.makeText(EditMahasiswaEditAct.this, "isi foto baru!", Toast.LENGTH_SHORT).show();
                }

//                email = etEmail.getText().toString();
//                password = etPassword.getText().toString();
//                uploadImage(email,password);
//                signUp(email,password);
//                submitMahasiswa(new Mahasiswa( nim,  nama_mahasiswa,  tempat_lahir,  tanggal_lahir,  agama,  jurusan,  kewarganegaraan,  jenis_kelamin,  email,  telepon,  alamat,  nama_sekolah,  tahun_lulus,  alamat_sekolah,  nama_orang_tua,  telp_orang_tua,  alamat_orang_tua,  password));
            }
        });

        etTanggal_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TampilTanggal();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TampilTanggal();
            }
        });
        btnMasaBerlaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TampilTanggal1();
            }
        });

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

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    jurusans.add(dataSnapshot.child(noteDataSnapshot.getKey()).child("jurusan").getValue().toString());
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(EditMahasiswaEditAct.this, android.R.layout.simple_spinner_item, jurusans);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spJurusan.setAdapter(areasAdapter);
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
        key = getIntent().getStringExtra("key");
        DatabaseReference ref = database.child("mahasiswa").child(key);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if (snapshot.getValue() != null) {
                        try {
                            Mahasiswa mahasiswa =snapshot.getValue(Mahasiswa.class);
                            Picasso.get()
                                    .load(mahasiswa.getPhoto())
                                    .placeholder(R.drawable.empty)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView);
                            tvNim.setText(mahasiswa.getNim());
                            tvNoItas.setText(mahasiswa.getNo_itas());
                            tvNopaspor.setText(mahasiswa.getNo_paspor());
                            tvMasaBerlaku.setText(mahasiswa.getMasa_berlaku());
                            tvNama_mahasiswa.setText(mahasiswa.getNama_mahasiswa());
                            tvKeterangan.setText(mahasiswa.getKeterangan());
                            etTempat_lahir.setText(mahasiswa.getTempat_lahir());
                            etTanggal_lahir.setText(mahasiswa.getTanggal_lahir());
                            etKewarganegaraan.setText(mahasiswa.getKewarganegaraan());
                            etTelepon.setText(mahasiswa.getTelepon());
                            etAlamat.setText(mahasiswa.getAlamat());
                            etNamaSekolah.setText(mahasiswa.getNama_sekolah());
                            etTahunLulus.setText(mahasiswa.getTahun_lulus());
                            etAlamatSekolah.setText(mahasiswa.getAlamat_sekolah());
                            etNamaOrangTua.setText(mahasiswa.getNama_orang_tua());
                            etTeleponOrtu.setText(mahasiswa.getTelp_orang_tua());
                            etAlamatOrtu.setText(mahasiswa.getAlamat_orang_tua());
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

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }


    }
    public void TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = hari+"-"+bulan+"-"+tahun;
                etTanggal_lahir.setText(text);
            }
        });
    }
    public void TampilTanggal1(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = hari+"-"+bulan+"-"+tahun;
                tvMasaBerlaku.setText(text);
            }
        });
    }

    private void EditMahasiswa(String foto){
        nim = tvNim.getText().toString();
        no_itas = tvNoItas.getText().toString();
        no_paspor = tvNopaspor.getText().toString();
        masa_berlaku = tvMasaBerlaku.getText().toString();
        nama_mahasiswa = tvNama_mahasiswa.getText().toString();
        keterangan = tvKeterangan.getText().toString();
        jurusan= spJurusan.getSelectedItem().toString();
        tempat_lahir = etTempat_lahir.getText().toString();
        tanggal_lahir = etTanggal_lahir.getText().toString();
        agama = spAgama.getSelectedItem().toString();
        kewarganegaraan = etKewarganegaraan.getText().toString();
        RadioGroup rg = (RadioGroup)findViewById(R.id.rg_gender);
        int selectedId = rg .getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        jenis_kelamin = radioButton.getText().toString();
        telepon = etTelepon.getText().toString();
        alamat = etAlamat.getText().toString();
        nama_sekolah = etNamaSekolah.getText().toString();
        tahun_lulus = etTahunLulus.getText().toString();
        alamat_sekolah = etAlamatSekolah.getText().toString();
        nama_orang_tua = etNamaOrangTua.getText().toString();
        telp_orang_tua = etTeleponOrtu.getText().toString();
        alamat_orang_tua = etAlamatOrtu.getText().toString();

        Mahasiswa mahasiswa = new Mahasiswa(no_itas, no_paspor, masa_berlaku, keterangan, foto, nim, nama_mahasiswa, tempat_lahir, tanggal_lahir, agama, jurusan, kewarganegaraan, jenis_kelamin, telepon, alamat, nama_sekolah, tahun_lulus, alamat_sekolah, nama_orang_tua, telp_orang_tua, alamat_orang_tua);
        database.child("mahasiswa")
                .child(key)
                .setValue(mahasiswa)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditMahasiswaEditAct.this, "data berhasil di edit", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}