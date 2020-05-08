package com.example.ayoberbagi_mysql.relawan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.adapter.AdapterViewDDiterimaR;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;
import com.example.ayoberbagi_mysql.relawan.model.RelawanProsesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListDonasi extends AppCompatActivity {

    Intent intent;
    Context context;
    RecyclerView recyclerView;
    ArrayList<RelawanProsesModel> mItems;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_donasi);

        intent = getIntent();
        context = ListDonasi.this;

        recyclerView = findViewById(R.id.recycle_view);
        mItems = new ArrayList<>();
        mAdapter = new AdapterViewDDiterimaR(ListDonasi.this, mItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadjson();
    }


    public void loadjson(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_VIEW_SEMUA_DITERIMA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "response : " + response);
                        try {
                            JSONArray data = new JSONArray(response);
                            for (int i = 0; i < data.length(); i++){

                                JSONObject hasil = data.getJSONObject(i);
                                RelawanProsesModel rpm = new RelawanProsesModel();
                                rpm.setId_donasi(hasil.getString("id_donasi"));
                                rpm.setNama_donatur(hasil.getString("nama_donatur"));
                                rpm.setNominal(hasil.getString("nominal"));
                                rpm.setWaktu_donasi(hasil.getString("waktu_donasi"));
                                rpm.setBukti(config.URL_GAMBAR + hasil.getString("bukti"));
                                rpm.setUpload_path(hasil.getString("upload_path"));
                                rpm.setFoto(config.URL_GAMBAR + hasil.getString("foto"));
                                rpm.setKeterangan(hasil.getString("keterangan"));
                                rpm.setKategori(hasil.getString("kategori"));
                                rpm.setJumlah_total(hasil.getString("jumlah_total"));

                                mItems.add(rpm);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", ""+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Preferences pref = new Preferences(getApplicationContext());
                RelawanModel relawanModel = pref.getRelawanSession();
                //Adding parameters to request
                params.put(config.KEY_ID_BENCANA, intent.getStringExtra("id_bencana"));
                params.put(config.KEY_ID_PJ, relawanModel.getIdPj());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
