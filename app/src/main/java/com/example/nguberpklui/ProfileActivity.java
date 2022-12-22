package com.example.nguberpklui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {
    private TextView txtnama, txtprofilenama, txtprofileemail;
    private Button btnprofilelogout, btnprofilepassword;
    private ImageButton imgbtnprofileemail, imgbtnprofilenama, imgbtnprofileback;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        getSupportActionBar().hide();

        txtnama = findViewById(R.id.txt_hi_profile_nama);

        txtprofilenama = findViewById(R.id.txt_profile_nama);
        txtprofileemail = findViewById(R.id.txt_profile_email);

        btnprofilepassword = findViewById(R.id.btn_profile_password);
        btnprofilelogout = findViewById(R.id.btn_profile_logout);

        imgbtnprofilenama = findViewById(R.id.imgbtn_profile_nama);
        imgbtnprofileemail = findViewById(R.id.imgbtn_profile_email);
        imgbtnprofileback = findViewById(R.id.imgbtn_profile_back);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            txtnama.setText("Halo, " + firebaseUser.getDisplayName());
            txtprofilenama.setText(firebaseUser.getDisplayName());
            txtprofileemail.setText(firebaseUser.getEmail());
        }
        else {
            Toast.makeText(this, "Akun tidak berhasil masuk!", Toast.LENGTH_SHORT).show();
        }

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("Sedang memuat");
        progressDialog.setMessage("Mohon ditunggu!");
        progressDialog.setCancelable(false);

        btnprofilelogout.setOnClickListener(op);
        btnprofilepassword.setOnClickListener(op);
        imgbtnprofilenama.setOnClickListener(op);
        imgbtnprofileemail.setOnClickListener(op);
        imgbtnprofileback.setOnClickListener(op);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_profile_logout:
                    logout();
                    break;
                case R.id.btn_profile_password:
                    updatePass();
                    break;
                case R.id.imgbtn_profile_email:
                    updateEmail();
                    break;
                case R.id.imgbtn_profile_nama:
                    updateNama();
                    break;
                case R.id.imgbtn_profile_back:
                    tohome();
                    break;
            }
        }
    };

    private void tohome () {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    private void logout () {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void updatePass() {
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.dialog_profile,null);
        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);
        dialognya.setTitle("Password baru");

        final EditText edtdialogprofile = (EditText) inputnya.findViewById(R.id.edt_dialog_profile);
        dialognya
                .setCancelable(false)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        firebaseUser.updatePassword(edtdialogprofile.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this, "Password berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Password gagal diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.dismiss();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    private void updateEmail() {
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.dialog_profile,null);
        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);
        dialognya.setTitle("Email baru");

        final EditText edtdialogprofile = (EditText) inputnya.findViewById(R.id.edt_dialog_profile);
        dialognya
                .setCancelable(false)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        firebaseUser.updateEmail(edtdialogprofile.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                txtprofileemail.setText(firebaseUser.getEmail());
                                Toast.makeText(ProfileActivity.this, "Email berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Email gagal diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.dismiss();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    private void updateNama() {
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.dialog_profile,null);
        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);
        dialognya.setTitle("Nama baru");

        final EditText edtdialogprofile = (EditText) inputnya.findViewById(R.id.edt_dialog_profile);
        dialognya
                .setCancelable(false)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(edtdialogprofile.getText().toString())
                                .build();
                        firebaseUser.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                txtnama.setText(firebaseUser.getDisplayName());
                                txtprofilenama.setText(firebaseUser.getDisplayName());
                                Toast.makeText(ProfileActivity.this, "Nama berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Nama gagal diperbarui!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.dismiss();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }
}