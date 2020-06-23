package com.example.ayoberbagi_mysql;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterRelawan extends AppCompatActivity {

    EditText editTextNama, editTextKTP, editNoTelp, editTextEmail, editNoRek;
    Spinner bank;
    Button keRegister2;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_relawan);

        editTextNama = findViewById(R.id.editTextNama);
        editTextKTP = findViewById(R.id.editTextKTP);
        editNoTelp = findViewById(R.id.editNoTelp);
        editTextEmail = findViewById(R.id.editTextEmail);
        bank = findViewById(R.id.nama_bank);
        editNoRek = findViewById(R.id.editNoRek);
        keRegister2 = findViewById(R.id.keRegister2);
        login = findViewById(R.id.login);

        keRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keRegister2();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterRelawan.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void keRegister2() {
        if (validate()) {
            Intent intent = new Intent(RegisterRelawan.this, RegisterRelawan2.class);
            intent.putExtra("nama", editTextNama.getText().toString());
            intent.putExtra("no_ktp", editTextKTP.getText().toString());
            intent.putExtra("no_telp", editNoTelp.getText().toString());
            intent.putExtra("email", editTextEmail.getText().toString());
            intent.putExtra("nama_bank", bank.getSelectedItem().toString());
            intent.putExtra("no_rek", editNoRek.getText().toString());
            startActivity(intent);
        }
    }

    public boolean validate() {
        boolean valid = false;
        boolean valid_nama = false;
        boolean valid_ktp = false;
        boolean valid_telp = false;
        boolean valid_email = false;
        boolean valid_rek = false;


        //Get values from EditText fields
        String Nama = editTextNama.getText().toString();
        String KTP = editTextKTP.getText().toString();
        String NoTelp = editNoTelp.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Norek = editNoRek.getText().toString();

        //Handling validation for Email field
        if (Nama.isEmpty()) {
            valid_nama = false;
            editTextNama.setError("Nama tidak boleh kosong!");
        } else {
            valid_nama = true;
            editTextNama.setError(null);
        }

        //Handling validation for KTP field
        if (KTP.isEmpty()) {
            valid_ktp = false;
            editTextKTP.setError("Nomor KTP tidak boleh kosong!");
        } else if (KTP.length() < 16 || KTP.length() > 16) {
            valid_ktp = false;
            editTextKTP.setError("KTP harus 16 karakter!");
        } else {
            valid_ktp = true;
            editTextKTP.setError(null);
        }

        if (NoTelp.isEmpty()) {
            valid_telp = false;
            editNoTelp.setError("Nomor telepon tidak boleh kosong!");
        } else {
            valid_telp = true;
            editNoTelp.setError(null);
        }

        if (Norek.isEmpty()) {
            valid_rek = false;
            editNoRek.setError("Nomor rekening tidak boleh kosong!");
        } else {
            valid_rek = true;
            editNoRek.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid_email = false;
            editTextEmail.setError("Email tidak valid!");
        } else if (Email.isEmpty()) {
            valid_email = false;
            editTextEmail.setError("Email tidak boleh kosong!");
        } else {
            valid_email = true;
            editTextEmail.setError(null);
        }

        if (valid_telp && valid_email && valid_ktp && valid_nama && valid_rek) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}