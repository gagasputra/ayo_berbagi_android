package com.example.ayoberbagi_mysql.relawan;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

import java.util.HashMap;
import java.util.Map;

public class RPertanyaan extends AppCompatActivity {
    Intent intent;
    Context context;
    String Spertanyaan, Sjawaban;
    Spinner pertanyaan;
    Button submit;
    EditText jawaban;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pertanyaan_rahasia);

        context = RPertanyaan.this;

        pertanyaan = findViewById(R.id.pertanyaan);
        jawaban = findViewById(R.id.jawaban);
        submit = findViewById(R.id.buttonRegister);

        Drawable d = getResources().getDrawable(R.drawable.button_logout_relawan);
        submit.setBackground(d);
        submit.setText("SUBMIT");
    }

    public void register(View view) {
        Spertanyaan = pertanyaan.getSelectedItem().toString();
        Sjawaban = jawaban.getText().toString();
        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_UBAH_KEAMANAN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            Toast.makeText(context, "Ubah Pertanyaan Keamanan Berhasil", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, RelawanProfile.class);
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
                    Preferences pref = new Preferences(getApplicationContext());
                    RelawanModel relawanModel = pref.getRelawanSession();
                    //Adding parameters to request

                    params.put("secret_q", Spertanyaan);
                    params.put("answer", Sjawaban);
                    params.put("username", relawanModel.getUsername());
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
