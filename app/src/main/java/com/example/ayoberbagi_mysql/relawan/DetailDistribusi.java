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
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailDistribusi extends AppCompatActivity {

    TextView TVid_bencana, TVnama_bencana, TVDnama_bencana;
    EditText ETtotal_donasi, ETtgl_distribusi, ETlokasi_distribusi;
    String id_bencana, nama_bencana, total_donasi, tgl_distribusi, tgl_akhir_distribusi, lokasi_distribusi;

    ImageView gambar1, gambar2, gambar3;
    String sgambar1, sgambar2, sgambar3;

    Context context;
    ImageLoader imageLoader;
    Intent intent;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    Button detailDistribusi, listDonasi;

    EditText DETtgl_kejadian, DETlokasi, DETdeskripsi, DETjml_korban, DETkerugian, DETbatas_akhir, DETnama_relawan;
    EditText ETDtotal_donasi, ETDlokasi_distribusi, ETDtanggal_distribusi, ETDbatas_akhir, ETDlaporan;
    NetworkImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_distribusi);

        identified();

        intent = getIntent();
        context = DetailDistribusi.this;

        id_bencana = intent.getStringExtra("id_bencana");
        nama_bencana = intent.getStringExtra("nama_bencana");
        total_donasi = intent.getStringExtra("total_donasi");
        tgl_distribusi = intent.getStringExtra("tanggal_distribusi");
        tgl_akhir_distribusi = intent.getStringExtra("tgl_akhir_distribusi");
        lokasi_distribusi = intent.getStringExtra("lokasi_distribusi");

        TVid_bencana.setText(id_bencana);
        TVnama_bencana.setText(nama_bencana);
        ETtotal_donasi.setText("Rp. " + total_donasi + ".00");
        if (tgl_distribusi.equalsIgnoreCase("null")) {
            ETtgl_distribusi.setText("Donasi Belum Didistribusikan");
        } else if (tgl_akhir_distribusi.equals(tgl_distribusi)) {
            ETtgl_distribusi.setText(tgl_distribusi);
            detailDistribusi.setVisibility(View.VISIBLE);
        } else {
            ETtgl_distribusi.setText(tgl_distribusi + " s/d " + tgl_akhir_distribusi);
            detailDistribusi.setVisibility(View.VISIBLE);
        }
        if (lokasi_distribusi.equalsIgnoreCase("null")) {
            ETlokasi_distribusi.setText("Donasi Belum Didistribusikan");
        } else {
            ETlokasi_distribusi.setText(lokasi_distribusi);
        }
        sgambar1 = config.URL_KOSONGAN + intent.getStringExtra("gambar1");
        sgambar2 = config.URL_KOSONGAN + intent.getStringExtra("gambar2");
        sgambar3 = config.URL_KOSONGAN + intent.getStringExtra("gambar3");

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        NetworkImageView imageView1 = (NetworkImageView) findViewById(R.id.gambar1);
        NetworkImageView imageView2 = (NetworkImageView) findViewById(R.id.gambar2);
        NetworkImageView imageView3 = (NetworkImageView) findViewById(R.id.gambar3);
        imageView1.setImageUrl(sgambar1, imageLoader);
        imageView2.setImageUrl(sgambar2, imageLoader);
        imageView3.setImageUrl(sgambar3, imageLoader);

        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.flipperid);
        viewFlipper.startFlipping();

