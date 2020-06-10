package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;
import com.example.ayoberbagi_mysql.relawan.DetailDistribusi;
import com.example.ayoberbagi_mysql.relawan.RPertanyaan;
import com.example.ayoberbagi_mysql.relawan.UploadDistribusi;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditAkun extends AppCompatActivity {

    Preferences pref;
    DonaturModel donaturModel;

    String uname;

    Context context;
    Intent intent;
    EditText ETusername, ETold_password, ETnew_password, ETkonf_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_akun);
        context = EditAkun.this;

        intent = getIntent();
        pref = new Preferences(getApplicationContext());
        donaturModel = pref.getUserSession();

        ETusername = findViewById(R.id.ETusername);
        ETold_password = findViewById(R.id.ETold_password);
        ETnew_password = findViewById(R.id.ETnew_password);
        ETkonf_password = findViewById(R.id.ETkonf_password);

        ETusername.setText(donaturModel.getUsername());
    }

    public void editAkun(View view) {
        if (validate()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_EDIT_AKUN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject user = new JSONObject(response);
                        if (user.getString("hasil").equals("berhasil")) {
                            Log.d("response: ", response);
                            Toast.makeText(context, "Edit Akun Berhasil", Toast.LENGTH_LONG).show();
                            uname = user.getString("uname");
                            Log.d("uname", uname);
                            pref.saveData(uname);
                            Intent i = new Intent(EditAkun.this, ProfileActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(context, "Password lama tidak cocok!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(EditAkun.this, ProfileActivity.class);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Edit Akun Gagal", Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("id", donaturModel.getId());
                    params.put("username", ETusername.getText().toString());
                    params.put("password", ETnew_password.getText().toString());
                    params.put("old_password", ETold_password.getText().toString());

                    return params;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    public boolean validate() {
        boolean valid_username = false;
        boolean valid_old_password = false;
        boolean valid_new_password = false;
        boolean valid_konf_password = false;
        boolean valid = false;

        //Get values from EditText fields
        String username = ETusername.getText().toString();
        String old_password = ETold_password.getText().toString();
        String new_password = ETnew_password.getText().toString();
        String konf_password = ETkonf_password.getText().toString();

        //Handling validation for UserName field
        if (username.isEmpty()) {
            valid_username = false;
            ETusername.setError("Mohon isi username!");
        } else if (username.length() < 5) {
            valid_username = false;
            ETusername.setError("Username terlalu pendek!");
        } else {
            valid_username = true;
            ETusername.setError(null);
        }

        //Handling validation for Password field
        if (old_password.isEmpty()) {
            valid_old_password = false;
            ETold_password.setError("Password lama tidak boleh kosong!");
        } else {
            valid_old_password = true;
            ETold_password.setError(null);
        }

        if (new_password.isEmpty()) {
            valid_new_password = false;
            ETnew_password.setError("Password baru tidak boleh kosong!");
        } else if (new_password.equalsIgnoreCase(old_password)) {
            valid_new_password = false;
            ETnew_password.setError("Password baru tidak boleh sama!");
        } else if (new_password.length() < 8) {
            valid_new_password = false;
            ETnew_password.setError("Password minimal 8 karakter!");
        } else {
            valid_new_password = true;
            ETnew_password.setError(null);
        }

        //
        if (!konf_password.equalsIgnoreCase(new_password)) {
            valid_konf_password = false;
            ETkonf_password.setError("Konfirmasi password tidak sama!");
        } else if (konf_password.isEmpty()) {
            valid_konf_password = false;
            ETkonf_password.setError("Konfirmasi password cannot empty!");
        } else {
            valid_konf_password = true;
            ETkonf_password.setError(null);
        }

        if (valid_username && valid_old_password && valid_new_password && valid_konf_password) {
            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

    public void pertanyaan(View view) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle("Apakah anda ingin mengubah pertanyaan keamanan?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preferences pref = new Preferences(getApplicationContext());
                        DonaturModel donaturModel = pref.getUserSession();
                        Intent i = new Intent(EditAkun.this, DPertanyaan.class);
                        i.putExtra("id_donatur", donaturModel.getIdDonatur());
                        startActivity(i);
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
