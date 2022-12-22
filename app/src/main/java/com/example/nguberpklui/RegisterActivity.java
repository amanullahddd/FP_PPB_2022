package com.example.nguberpklui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private Button btntologin, btndaftar;
    private EditText edtemail, edtpassword, edtnama, edtconfirmpassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();

        btndaftar = findViewById(R.id.btn_daftar);
        btntologin = findViewById(R.id.btn_to_login);

        edtnama = findViewById(R.id.edt_register_nama);
        edtemail = findViewById(R.id.edt_register_email);
        edtpassword = findViewById(R.id.edt_register_password);
        edtconfirmpassword = findViewById(R.id.edt_register_password_confirm);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Sedang memuat");
        progressDialog.setMessage("Mohon ditunggu!");
        progressDialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();

        btndaftar.setOnClickListener(op);
        btntologin.setOnClickListener(op);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_to_login:tologin();break;
                case R.id.btn_daftar:
                    if (edtnama.getText().length()>0 && edtemail.getText().length()>0 && edtpassword.getText().length()>0 && edtconfirmpassword.getText().length()>0) {
                        if (edtpassword.getText().toString().equals(edtconfirmpassword.getText().toString())) {
                            daftarAccount(edtnama.getText().toString(), edtemail.getText().toString(), edtpassword.getText().toString(), edtconfirmpassword.getText().toString());
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Silakan konfirmasi password yang sama", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Silakan lengkapi semua data", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload () {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private void tologin () {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void daftarAccount (String nama, String email, String password, String confirmpassword) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nama)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Daftar akun tidak berhasil!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}