package com.example.ayoberbagi_mysql.relawan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.ayoberbagi_mysql.donatur.model.Model;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentRelawan extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<Model> mItems;
    TextView tv_jumlahdonatur, tv_nama_bencana, tv_tgl_kejadian, tv_total_donasi, tv_donasi_diterima, tv_donasi_diproses, jumlah_diterima, jumlah_diproses;
    String id, username;

    public FragmentRelawan(){

    }

    public static FragmentRelawan newInstance(String param1, String param2) {
        FragmentRelawan fragment = new FragmentRelawan();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_relawan, container, false);

        tv_jumlahdonatur = view.findViewById(R.id.jumlahdonatur);
        tv_nama_bencana = view.findViewById(R.id.nama_bencana);
        tv_tgl_kejadian = view.findViewById(R.id.tgl_kejadian);
        tv_total_donasi = view.findViewById(R.id.totaldonasi);
        tv_donasi_diterima = view.findViewById(R.id.donasiditerima);
        tv_donasi_diproses = view.findViewById(R.id.donasiproses);
        jumlah_diterima = view.findViewById(R.id.jumlah_diterima);
        jumlah_diproses = view.findViewById(R.id.barang_diproses);
        sp = this.getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        id = this.getActivity().getIntent().getStringExtra(config.TAG_ID);
        username = this.getActivity().getIntent().getStringExtra(config.TAG_USERNAME);

        setToView(view);
        return view;
    }

    private void setToView(View view) {
        final View v = view;
        Preferences pref = new Preferences(getContext());
        RelawanModel relawanModel = pref.getRelawanSession();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_DASHBOARD_RELAWAN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response: ", response);
                        try {

//                            JSONArray jsonArray = user.getJSONArray(response);
                            if(response.equalsIgnoreCase("null")){
                                tv_nama_bencana.setText("Tidak Ada Bencana Aktif");
                                tv_tgl_kejadian.setText("-");
                                tv_jumlahdonatur.setText("0");
                                tv_total_donasi.setText("0");
                                tv_donasi_diterima.setText("0");
                                tv_donasi_diproses.setText("0");
                                jumlah_diterima.setText("0");
                                jumlah_diproses.setText("0");
                            } else {
                                JSONObject user = new JSONObject(response);
                                tv_nama_bencana.setText(user.getString("nama_bencana"));
                                tv_tgl_kejadian.setText(user.getString("tgl_kejadian"));
                                String jumlahDonatur = user.getString(("banyak"));
                                if (jumlahDonatur.equalsIgnoreCase("null")) {
                                    tv_jumlahdonatur.setText("0");
                                } else {
                                    tv_jumlahdonatur.setText(jumlahDonatur);
                                }
                                String totalDonasi = user.getString(("total_donasi"));
                                if (totalDonasi.equalsIgnoreCase("null")) {
                                    tv_total_donasi.setText("0");
                                } else {
                                    tv_total_donasi.setText(totalDonasi);
                                }
                                String donasiDiterima = user.getString(("hitung_total"));
                                if (donasiDiterima.equalsIgnoreCase("null")) {
                                    tv_donasi_diterima.setText("0");
                                } else {
                                    tv_donasi_diterima.setText(donasiDiterima);
                                }
                                String donasiDiproses = user.getString(("total_diproses"));
                                if (donasiDiproses.equalsIgnoreCase("null")) {
                                    tv_donasi_diproses.setText("0");
                                } else {
                                    tv_donasi_diproses.setText(donasiDiproses);
                                }
                                String jumlahDiterima = user.getString(("jumlah_diterima"));
                                if (jumlahDiterima.equalsIgnoreCase("null")) {
                                    jumlah_diterima.setText("0");
                                } else {
                                    jumlah_diterima.setText(jumlahDiterima);
                                }
                                String jumlahDiproses = user.getString(("jumlah_diproses"));
                                if (jumlahDiproses.equalsIgnoreCase("null")) {
                                    jumlah_diproses.setText("0");
                                } else {
                                    jumlah_diproses.setText(jumlahDiproses);
                                }
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //You can handle error here if you want
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
