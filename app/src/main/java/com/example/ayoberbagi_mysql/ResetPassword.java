package com.example.ayoberbagi_mysql;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    Context context;

    String username, jawaban;

    EditText ETusername, ETjawaban;
    TextView hasil_pertanyaan;
    LinearLayout qna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        context = ResetPassword.this;

        ETusername = findViewById(R.id.ETusername);
        ETjawaban = findViewById(R.id.ETjawaban);
        hasil_pertanyaan = findViewById(R.id.hasil_pertanyaan);
        qna = findViewById(R.id.qna);
    }

    public void Cek(View view) {
        username = ETusername.getText().toString();
        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_BEFORE_RESET_PASS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("null")) {
                                qna.setVisibility(View.GONE);
                                Toast.makeText(context, "Username tidak valid", Toast.LENGTH_SHORT).show();
                            } else {
                                qna.setVisibility(View.VISIBLE);
                                Log.d("response", response);
                                try {
                                    JSONObject user = new JSONObject(response);
                                    hasil_pertanyaan.setText(user.getString("secret_q"));
                                    jawaban = user.getString("answer");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
                    params.put(config.KEY_USERNAME, username);

                    //returning parameter
                    return params;
                }
            };
            //Adding the string request to the queue
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    public boolean validate() {
        boolean valid_username = false;
        boolean valid = false;

        String Username = ETusername.getText().toString();

        //Handling validation for KTP field
        if (Username.isEmpty()) {
            valid_username = false;
            ETusername.setError("Username tidak boleh kosong!");
        } else if (Username.length() <= 0) {
            valid_username = false;
            ETusername.setError("Username tidak valid!");
        } else {
            valid_username = true;
            ETusername.setError(null);
        }

        if (valid_username) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

    public void inputUname(View view) {
        jawaban = ETjawaban.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_INPUT_RESET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject user = new JSONObject(response);
                            if (user.getString("akses").equalsIgnoreCase("berhasil")) {
                                Intent i = new Intent(context, ChangePassword.class);
                                i.putExtra("username", username);
                                startActivity(i);
                            } else {
                                Toast.makeText(context, "Jawaban Salah!", Toast.LENGTH_LONG).show();
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put(config.KEY_USERNAME, username);
                params.put("answer", jawaban);

                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
