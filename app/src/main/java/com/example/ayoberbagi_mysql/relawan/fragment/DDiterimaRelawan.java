package com.example.ayoberbagi_mysql.relawan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

public class DDiterimaRelawan extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<RelawanProsesModel> mItems;

    Intent intent;

    String id, username;
    public DDiterimaRelawan(){

    }

    public static DDiterimaRelawan newInstance(String param1, String param2) {

        DDiterimaRelawan fragment = new DDiterimaRelawan();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_rditerima, container, false);

        sp = this.getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        id = this.getActivity().getIntent().getStringExtra(config.TAG_ID);
        username = this.getActivity().getIntent().getStringExtra(config.TAG_USERNAME);

        pd = new ProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.list_data);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterViewDDiterimaR(getActivity(), mItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        loadjson();
        return view;

    }

    public void loadjson(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_VIEW_RELAWAN_DITERIMA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
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
                                rpm.setWaktu_diterima(hasil.getString("waktu_diterima"));
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
                Preferences pref = new Preferences(getContext());
                RelawanModel relawanModel = pref.getRelawanSession();
                //Adding parameters to request
                params.put(config.KEY_ID_PJ, relawanModel.getIdPj());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }




}
