package com.example.ayoberbagi_mysql.donatur.fragment;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.donatur.model.BeritaModel;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.adapter.AdapterViewBerita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BeritaFragment extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<BeritaModel> mItems;

    public BeritaFragment(){

    }

    public static BeritaFragment newInstance(String param1, String param2) {
        BeritaFragment fragment = new BeritaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_berita, container, false);

        pd = new ProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.list_data);
        mItems = new ArrayList<BeritaModel>();
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterViewBerita(getActivity(), mItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        loadjson();

        return view;
    }

    public void loadjson(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, config.URL_VIEW_LAPORAN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i=0; i < response.length(); i++){
                            try {
                                JSONObject data = response.getJSONObject(i);
                                BeritaModel bm = new BeritaModel();
                                bm.setId_distribusi(data.getString("id_distribusi"));
                                bm.setId_bencana(data.getString("id_bencana"));
                                bm.setNama_bencana(data.getString("nama_bencana"));
                                bm.setTanggal_distribusi(data.getString("tanggal_distribusi"));
                                bm.setTgl_akhir_distribusi(data.getString("tgl_akhir_distribusi"));
                                bm.setLokasi_distribusi(data.getString("lokasi_distribusi"));
                                bm.setNama(data.getString("nama"));
                                bm.setTotal_donasi(data.getString("total_donasi"));
                                bm.setGambar1(config.URL_KOSONGAN + data.getString("gambar1"));
                                bm.setGambar2(config.URL_KOSONGAN + data.getString("gambar2"));
                                bm.setGambar3(config.URL_KOSONGAN + data.getString("gambar3"));
                                bm.setLaporan(data.getString("laporan"));

                                mItems.add(bm);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getActivity()).add(arrayRequest);
    }



}
