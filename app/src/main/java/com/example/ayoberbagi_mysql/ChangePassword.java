package com.example.ayoberbagi_mysql;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class ChangePassword extends AppCompatActivity {

    Context context;
    Intent intent;

    EditText ETusername, ETnew_password, ETkonf_password;
    String username, new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = ChangePassword.this;
        intent = getIntent();

        ETusername = findViewById(R.id.ETusername);
        ETnew_password = findViewById(R.id.ETnew_password);
        ETkonf_password = findViewById(R.id.ETkonf_password);

        ETusername.setText(intent.getStringExtra("username"));
    }

    public void ubahPassword(View view) {
        if (validate()) {
            username = ETusername.getText().toString();
            new_password = ETnew_password.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_NEW_PASS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            Toast.makeText(context, "Ubah Password Berhasil", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, LoginActivity.class);
                            startActivity(i);
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
                    params.put(config.KEY_PASSWORD, new_password);

                    //returning parameter
                    return params;
                }
            };
            //Adding the string request to the queue
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    public boolean validate() {
        boolean valid_newpass = false;
        boolean valid_konfpass = false;
        boolean valid = false;

        String NewPass = ETnew_password.getText().toString();
        String KonfPass = ETkonf_password.getText().toString();

        //Handling validation for KTP field
        if (NewPass.isEmpty()) {
            valid_newpass = false;
            ETnew_password.setError("Password tidak boleh kosong!");
        } else if (NewPass.length() < 8) {
            valid_newpass = false;
            ETnew_password.setError("Password minimal 8 karakter!");
        } else {
            valid_newpass = true;
            ETnew_password.setError(null);
        }

        if (KonfPass.isEmpty()) {
            valid_konfpass = false;
            ETkonf_password.setError("Password tidak boleh kosong!");
        } else if (KonfPass.equals(NewPass)) {
            valid_konfpass = true;
            ETkonf_password.setError(null);
        } else {
            valid_konfpass = false;
            ETkonf_password.setError("Konfirmasi password tidak sama!");
        }

        if (valid_newpass && valid_konfpass) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

}