//        infoRelawan = findViewById(R.id.infoRelawan);
//        infoRelawan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadInfo();
//                dialogRelawan();
//
//            }
//        });

        listDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailDistribusi.this, ListDonasi.class);
                intent.putExtra("id_bencana", id_bencana);
                startActivity(intent);
            }
        });
    }

    public void identified() {
        TVid_bencana = findViewById(R.id.id_bencana);
        TVnama_bencana = findViewById(R.id.nama_bencana);
//        nama_relawan = findViewById(R.id.nama_relawan);
//        deadline = findViewById(R.id.deadline);
        gambar1 = findViewById(R.id.gambar1);
        gambar2 = findViewById(R.id.gambar2);
        gambar3 = findViewById(R.id.gambar3);
        ETtotal_donasi = (EditText) findViewById(R.id.total_donasi);
        ETtgl_distribusi = findViewById(R.id.tgl_distribusi);
        ETlokasi_distribusi = findViewById(R.id.lokasi_distribusi);
        detailDistribusi = findViewById(R.id.detailDistribusi);
        listDonasi = findViewById(R.id.listDonasi);
    }

    public void detailBencana(View view) {

        dialog = new AlertDialog.Builder(DetailDistribusi.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_bencana_r, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        TVDnama_bencana = dialogView.findViewById(R.id.TVDnama_bencana);
        DETtgl_kejadian = dialogView.findViewById(R.id.ETtgl_kejadian);
        DETlokasi = dialogView.findViewById(R.id.ETlokasi);
        DETdeskripsi = dialogView.findViewById(R.id.ETdeskripsi);
        DETjml_korban = dialogView.findViewById(R.id.ETjml_korban);
        DETkerugian = dialogView.findViewById(R.id.ETkerugian);
        DETbatas_akhir = dialogView.findViewById(R.id.ETbatas_akhir);
        DETnama_relawan = dialogView.findViewById(R.id.ETnama_relawan);
        imageView1 = (NetworkImageView) dialogView.findViewById(R.id.gambar);
        imageView2 = (NetworkImageView) dialogView.findViewById(R.id.gambar2);
        imageView3 = (NetworkImageView) dialogView.findViewById(R.id.gambar3);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        ViewFlipper viewFlipper = (ViewFlipper) dialogView.findViewById(R.id.flipperid);
        viewFlipper.startFlipping();

        loadjson();


        dialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void loadjson() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_SELECT_BENCANA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley", "response : " + response.toString());
                        try {
                            JSONArray hasil = new JSONArray(response);
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject data = hasil.getJSONObject(i);
                                TVDnama_bencana.setText(data.getString("nama_bencana"));
                                DETtgl_kejadian.setText(data.getString("tgl_kejadian"));
                                DETlokasi.setText(data.getString("lokasi"));
                                DETdeskripsi.setText(data.getString("deskripsi"));
                                DETjml_korban.setText(data.getString("jumlah_korban"));
                                DETkerugian.setText(data.getString("kerugian"));
                                DETbatas_akhir.setText(data.getString("batas_akhir"));
                                imageView1.setImageUrl(config.URL_GAMBAR + data.getString("gambar"), imageLoader);
                                imageView2.setImageUrl(config.URL_GAMBAR + data.getString("gambar2"), imageLoader);
                                imageView3.setImageUrl(config.URL_GAMBAR + data.getString("gambar3"), imageLoader);
                            }
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
                params.put(config.KEY_ID_BENCANA, id_bencana);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void detailDistribusi(View view) {
        dialog = new AlertDialog.Builder(DetailDistribusi.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_distribusi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        TVDnama_bencana = dialogView.findViewById(R.id.TVDnama_bencana);
        ETDtotal_donasi = dialogView.findViewById(R.id.ETtotal_donasi);
        ETDlokasi_distribusi = dialogView.findViewById(R.id.ETlokasi_distribusi);
        ETDtanggal_distribusi = dialogView.findViewById(R.id.ETtgl_distribusi);
        ETDbatas_akhir = dialogView.findViewById(R.id.ETbatas_akhir);
        ETDlaporan = dialogView.findViewById(R.id.ETlaporan);
        imageView1 = (NetworkImageView) dialogView.findViewById(R.id.gambar1);
        imageView2 = (NetworkImageView) dialogView.findViewById(R.id.gambar2);
        imageView3 = (NetworkImageView) dialogView.findViewById(R.id.gambar3);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        TVDnama_bencana.setText(nama_bencana);
        ETDtotal_donasi.setText("Rp. " + total_donasi + ".00");
        ETDlokasi_distribusi.setText(lokasi_distribusi);
        ETDtanggal_distribusi.setText(tgl_distribusi + " s/d " + tgl_akhir_distribusi);
        ETDbatas_akhir.setText(intent.getStringExtra("batas_akhir"));
        ETDlaporan.setText(intent.getStringExtra("laporan"));

        imageView1.setImageUrl(sgambar1, imageLoader);
        imageView2.setImageUrl(sgambar2, imageLoader);
        imageView3.setImageUrl(sgambar3, imageLoader);

        ViewFlipper viewFlipper = (ViewFlipper) dialogView.findViewById(R.id.flipperid);
        viewFlipper.startFlipping();

        dialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void uploadBukti(View view) {
        Intent i = new Intent(DetailDistribusi.this, UploadDistribusi.class);
        i.putExtra("id_bencana", id_bencana);
        i.putExtra("nama_bencana", nama_bencana);
        i.putExtra("total_donasi", total_donasi);
        startActivity(i);
    }
}
