package com.example.ayoberbagi_mysql.relawan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class R_DetailDonasi extends AppCompatActivity {

    TextView id_bencana, nama_donatur;
    EditText nominal, waktu_donasi, kategori, jumlah;
    ImageView bukti;
    Context context;
    Button terimaDonasi, tolakDonasi;

    private ImageLoader imageLoader;
    boolean isImageFitToScreen;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r__detail_donasi);

        identified();

        intent = getIntent();
        context = R_DetailDonasi.this;

        terimaDonasi = findViewById(R.id.terimaDonasi);
        tolakDonasi = findViewById(R.id.tolakDonasi);

        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_donatur.setText(intent.getStringExtra("nama_donatur"));
        String Tnominal = intent.getStringExtra("nominal");
        if (Tnominal.equalsIgnoreCase("null")) {
            nominal.setText("-");
        } else {
            nominal.setText(Tnominal + ",00");
        }
        waktu_donasi.setText(intent.getStringExtra("waktu_donasi"));
        kategori.setText(intent.getStringExtra("kategori"));
//        String bukti = intent.getStringExtra("bukti");
        String upload_path = intent.getStringExtra("upload_path");
        String Tjumlah = intent.getStringExtra("jumlah_total");
        if (Tjumlah.equalsIgnoreCase("null")) {
            jumlah.setText("-");
        } else {
            jumlah.setText(Tjumlah);
        }

        if (upload_path.equalsIgnoreCase("null")) {
            terimaDonasi.setEnabled(false);
            terimaDonasi.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            tolakDonasi.setEnabled(false);
            tolakDonasi.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            setProfileImage(config.URL_NOIMAGE);
        } else {
            setProfileImage(config.URL_KOSONGAN + upload_path);
        }

//        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
//        final NetworkImageView imageView1 = (NetworkImageView)findViewById(R.id.bukti);
//        imageView1.setImageUrl(config.URL_KOSONGAN + upload_path, imageLoader);

        terimaDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(R_DetailDonasi.this)
                        .setIcon(R.drawable.ic_terima)
                        .setTitle("Terima Donasi")
                        .setMessage("Apakah Anda ingin menerima donasi ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                terimaDonasi();
                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });

        tolakDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(R_DetailDonasi.this)
                        .setIcon(R.drawable.ic_tolak)
                        .setTitle("Tolak Donasi")
                        .setMessage("Apakah Anda ingin menolak donasi ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tolakDonasi();
                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
    }

    public void identified() {
        id_bencana = findViewById(R.id.id_bencana);
        nama_donatur = findViewById(R.id.nama_donatur);
        nominal = findViewById(R.id.nominal);
        waktu_donasi = findViewById(R.id.waktu_donasi);
        kategori = findViewById(R.id.kategori);
        jumlah = findViewById(R.id.jumlah);
        bukti = findViewById(R.id.bukti);
    }


    public void terimaDonasi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_TERIMA_DONASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("berhasil", "Response: " + response);
                            JSONObject user = new JSONObject(response);

                            if (user.getString("akses").equals("update")) {
                                Toast.makeText(context, "Mohon Ganti Password dan Lengkapi Keamanan Akun", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(R_DetailDonasi.this, RelawanProfile.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(context, "Donasi Berhasil Diterima", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(R_DetailDonasi.this, ActivityRelawan.class);
                                Log.d("terima", "terima" + response);
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
                Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Preferences pref = new Preferences(getApplicationContext());
                RelawanModel relawanModel = pref.getRelawanSession();

                Map<String, String> params = new HashMap<>();

                params.put(config.KEY_ID_DONASI, intent.getStringExtra("id_donasi"));
                params.put(config.KEY_ID_DONATUR, relawanModel.getIdPj());
                params.put("username", relawanModel.getUsername());

                return params;
            }

        };
        Volley.newRequestQueue(this).

                add(stringRequest);
    }

    public void tolakDonasi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_TOLAK_DONASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent i = new Intent(R_DetailDonasi.this, ActivityRelawan.class);
                        Log.d("terima", "terima" + response);
                        Toast.makeText(context, "Donasi Berhasil Ditolak", Toast.LENGTH_LONG).show();
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(i);
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
                params.put(config.KEY_ID_DONASI, intent.getStringExtra("id_donasi"));
                params.put(config.KEY_ID_DONATUR, relawanModel.getIdPj());

                return params;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(R_DetailDonasi.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }
}
