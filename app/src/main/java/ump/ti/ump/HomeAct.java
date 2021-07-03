package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;
import ump.ti.ump.model.User;

import static android.provider.Settings.System.DATE_FORMAT;

public class HomeAct extends AppCompatActivity {
    LinearLayout inputInformasi, inputJurusan, inputMahasiswa, signout, jurusan, mahasiswas, informasi, profil,change_password;
    private String id;
    RelativeLayout notif;
    private FirebaseAuth mAuth;
    private CardView cvInput;
    private DatabaseReference database;
    private TextView pesan;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid = currentFirebaseUser.getUid();
        inputInformasi = findViewById(R.id.inputInformasi);
        inputJurusan = findViewById(R.id.inputJurusan);
        inputMahasiswa = findViewById(R.id.inputMahasiswa);
        jurusan = findViewById(R.id.jurusan);
        mahasiswas = findViewById(R.id.mahasiswa);
        informasi = findViewById(R.id.informasi);
        signout = findViewById(R.id.lnSignout);
        cvInput = findViewById(R.id.cvInput);
        profil = findViewById(R.id.profile);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        id = mAuth.getCurrentUser().getUid();
        notif = findViewById(R.id.notif);
        pesan = findViewById(R.id.pesan);
        change_password= findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAct.this, UbahPasswordAct.class));
            }
        });
        database.child("mahasiswa").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Mahasiswa mahasiswa= dataSnapshot.getValue(Mahasiswa.class);
                    String masa_berlaku = mahasiswa.getMasa_berlaku();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(new Date());
//                    Toast.makeText(getApplicationContext(),""+findDifference(date,masa_berlaku),Toast.LENGTH_LONG).show();
                    if(Integer.parseInt(String.valueOf(findDifference(date,masa_berlaku)))<=365){
                        pesan.setText("Masa Berlaku anda kurang dari "+findDifference(date,masa_berlaku)+" hari lagi. Silahkan perpanjang masa berlaku pasport anda");
                        notif.setVisibility(View.VISIBLE);
                    }
                    cvInput.setVisibility(View.GONE);
                    mahasiswas.setVisibility(View.VISIBLE);
                    profil.setVisibility(View.GONE);
                }else {
                    cvInput.setVisibility(View.VISIBLE);
                    mahasiswas.setVisibility(View.VISIBLE);
                    profil.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(HomeAct.this, LoginAct.class));
                finish();
            }
        });
        mahasiswas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAct.this, MahasiswaAct.class));
            }
        });
        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAct.this, InformasiAct.class));
            }
        });
        jurusan.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeAct.this, JurusanAct.class));
            }
        }));
        inputInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeAct.this, InputInformasiAct.class));
            }
        });

        inputJurusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeAct.this, InputJurusanAct.class));
            }
        });

        inputMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeAct.this, InputMahasiwaAct.class));
            }
        });
    }
    public static final String DATE_FORMAT = "dd-mm-yyyy";

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public static long findDifference(String start_date,
                               String end_date)
    {
        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy");
        long difference_In_Days = 0;

        // Try Class
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            difference_In_Days
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    % 365;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return difference_In_Days;
    }
}