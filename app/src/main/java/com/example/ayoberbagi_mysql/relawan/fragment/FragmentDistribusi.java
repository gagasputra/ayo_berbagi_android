package com.example.ayoberbagi_mysql.relawan.fragment;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.adapter.AdapterViewDistribusi;
import com.example.ayoberbagi_mysql.relawan.model.DistribusiModel;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentDistribusi extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<DistribusiModel> mItems;

    public FragmentDistribusi() {

    }

    public static FragmentDistribusi newInstance(String param1, String param2) {
        FragmentDistribusi fragment = new FragmentDistribusi();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_distribusi_donasi, container, false);

        pd = new ProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.list_data);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterViewDistribusi(getActivity(), mItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        loadjson();

        return view;
    }

    public void loadjson() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_VIEW_DISTRIBUSI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        Log.d("response", "response : " + response);
                        try {
                            JSONArray data = new JSONArray(response);
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject hasil = data.getJSONObject(i);
                                DistribusiModel dm = new DistribusiModel();
                                dm.setId_bencana(hasil.getString("id_bencana"));
                                dm.setId_pj(hasil.getString("id_pj"));
                                dm.setNama_bencana(hasil.getString("nama_bencana"));
                                dm.setNama_relawan(hasil.getString("nama"));
                                dm.setTgl_kejadian(hasil.getString("tgl_kejadian"));
                                dm.setJumlah_donasi(hasil.getString("jumlah_donasi"));
                                dm.setStatus(hasil.getString("status"));
                                dm.setLokasi(hasil.getString("lokasi"));
                                dm.setDeskripsi(hasil.getString("deskripsi"));
                                dm.setJumlah_korban(hasil.getString("jumlah_korban"));
                                dm.setKerugian(hasil.getString("kerugian"));
                                dm.setBatas_akhir(hasil.getString("batas_akhir"));
                                dm.setTotal_donasi(hasil.getString("total_donasi"));
                                dm.setGambar_bencana(hasil.getString("gambar1"));
                                dm.setGambar_bencana2(hasil.getString("gambar2"));
                                dm.setGambar_bencana3(hasil.getString("gambar3"));
                                dm.setDeadline(hasil.getString("deadline"));
                                dm.setTanggal_distribusi(hasil.getString("tanggal_distribusi"));
                                dm.setTgl_akhir_distribusi(hasil.getString("tgl_akhir_distribusi"));
                                dm.setLokasi_distribusi(hasil.getString("lokasi_distribusi"));
                                dm.setLaporan(hasil.getString("laporan"));
                                dm.setKonfirmasi(hasil.getString("konfirmasi"));
                                mItems.add(dm);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }


}
