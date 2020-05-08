package com.example.ayoberbagi_mysql.donatur;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonasiUang extends AppCompatActivity {

    RadioGroup Rgroup;
    RadioButton RB10k, RB20k, RB50k, RB100k, RBlain;
    EditText ETnominal;
    String Tid_bencana, Tnama_bencana;
    TextView id_bencana, nama_bencana;
    Context context;
    String nominal, anonim;
    CheckBox CBanonim;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        identified();

        intent = getIntent();
        context = DonasiUang.this;

        Tid_bencana = intent.getStringExtra("id_bencana");
        Tnama_bencana = intent.getStringExtra("nama_bencana");
        id_bencana.setText(Tid_bencana);
        nama_bencana.setText(Tnama_bencana);

    }

    public void identified() {
        id_bencana = findViewById(R.id.id_bencana);
        nama_bencana = findViewById(R.id.nama_bencana);
        RB10k = findViewById(R.id.RB10k);
        RB20k = findViewById(R.id.RB20k);
        RB50k = findViewById(R.id.RB50k);
        RB100k = findViewById(R.id.RB100k);
        RBlain = findViewById(R.id.RBlain);
        ETnominal = findViewById(R.id.ETnominal);
        CBanonim = findViewById(R.id.anonim);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.RB10k:
                if (checked)
                    nominal = "10000";
                ETnominal.setEnabled(false);
                break;
            case R.id.RB20k:
                if (checked)
                    nominal = "20000";
                ETnominal.setEnabled(false);
                break;
            case R.id.RB50k:
                if (checked)
                    nominal = "50000";
                ETnominal.setEnabled(false);
                break;
            case R.id.RB100k:
                if (checked)
                    nominal = "100000";
                ETnominal.setEnabled(false);
                break;
            case R.id.RBlain:
                if (checked)
                    ETnominal.setEnabled(true);
                nominal = ETnominal.getText().toString().trim();
                break;
        }
    }

    public void addDonasi(View view) {

//        if (RB10k.isChecked()) {
//            nominal = "10000";
//            ETnominal.setEnabled(false);
//        } else if (RB20k.isChecked()) {
//            nominal = "20000";
//            ETnominal.setEnabled(false);
//        } else if (RB50k.isChecked()) {
//            nominal = "50000";
//            ETnominal.setEnabled(false);
//        } else if (RB100k.isChecked()) {
//            nominal = "100000";
//            ETnominal.setEnabled(false);
//        }
        if (RBlain.isChecked()) {
            nominal = ETnominal.getText().toString();
        }

        if (CBanonim.isChecked()) {
            anonim = "1";
        } else {
            anonim = "0";
        }

//        final String nominal = ETnominal.getText().toString().trim();
//
        if (validate()) {
//            Preferences pref = new Preferences(getApplicationContext());
//            DonaturModel donaturModel = pref.getUserSession();
//            if (donaturModel.getNama().isEmpty() ||
//                    donaturModel.getNo_ktp().isEmpty() ||
//                    donaturModel.getEmail().isEmpty() ||
//                    donaturModel.getTelp().isEmpty() ||
//                    donaturModel.getAlamat().isEmpty()) {
//                Toast.makeText(context, "Mohon Lengkapi Profile Sebelum Donasi", Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Donasi.this, ProfileActivity.class);
//                startActivity(i);
//            } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_DONASI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject user = new JSONObject(response);
                                if (user.getString("akses").equals("update")) {
                                    Toast.makeText(context, "Mohon Lengkapi Profile Sebelum Donasi", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(DonasiUang.this, ProfileActivity.class);
                                    startActivity(i);
                                } else {
                                    Log.d("donasi", response);
                                    Toast.makeText(context, "Donasi Berhasil, Silahkan Upload Bukti Transfer", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(context, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Mohon Pilih Nominal Donasi", Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Preferences pref = new Preferences(getApplicationContext());
                    DonaturModel donaturModel = pref.getUserSession();

                    Map<String, String> params = new HashMap<>();
                    params.put(config.KEY_NOMINAL, nominal);
                    params.put(config.KEY_ANONIM, anonim);
                    params.put(config.KEY_ID_BENCANA, intent.getStringExtra("id_bencana"));
                    params.put(config.KEY_ID_DONATUR, donaturModel.getIdDonatur());

                    return params;
                }

            };
            Volley.newRequestQueue(this).add(stringRequest);
//            }
        }
    }

    public boolean validate() {
        if (RBlain.isChecked()) {

            boolean valid_nominal = false;

            String nominal = ETnominal.getText().toString();

            if (nominal.isEmpty()) {
                valid_nominal = false;
                ETnominal.setError("Mohon isi nominal!");
            } else if (Integer.parseInt(nominal) < 10000) {
                valid_nominal = false;
                ETnominal.setError("Donasi tidak boleh kurang dari Rp10.000");
            } else {
                valid_nominal = true;
                ETnominal.setError(null);
            }
            return valid_nominal;
        }
        return true;
    }

}
