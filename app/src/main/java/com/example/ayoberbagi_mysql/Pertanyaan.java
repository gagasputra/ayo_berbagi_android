package com.example.ayoberbagi_mysql;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.config.config;

import java.util.HashMap;
import java.util.Map;

public class Pertanyaan extends AppCompatActivity {
    Intent intent;
    Context context;
    String nama_donatur, tipe_donatur, no_ktp, email, username, password, repassword, Spertanyaan, Sjawaban;
    Spinner pertanyaan;
    EditText jawaban;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pertanyaan_rahasia);

        context = Pertanyaan.this;

        pertanyaan = findViewById(R.id.pertanyaan);
        jawaban = findViewById(R.id.jawaban);

        intent = getIntent();

        nama_donatur = intent.getStringExtra("nama_donatur");
        tipe_donatur = intent.getStringExtra("tipe_donatur");
        no_ktp = intent.getStringExtra("noktp");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        repassword = intent.getStringExtra("repassword");

        Log.d("hasil", nama_donatur+no_ktp+tipe_donatur+email+username+password+repassword);
    }

    public void register(View view) {
        Spertanyaan = pertanyaan.getSelectedItem().toString();
        Sjawaban = jawaban.getText().toString();
        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put("nama", nama_donatur);
                    params.put("tipedonatur", tipe_donatur);
                    params.put("no_ktp", no_ktp);
                    params.put("email", email);
                    params.put("pertanyaan", Spertanyaan);
                    params.put("jawaban", Sjawaban);
                    params.put(config.KEY_USERNAME, username);
                    params.put(config.KEY_PASSWORD, password);

                    //returning parameter
                    return params;
                }
            };
            //Adding the string request to the queue
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    public boolean validate() {
        boolean valid_jawaban = false;
        boolean valid = false;

        String Jawaban = jawaban.getText().toString();

        //Handling validation for KTP field
        if (Jawaban.isEmpty()) {
            valid_jawaban = false;
            jawaban.setError("Jawaban tidak boleh kosong!");
        } else if (Jawaban.length() <= 0) {
            valid_jawaban = false;
            jawaban.setError("Jawaban tidak valid!");
        } else {
            valid_jawaban = true;
            jawaban.setError(null);
        }

        if (valid_jawaban) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }
}
