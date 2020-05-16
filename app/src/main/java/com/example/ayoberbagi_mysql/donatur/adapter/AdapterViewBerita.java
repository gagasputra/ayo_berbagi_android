package com.example.ayoberbagi_mysql.donatur.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ayoberbagi_mysql.donatur.Berita;
import com.example.ayoberbagi_mysql.donatur.model.BeritaModel;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.R;

import java.util.ArrayList;

public class AdapterViewBerita extends RecyclerView.Adapter<AdapterViewBerita.ViewBeritaHolder> {
    Context context;
    private ArrayList<BeritaModel> item;
    ImageLoader imageLoader;

    public AdapterViewBerita(Context context, ArrayList<BeritaModel> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewBeritaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_berita, parent, false);
        ViewBeritaHolder vph = new ViewBeritaHolder(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBeritaHolder holder, final int position) {
        final BeritaModel bm = item.get(position);
        holder.nama_bencana.setText(bm.getNama_bencana());
        holder.nama_relawan.setText(bm.getNama());
        holder.total_donasi.setText("Rp. " + bm.getTotal_donasi() + ",00");


        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(bm.getGambar1(),
                ImageLoader.getImageListener(
                        ViewBeritaHolder.gambar_bencana,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        holder.gambar_bencana.setImageUrl(bm.getGambar1(), imageLoader);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Berita.class);
                i.putExtra("id_distribusi", bm.getId_distribusi());
                i.putExtra("nama_bencana", bm.getNama_bencana());
                i.putExtra("nama_relawan", bm.getNama());
                i.putExtra("gambar1", bm.getGambar1());
                i.putExtra("gambar2", bm.getGambar2());
                i.putExtra("gambar3", bm.getGambar3());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewBeritaHolder extends RecyclerView.ViewHolder {
        TextView nama_bencana, nama_relawan, total_donasi;
        public static NetworkImageView gambar_bencana;
        public ViewBeritaHolder(@NonNull View itemView) {
            super(itemView);

            nama_bencana = (TextView) itemView.findViewById(R.id.nama_bencana);
            nama_relawan = (TextView) itemView.findViewById(R.id.nama_relawan);
            total_donasi = (TextView) itemView.findViewById(R.id.total_donasi);
            gambar_bencana = (NetworkImageView) itemView.findViewById(R.id.list_image);
        }
    }
}
