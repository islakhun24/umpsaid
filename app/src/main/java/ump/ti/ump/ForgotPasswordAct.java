package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordAct extends AppCompatActivity {

    EditText etEmail;
    Button btnProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnProses = findViewById(R.id.btnProses);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(etEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAGEMAIL", "Email sent.");
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            ForgotPasswordAct.this);
                                    alertDialogBuilder.setTitle("FORGOT PASSWORD");
                                    alertDialogBuilder
                                            .setMessage("Email has been sent !")
                                            .setCancelable(false)
                                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    // jika tombol diklik, maka akan menutup activity ini
                                                    ForgotPasswordAct.this.finish();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            ForgotPasswordAct.this);
                                    alertDialogBuilder.setTitle("FORGOT PASSWORD");
                                    alertDialogBuilder
                                            .setMessage("gagal")
                                            .setCancelable(false)
                                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    // jika tombol diklik, maka akan menutup activity ini
                                                    ForgotPasswordAct.this.finish();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                            }
                        });
            }
        });
    }
}