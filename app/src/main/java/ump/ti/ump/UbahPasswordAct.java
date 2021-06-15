package ump.ti.ump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UbahPasswordAct extends AppCompatActivity {
    private Button btnUbahh, btnKembali;
    private EditText etPassword1, etPassword2;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        btnKembali = findViewById(R.id.btnKembali);
        btnUbahh = findViewById(R.id.btnUbah);
        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUbahh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = etPassword1.getText().toString();
                String pass2 = etPassword2.getText().toString();

                ubahPassword(pass1, pass2);
            }
        });
    }

    void ubahPassword(String pass1, String pass2){
        if (pass1.equals(pass2)) {
            String currentEmail = firebaseUser.getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, pass1);

            firebaseUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.updatePassword(pass1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(UbahPasswordAct.this, "Password was changed successfully", Toast.LENGTH_LONG).show();
                                                }
                                                finish();
                                            }
                                        });
                            } else {
                                Toast.makeText(UbahPasswordAct.this, "Authentication failed, wrong password?", Toast.LENGTH_LONG).show();
//                                loadingDialog.dismiss();
                            }
                        }
                    });
        } else {
            Toast.makeText(UbahPasswordAct.this, "Passwords tidak sama", Toast.LENGTH_LONG).show();
        }

    }
    public interface ChangePasswordListener {
        void onChangePassword(String oldPassword, String newPassword);
    }

    public ChangePasswordListener listener;
}