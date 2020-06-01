package com.example.ayoberbagi_mysql.relawan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.R_DetailDonasiEnd;
import com.example.ayoberbagi_mysql.relawan.model.RelawanProsesModel;

import java.util.ArrayList;

public class AdapterViewDDiterimaR extends RecyclerView.Adapter<AdapterViewDDiterimaR.ViewDHistoryR> {
    Context context;
    private ArrayList<RelawanProsesModel> item;
    ImageLoader imageLoader;

    public AdapterViewDDiterimaR(Context context, ArrayList<RelawanProsesModel> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewDHistoryR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_r_diterima, parent, false);
        ViewDHistoryR vph = new ViewDHistoryR(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDHistoryR holder, final int position) {
        final RelawanProsesModel model = item.get(position);
        holder.nama_donatur.setText(model.getNama_donatur());
        holder.kategori.setText(model.getKategori());
        if(model.getNominal().equalsIgnoreCase("null")){
            holder.nominal.setText("0");
        } else {
            holder.nominal.setText(model.getNominal() + ",00");
        }
        String Tketerangan = model.getKeterangan();
        if(Tketerangan.equalsIgnoreCase("Belum Upload")){
            holder.keterangan.setTextColor(Color.parseColor("#FF0000"));
            holder.keterangan.setText(model.getKeterangan());
        } else {
            holder.keterangan.setText(model.getKeterangan());
        }

        if(model.getJumlah_total().equalsIgnoreCase("null")){
            holder.jumlah.setText("-");
        } else {
            holder.jumlah.setText(model.getJumlah_total());
            Log.d("pesan", "berhasil"+model.getJumlah_total());
        }


        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(config.URL_KOSONGAN+ model.getUpload_path(),
                ImageLoader.getImageListener(
                        ViewDHistoryR.bukti,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        R.drawable.noimage //Error image if requested image dose not found on server.
                )
        );

        holder.bukti.setImageUrl(model.getUpload_path(), imageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, R_DetailDonasiEnd.class);
                i.putExtra("id_donasi", model.getId_donasi());
                i.putExtra("nama_donatur", model.getNama_donatur());
                i.putExtra("nominal", model.getNominal());
                i.putExtra("waktu_donasi", model.getWaktu_donasi());
                i.putExtra("kategori", model.getKategori());
                i.putExtra("keterangan", model.getKeterangan());
                i.putExtra("jumlah_total", model.getJumlah_total());
                i.putExtra("bukti", model.getBukti());
                i.putExtra("upload_path", model.getUpload_path());
                i.putExtra("waktu_diterima", model.getWaktu_diterima());
                Log.d("waktu_diterima_relawan", "waktu diterima" + model.getWaktu_diterima());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewDHistoryR extends RecyclerView.ViewHolder {
        TextView nama_donatur, kategori, nominal, keterangan, jumlah;
        public static NetworkImageView bukti;
        public ViewDHistoryR(@NonNull View itemView) {
            super(itemView);

            nama_donatur = (TextView) itemView.findViewById(R.id.nama_donatur);
            kategori = (TextView) itemView.findViewById(R.id.kategori);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            bukti = (NetworkImageView) itemView.findViewById(R.id.list_image);
            keterangan = (TextView) itemView.findViewById(R.id.keterangan);
            jumlah = (TextView) itemView.findViewById(R.id.jumlah);
        }
    }
}
