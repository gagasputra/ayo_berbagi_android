package com.example.ayoberbagi_mysql.donatur.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.donatur.Donasi;
import com.example.ayoberbagi_mysql.donatur.model.Model;

import java.util.ArrayList;

public class AdapterViewBencana extends RecyclerView.Adapter<AdapterViewBencana.ViewBencanaHolder> {
    Context context;
    private ArrayList<Model> item;
    ImageLoader imageLoader;

    public AdapterViewBencana(Context context, ArrayList<Model> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewBencanaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bencana, parent, false);
        ViewBencanaHolder vph = new ViewBencanaHolder(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBencanaHolder holder, final int position) {
        final Model model = item.get(position);
        holder.nama_bencana.setText(model.getNama_bencana());
        holder.nama_relawan.setText(model.getNama_relawan());
        holder.total_donasi.setText("Rp. " + model.getTotal_donasi() + ",00");

        String Tdeadline = model.getDeadline();

        if(Integer.valueOf(Tdeadline) <= 5){
            holder.deadline.setTextColor(Color.parseColor("#FF0000"));
            holder.deadline.setText(model.getDeadline());
        } else {
            holder.deadline.setText(model.getDeadline());
        }


        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(model.getGambar_bencana(),
                ImageLoader.getImageListener(
                        ViewBencanaHolder.gambar_bencana,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        holder.gambar_bencana.setImageUrl(model.getGambar_bencana(), imageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Donasi.class);
                i.putExtra("id_bencana", model.getId_bencana());
                i.putExtra("id_pj", model.getId_pj());
                i.putExtra("nama_bencana", model.getNama_bencana());
                i.putExtra("tgl_kejadian", model.getTgl_kejadian());
                i.putExtra("lokasi", model.getLokasi());
                i.putExtra("deskripsi", model.getDeskripsi());
                i.putExtra("jumlah_korban", model.getJumlah_korban());
                i.putExtra("kerugian", model.getKerugian());
                i.putExtra("batas_akhir", model.getBatas_akhir());
                i.putExtra("nama_relawan", model.getNama_relawan());
                i.putExtra("deadline", model.getDeadline());
                i.putExtra("gambar", model.getGambar_bencana());
                i.putExtra("gambar2", model.getGambar_bencana2());
                i.putExtra("gambar3", model.getGambar_bencana3());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewBencanaHolder extends RecyclerView.ViewHolder {
        TextView nama_bencana, nama_relawan, total_donasi, deadline;
        public static NetworkImageView gambar_bencana;
        public ViewBencanaHolder(@NonNull View itemView) {
            super(itemView);

            nama_bencana = (TextView) itemView.findViewById(R.id.nama_bencana);
            nama_relawan = (TextView) itemView.findViewById(R.id.nama_relawan);
            total_donasi = (TextView) itemView.findViewById(R.id.total_donasi);
            gambar_bencana = (NetworkImageView) itemView.findViewById(R.id.list_image);
            deadline = (TextView) itemView.findViewById(R.id.deadline);
        }
    }
}
