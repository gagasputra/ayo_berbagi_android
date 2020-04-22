package com.example.ayoberbagi_mysql.donatur.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.ayoberbagi_mysql.donatur.model.DonasiProsesModel;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.adapter.AdapterViewDProses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DProsesFragment extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<DonasiProsesModel> mItems;
    TextView tv_donasi_proses;

    String id, username;
    public DProsesFragment(){

    }

    public static DProsesFragment newInstance(String param1, String param2) {
        DProsesFragment fragment = new DProsesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_dproses, container, false);

        sp = this.getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        id = this.getActivity().getIntent().getStringExtra(config.TAG_ID);
        username = this.getActivity().getIntent().getStringExtra(config.TAG_USERNAME);
        tv_donasi_proses = view.findViewById(R.id.d_proses);

        pd = new ProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.list_data);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterViewDProses(getActivity(), mItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        loadjson();

        return view;
    }

    public void loadjson(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_VIEW_DONASI_PROSES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        Log.d("response", "response : " + response);
                        if (response.equalsIgnoreCase("[]")) {
                            tv_donasi_proses.setText("TIDAK ADA DONASI DIPROSES");
                            tv_donasi_proses.setGravity(Gravity.CENTER);
                        } else {
                            try {
                                JSONArray data = new JSONArray(response);
                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject hasil = data.getJSONObject(i);
                                    DonasiProsesModel dpm = new DonasiProsesModel();
                                    dpm.setId_donasi(hasil.getString("id_donasi"));
                                    dpm.setId_donatur(hasil.getString("id_donatur"));
                                    dpm.setNama_bencana(hasil.getString("nama_bencana"));
                                    dpm.setNama(hasil.getString("nama"));
                                    dpm.setNominal(hasil.getString("nominal"));
                                    dpm.setWaktu_donasi(hasil.getString("waktu_donasi"));
                                    dpm.setUpload_path(hasil.getString("upload_path"));
                                    dpm.setKeterangan(hasil.getString("keterangan"));
                                    dpm.setFoto(hasil.getString("foto"));
                                    dpm.setJumlah_total(hasil.getString("jumlah_total"));
                                    dpm.setPath_foto(hasil.getString("path_foto"));
                                    dpm.setJml_pakaian(hasil.getString("jml_pakaian"));
                                    dpm.setJml_selimut(hasil.getString("jml_selimut"));
                                    dpm.setJml_buku(hasil.getString("jml_buku"));
                                    dpm.setJml_sembako(hasil.getString("jml_sembako"));
                                    dpm.setJml_makan_minum(hasil.getString("jml_makan_minum"));
                                    dpm.setJml_medis_obat(hasil.getString("jml_medis_obat"));
                                    dpm.setJml_mainan(hasil.getString("jml_mainan"));
                                    dpm.setJml_alat_rt(hasil.getString("jml_alat_rt"));
                                    dpm.setBarang_lain(hasil.getString("barang_lain"));
                                    dpm.setJml_lain(hasil.getString("jml_lain"));

                                    mItems.add(dpm);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
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
                DonaturModel donaturModel = pref.getUserSession();
                //Adding parameters to request
                params.put(config.KEY_ID_DONATUR, donaturModel.getIdDonatur());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }




}
