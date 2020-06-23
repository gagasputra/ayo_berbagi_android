package com.example.ayoberbagi_mysql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterRelawan2 extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editKonfPassword, editJawaban;
    String nama, email, no_ktp, no_telp, bank, no_rek;
    Spinner Pertanyaan;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_relawan_2);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editKonfPassword = findViewById(R.id.editTextKonfPassword);
        Pertanyaan = findViewById(R.id.pertanyaan);
        editJawaban = findViewById(R.id.jawaban);

        intent = getIntent();
        nama = intent.getStringExtra("nama");
        email = intent.getStringExtra("email");
        no_ktp = intent.getStringExtra("no_ktp");
        no_telp = intent.getStringExtra("no_telp");
        bank = intent.getStringExtra("nama_bank");
        no_rek = intent.getStringExtra("no_rek");

        Log.d("intent", "hasil = " + nama+","+email+","+no_ktp+","+no_telp+","+bank+","+no_rek);
    }

    public void register(View view) {
        if (validate()) {
            Intent i = new Intent(this, RegisterRelawan3.class);
            i.putExtra("nama", nama);
            i.putExtra("no_ktp", no_ktp);
            i.putExtra("no_telp", no_telp);
            i.putExtra("email", email);
            i.putExtra("bank", bank);
            i.putExtra("no_rek", no_rek);
            i.putExtra("username", editTextUsername.getText().toString());
            i.putExtra("password", editTextPassword.getText().toString());
            i.putExtra("pertanyaan", Pertanyaan.getSelectedItem().toString());
            i.putExtra("jawaban", editJawaban.getText().toString());
            startActivity(i);
        }
    }

    public boolean validate() {
        boolean valid = false;
        boolean valid_user = false;
        boolean valid_pass = false;
        boolean valid_conf = false;
        boolean valid_jawaban = false;

        //Get values from EditText fields
        String Username = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();
        String KonfPassword = editKonfPassword.getText().toString();
        String Jawaban = editJawaban.getText().toString();

        //Handling validation for Email field
        if (Username.isEmpty()) {
            valid_user = false;
            editTextUsername.setError("Username tidak boleh kosong!");
        } else if (Username.length() < 5) {
            valid_user = false;
            editTextUsername.setError("Username minimal 6 karakter!");
        } else {
            valid_user = true;
            editTextUsername.setError(null);
        }

        //Handling validation for KTP field
        if (Password.isEmpty()) {
            valid_pass = false;
            editTextPassword.setError("Password tidak boleh kosong!");
        } else if (Password.length() > 7) {
            valid_pass = true;
            editTextPassword.setError(null);
        } else {
            valid_pass = false;
            editTextPassword.setError("Password minimal 8 karakter!");
        }

        //
        if (!KonfPassword.equals(Password)) {
            valid_conf = false;
            editKonfPassword.setError("Konfirmasi password tidak sama!");
        } else if (KonfPassword.isEmpty()) {
            valid_conf = false;
            editKonfPassword.setError("Konfirmasi password tidak boleh kosong!");
        } else {
            valid_conf = true;
            editKonfPassword.setError(null);
        }

        if (Jawaban.isEmpty()) {
            valid_jawaban = false;
            editJawaban.setError("Jawaban tidak boleh kosong!");
        } else {
            valid_jawaban = true;
            editJawaban.setError(null);
        }

        if (valid_user && valid_pass && valid_conf && valid_jawaban) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }
}
