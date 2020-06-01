package com.example.ayoberbagi_mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String Snama, StipeDonatur, Snoktp, Semail, username, password, repassword;

    ProgressDialog pDialog;

    EditText ETnama, ETnoKtp, ETemail, ETusername, ETpassword, ETrepassword;
    Spinner SPtipeDonatur;
    String nama = "nama";
    String no_ktp = "no_ktp";
    String email = "email";
    String tipedonatur = "tipedonatur";

    Button BTNregister;

    Context context;

    TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findId();

        context = Register.this;

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keLogin();
            }
        });

    }

    public void register(View view) {
        Snama = ETnama.getText().toString().trim();
        StipeDonatur = SPtipeDonatur.getSelectedItem().toString().trim();
        Snoktp = ETnoKtp.getText().toString().trim();
        Semail = ETemail.getText().toString().trim();
        username = ETusername.getText().toString().trim();
        password = ETpassword.getText().toString().trim();
        repassword = ETrepassword.getText().toString().trim();
        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_CEK_USERNAME,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("berhasil", "Response: " + response);
                                JSONObject user = new JSONObject(response);

                                if (user.getString("akses").equals("username_sama")) {
                                    Toast.makeText(context, "Username telah digunakan", Toast.LENGTH_LONG).show();
                                } else if (user.getString("akses").equals("email_sama")){
                                    Toast.makeText(context, "Email telah digunakan", Toast.LENGTH_LONG).show();
                                } else if (user.getString("akses").equals("ktp_sama")){
                                    Toast.makeText(context, "No. KTP telah terdaftar", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent i = new Intent(context, Pertanyaan.class);
                                    i.putExtra("nama_donatur", Snama);
                                    i.putExtra("tipe_donatur", StipeDonatur);
                                    i.putExtra("email", Semail);
                                    i.putExtra("noktp", Snoktp);
                                    i.putExtra("username", username);
                                    i.putExtra("password", password);
                                    i.putExtra("repassword", repassword);
                                    Log.d("hasil: ", Snama + StipeDonatur
                                            + Semail
                                            + Snoktp
                                            + username
                                            + password
                                            + repassword);
                                    startActivity(i);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Preferences pref = new Preferences(getApplicationContext());
                    RelawanModel relawanModel = pref.getRelawanSession();

                    Map<String, String> params = new HashMap<>();

                    params.put("email", Semail);
                    params.put("username", username);
                    params.put("no_ktp", Snoktp);

                    return params;
                }

            };
            Volley.newRequestQueue(this).

                    add(stringRequest);
        }
    }

//    public void register(View view) {
//        final String Snama = ETnama.getText().toString().trim();
//        final String StipeDonatur = SPtipeDonatur.getSelectedItem().toString().trim();
//        final String Snoktp = ETnoKtp.getText().toString().trim();
//        final String Semail = ETemail.getText().toString().trim();
//        final String username = ETusername.getText().toString().trim();
//        final String password = ETpassword.getText().toString().trim();
//        final String repassword = ETrepassword.getText().toString().trim();
//
//        if (validate()) {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_REGISTER,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_LONG).show();
//                            keLogin();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            //You can handle error here if you want
//                            hideDialog();
//                            Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
//
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    //Adding parameters to request
//                    params.put(nama, Snama);
//                    params.put(tipedonatur, StipeDonatur);
//                    params.put(no_ktp, Snoktp);
//                    params.put(email, Semail);
//                    params.put(config.KEY_USERNAME, username);
//                    params.put(config.KEY_PASSWORD, password);
//
//                    //returning parameter
//                    return params;
//                }
//            };
//            //Adding the string request to the queue
//            Volley.newRequestQueue(this).add(stringRequest);
//        }
//    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void keLogin() {
        Intent i = new Intent(context, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public boolean validate() {
        boolean valid_nama = false;
        boolean valid_user = false;
        boolean valid_email = false;
        boolean valid_pass = false;
        boolean valid_conf = false;
        boolean valid_ktp = false;
        boolean valid = false;

        //Get values from EditText fields
        String Nama = ETnama.getText().toString();
        String UserName = ETusername.getText().toString();
        String KTP = ETnoKtp.getText().toString();
        String Email = ETemail.getText().toString();
        String Password = ETpassword.getText().toString();
        String ConfPassword = ETrepassword.getText().toString();

        //Handling validation for KTP field
        if (KTP.isEmpty()) {
            valid_ktp = false;
            ETnoKtp.setError("Nomor KTP tidak valid!");
        } else if (KTP.length() < 16 || KTP.length() > 16) {
            valid_ktp = false;
            ETnoKtp.setError("KTP harus 16 karakter!");
        } else {
            valid_ktp = true;
            ETnoKtp.setError(null);
        }

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid_user = false;
            ETusername.setError("Username tidak valid!");
        } else if (UserName.length() < 5) {
            valid_user = false;
            ETusername.setError("Username minimal 6 karakter!");
        } else {
            valid_user = true;
            ETusername.setError(null);
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid_email = false;
            ETemail.setError("Email tidak valid!");
        } else if (Email.isEmpty()) {
            valid_email = false;
            ETemail.setError("Email tidak boleh kosong!");
        } else {
            valid_email = true;
            ETemail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid_pass = false;
            ETpassword.setError("Password tidak boleh kosong!");
        } else if (Password.length() > 7) {
            valid_pass = true;
            ETpassword.setError(null);
        } else {
            valid_pass = false;
            ETpassword.setError("Password minimal 8 karakter!");
        }

        //
        if (!ConfPassword.equals(Password)) {
            valid_conf = false;
            ETrepassword.setError("Konfirmasi password tidak sama!");
        } else if (ConfPassword.isEmpty()) {
            valid_conf = false;
            ETrepassword.setError("Konfirmasi password tidak boleh kosong!");
        } else {
            valid_conf = true;
            ETrepassword.setError(null);
        }

        if (Nama.isEmpty()) {
            valid_nama = false;
            ETnama.setError("Nama Tidak Boleh Kosong!");
        } else {
            valid_nama = true;
            ETnama.setError(null);
        }

        if (valid_user && valid_conf && valid_email && valid_pass && valid_ktp && valid_nama) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

    public void findId() {
        ETnama = (EditText) findViewById(R.id.editTextNama);
        SPtipeDonatur = (Spinner) findViewById(R.id.tipeDonatur);
        ETemail = (EditText) findViewById(R.id.editTextEmail);
        ETpassword = (EditText) findViewById(R.id.editTextPassword);
        ETusername = (EditText) findViewById(R.id.editTextUsername);
        ETnoKtp = (EditText) findViewById(R.id.editTextKTP);
        ETrepassword = (EditText) findViewById(R.id.editTextConfPassword);
        BTNregister = (Button) findViewById(R.id.buttonRegister);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
