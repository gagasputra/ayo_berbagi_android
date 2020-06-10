package com.example.ayoberbagi_mysql.relawan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class R_DetailDonasiEnd extends AppCompatActivity {

    TextView id_bencana, nama_donatur, nama_bencana, CB9, Tjumlah_total;
    EditText nominal, waktu_donasi, kategori, jumlah, waktu_diterima;

    String Tcb9;
    EditText ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9;
    ImageView bukti, foto;
    Context context;
    Button detailDonasi;
    String id_donasi, userImg;

    private ImageLoader imageLoader;
    boolean isImageFitToScreen;

    Intent intent;
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_detail_donasi_end);

        identified();

        intent = getIntent();
        context = R_DetailDonasiEnd.this;

        id_donasi = intent.getStringExtra("id_donasi");
        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_donatur.setText(intent.getStringExtra("nama_donatur"));
        String Tnominal = intent.getStringExtra("nominal");
        if (Tnominal.equalsIgnoreCase("null")) {
            nominal.setText("-");
        } else {
            nominal.setText(Tnominal + ",00");
        }
        String upload_path = intent.getStringExtra("upload_path");
        setProfileImage(config.URL_KOSONGAN + upload_path);
        waktu_donasi.setText(intent.getStringExtra("waktu_donasi"));
        String Skategori = intent.getStringExtra("kategori");
        kategori.setText(Skategori);
        waktu_diterima.setText(intent.getStringExtra("waktu_diterima"));
//        String bukti = intent.getStringExtra("bukti");
        String Tjumlah = intent.getStringExtra("jumlah_total");
        if (Tjumlah.equalsIgnoreCase("null")) {
            jumlah.setText("-");
        } else {
            jumlah.setText(Tjumlah);
        }

        if (Skategori.equalsIgnoreCase("barang")) {
            detailDonasi.setVisibility(View.VISIBLE);
            detailDonasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadjson();
                    dialogBarang();
                }
            });
        }


//        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
//        final NetworkImageView imageView1 = (NetworkImageView)findViewById(R.id.bukti);
//        imageView1.setImageUrl(config.URL_KOSONGAN + upload_path, imageLoader);
    }

    public void identified() {
        id_bencana = findViewById(R.id.id_bencana);
        nama_donatur = findViewById(R.id.nama_donatur);
        nominal = findViewById(R.id.nominal);
        waktu_diterima = findViewById(R.id.waktu_diterima);
        waktu_donasi = findViewById(R.id.waktu_donasi);
        kategori = findViewById(R.id.kategori);
        jumlah = findViewById(R.id.jumlah);
        bukti = findViewById(R.id.bukti);
        detailDonasi = findViewById(R.id.detailDonasi);
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(R_DetailDonasiEnd.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }

    public void dialogBarang() {
        dialog = new AlertDialog.Builder(R_DetailDonasiEnd.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_donasi_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
//        Intent i = new Intent(DetailDProses.this, DetailDBarang.class);
        nama_bencana = dialogView.findViewById(R.id.nama_bencana);
        foto = dialogView.findViewById(R.id.bukti);
        ET1 = dialogView.findViewById(R.id.et1);
        ET2 = dialogView.findViewById(R.id.et2);
        ET3 = dialogView.findViewById(R.id.et3);
        ET4 = dialogView.findViewById(R.id.et4);
        ET5 = dialogView.findViewById(R.id.et5);
        ET6 = dialogView.findViewById(R.id.et6);
        ET7 = dialogView.findViewById(R.id.et7);
        ET8 = dialogView.findViewById(R.id.et8);
        ET9 = dialogView.findViewById(R.id.et9);
        CB9 = dialogView.findViewById(R.id.cb9);
        Tjumlah_total = dialogView.findViewById(R.id.jumlah_total);
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setProfileImage2(String imgUrl) {
        Picasso.get().load(imgUrl).into(foto);
    }

    public void loadjson() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_DIALOG_BARANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley", "response : " + response.toString());
                        try {
                            JSONObject data = new JSONObject(response);
                            ET1.setText(data.getString("jml_pakaian"));
                            ET2.setText(data.getString("jml_selimut"));
                            ET3.setText(data.getString("jml_buku"));
                            ET4.setText(data.getString("jml_sembako"));
                            ET5.setText(data.getString("jml_makan_minum"));
                            ET6.setText(data.getString("jml_medis_obat"));
                            ET7.setText(data.getString("jml_mainan"));
                            ET8.setText(data.getString("jml_alat_rt"));
                            ET9.setText(data.getString("jml_lain"));
                            Tcb9 = data.getString("barang_lain");
                            if (Tcb9.equalsIgnoreCase("-")) {
                                CB9.setText("Barang Lainnya");
                            } else {
                                CB9.setText(Tcb9);
                            }
                            Tjumlah_total.setText(data.getString("jumlah_total"));
                            userImg = data.getString("path_foto");
                            setProfileImage2(config.URL_KOSONGAN + userImg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(context, "The server unreachable error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Preferences pref = new Preferences(getApplicationContext());
                DonaturModel dm = pref.getUserSession();
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(config.KEY_ID_DONASI, id_donasi);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
