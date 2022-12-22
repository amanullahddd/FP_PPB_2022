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

public class LoginActivity extends AppCompatActivity {
    private Button btntodaftar, btnlogin;
    private EditText edtemail, edtpassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        btnlogin = findViewById(R.id.btn_login);
        btntodaftar = findViewById(R.id.btn_to_daftar);

        edtemail = findViewById(R.id.edt_login_email);
        edtpassword = findViewById(R.id.edt_login_password);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Sedang memuat");
        progressDialog.setMessage("Mohon ditunggu!");
        progressDialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(op);
        btntodaftar.setOnClickListener(op);

    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_to_daftar:toDaftar();break;
                case R.id.btn_login:
                    if (edtemail.getText().length()>0 && edtpassword.getText().length()>0) {
                        loginAccount(edtemail.getText().toString(), edtpassword.getText().toString());
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Silakan lengkapi email/password", Toast.LENGTH_SHORT).show();
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

    private void toDaftar () {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    private void loginAccount (String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    if (task.getResult().getUser()!=null) {
                        reload();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Tidak berhasil masuk!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tidak berhasil masuk!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        } );
    }
}