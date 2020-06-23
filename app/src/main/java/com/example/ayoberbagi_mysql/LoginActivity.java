package com.example.ayoberbagi_mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ayoberbagi_mysql.donatur.MainActivity;
import com.example.ayoberbagi_mysql.relawan.ActivityRelawan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "debug gagas";

    //Declaration TextView
    TextView lupa_password, daftar_baru_relawan;

    //Declaration EditTexts
    EditText editTextUsername;
    EditText editTextPassword;

    //Declaration Checkbox & Save Login
    Boolean isLoggedIn;
    Boolean isLoggedInRelawan;

    //Declaration Button
    Button buttonLogin, daftar_baru;

    //Declaration SharedPreferences
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    Boolean session = false;
    Boolean session2 = false;

    ProgressDialog pDialog;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    private Context context;

    String id, username, akses, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("sp", MODE_PRIVATE);
        editor = sp.edit();

        context = LoginActivity.this;
        pDialog = new ProgressDialog(context);
        editTextUsername = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        daftar_baru = findViewById(R.id.daftar_baru);
        daftar_baru_relawan = findViewById(R.id.daftar_baru_relawan);
        lupa_password = findViewById(R.id.lupa_password);

        // Cek session login jika TRUE maka langsung buka MainActivity
        id = sp.getString(config.TAG_ID, null);
        username = sp.getString(config.TAG_USERNAME, null);

        Preferences pref = new Preferences(getApplicationContext());
        session = pref.getUserStatus();
        session2 = pref.getRelawanStatus();

        if (session) {
//            keViewBencana();
            keMainActivity();
        } else if (session2) {
            keActivityRelawan();
        }


//        if (session) {
//            if(akses == SUCCESS_RELAWAN){
//                keMainActivity();
//            } else {
//                keActivityRelawan();
//            }
//        }

        daftar_baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keDaftar();
            }
        });

        daftar_baru_relawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keDaftarRelawan();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResetPassword.class);
                startActivity(intent);
            }
        });

    }

    private void login() {
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d(TAG, "Response: " + response);
                                JSONObject user = new JSONObject(response);
                                Log.d(TAG, "User: " + user.getString("username"));
                                String id = user.getString("id");
                                String username = user.getString("username");

                                if (user.getString("role").equals("user")) {
                                    String idDonatur = user.getString("id_donatur");
                                    String nama_donatur = user.getString("nama_donatur");
                                    String no_ktp = user.getString("no_ktp");
                                    String email = user.getString("email");
                                    String telp = user.getString("telp");
                                    String alamat = user.getString("alamat");
                                    isLoggedIn(id, username, idDonatur, nama_donatur, no_ktp, email, telp, alamat);
                                    Log.d("save", "isloggedin: " + nama_donatur);
//                                  keViewBencana();
                                    keMainActivity();
                                } else if (user.getString("role").equals("pj")) {
                                    String idPj = user.getString("id_pj");
                                    isLoggedInRelawan(id, username, idPj);
                                    keActivityRelawan();
                                    Log.d("response", "berhasil " + idPj);
                                } else {
                                    //Displaying an error message on toast
                                    Toast.makeText(context, "Username atau Password Salah!", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "Username atau Password Salah!", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                            hideDialog();
                            Toast.makeText(context, "The server unreachable error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }


    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void keMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(config.SUCCESS_USER, akses);
        intent.putExtra(config.TAG_ID, id);
        intent.putExtra(config.TAG_USERNAME, username);
        startActivity(intent);
        finish();
    }

//    private void keViewBencana() {
//        Intent intent = new Intent(context, view_bencana.class);
//        intent.putExtra(config.SUCCESS_USER, akses);
//        intent.putExtra(config.TAG_ID, id);
//        intent.putExtra(config.TAG_USERNAME, username);
//        startActivity(intent);
//        finish();
//    }

    private void keActivityRelawan() {
        Intent intent = new Intent(context, ActivityRelawan.class);
        intent.putExtra(config.SUCCESS_RELAWAN, akses);
        intent.putExtra(config.TAG_ID, id);
        intent.putExtra(config.TAG_USERNAME, username);
        startActivity(intent);
        finish();
    }

    private void keDaftar() {
        Intent intent = new Intent(context, Register.class);
        startActivity(intent);
        finish();
    }

    private void keDaftarRelawan() {
        Intent intent = new Intent(context, RegisterRelawan.class);
        startActivity(intent);
        finish();
    }

    private void isLoggedIn(String id, String username, String idDonatur,
                            String nama_donatur, String no_ktp, String email, String telp, String alamat) {
        Preferences pref = new Preferences(getApplicationContext());
        pref.setUserSession(id, username, idDonatur, nama_donatur, no_ktp, email, telp, alamat);
    }

    private void isLoggedInRelawan(String id, String username, String idPj) {
        Preferences pref = new Preferences(getApplicationContext());
        pref.setRelawanSession(id, username, idPj);
    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Username = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (Username.isEmpty()) {
            valid = false;
            editTextUsername.setError("Please enter username!");
        } else {
            valid = true;
            editTextUsername.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            editTextPassword.setError("Please enter valid password!");
        }

        return valid;
    }

}

