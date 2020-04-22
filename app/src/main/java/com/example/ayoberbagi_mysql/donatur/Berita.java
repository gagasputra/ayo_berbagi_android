package com.example.ayoberbagi_mysql.donatur;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.BeritaModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Berita extends AppCompatActivity {

    ImageView gambar1, gambar2, gambar3;
    TextView id_laporan, nama_bencana, nama_relawan, laporan;
    Context context;
    ArrayList<BeritaModel> bItem;

    private ImageLoader imageLoader;
    EditText ETnominal;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        identified();

        intent = getIntent();
        context = Berita.this;

        bItem = new ArrayList<>();

        id_laporan.setText(intent.getStringExtra("id_laporan"));
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        nama_relawan.setText(intent.getStringExtra("nama_relawan"));
//        laporan.setText(intent.getStringExtra("laporan"));
        laporan();
        String gambar1 = intent.getStringExtra("gambar1");
        String gambar2 = intent.getStringExtra("gambar2");
        String gambar3 = intent.getStringExtra("gambar3");

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        NetworkImageView imageView1 = (NetworkImageView)findViewById(R.id.gambar1);
        NetworkImageView imageView2 = (NetworkImageView)findViewById(R.id.gambar2);
        NetworkImageView imageView3 = (NetworkImageView)findViewById(R.id.gambar3);
        imageView1.setImageUrl(gambar1, imageLoader);
        imageView2.setImageUrl(gambar2, imageLoader);
        imageView3.setImageUrl(gambar3, imageLoader);

        ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.flipperid);
        viewFlipper.startFlipping();
    }

    private void laporan(){
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, config.URL_DETAIL_LAPORAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONObject data = new JSONObject(response);
                                Log.d("ASD", "onResponse: " + data);
                                laporan.setText(data.getString("laporan"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //You can handle error here if you want
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Intent i = getIntent();
                //Adding parameters to request
                params.put("id_laporan", i.getStringExtra("id_laporan"));
                //returning parameter
                return params;
            }
        };

        Volley.newRequestQueue(this).add(arrayRequest);
    }

    public void identified(){
        this.id_laporan = findViewById(R.id.id_laporan);
        this.nama_bencana = findViewById(R.id.nama_bencana);
        this.nama_relawan = findViewById(R.id.nama_relawan);
        this.laporan = findViewById(R.id.laporan);
        this.gambar1 = findViewById(R.id.gambar1);
        this.gambar2 = findViewById(R.id.gambar2);
        this.gambar3 = findViewById(R.id.gambar3);
        ETnominal = (EditText) findViewById(R.id.total_donasi);
    }
}
