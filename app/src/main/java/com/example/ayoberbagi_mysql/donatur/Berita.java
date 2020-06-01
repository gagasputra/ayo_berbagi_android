package com.example.ayoberbagi_mysql.donatur;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;

public class Berita extends AppCompatActivity {

    TextView nama_bencana;
    EditText ETtgl_kejadian, ETnama_relawan, ETlokasi_distribusi, ETtgl_distribusi, ETlaporan, ETtotal_donasi;
    Context context;
    ImageLoader imageLoader;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        identified();

        intent = getIntent();
        context = Berita.this;

        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        ETtgl_kejadian.setText(intent.getStringExtra("tgl_kejadian"));
        ETnama_relawan.setText(intent.getStringExtra("nama_relawan"));
        ETtotal_donasi.setText("Rp. " + intent.getStringExtra("total_donasi") + ".00");
        String tgl_distribusi = intent.getStringExtra("tanggal_distribusi");
        String tgl_akhir_distribusi = intent.getStringExtra("tgl_akhir_distribusi");
        ETtgl_distribusi.setText(tgl_distribusi + " s/d " + tgl_akhir_distribusi);
        ETlokasi_distribusi.setText(intent.getStringExtra("lokasi_distribusi"));
        ETlaporan.setText(intent.getStringExtra("laporan"));
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

    public void identified(){
        nama_bencana = findViewById(R.id.nama_bencana);
        ETnama_relawan = findViewById(R.id.ETnama_relawan);
        ETlaporan = findViewById(R.id.ETlaporan);
        ETtotal_donasi = (EditText) findViewById(R.id.ETtotal_donasi);
        ETlokasi_distribusi = findViewById(R.id.ETlokasi_distribusi);
        ETlaporan = findViewById(R.id.ETlaporan);
        ETtgl_distribusi = findViewById(R.id.ETtgl_distribusi);
        ETtgl_kejadian = findViewById(R.id.ETtgl_kejadian);
    }
}
