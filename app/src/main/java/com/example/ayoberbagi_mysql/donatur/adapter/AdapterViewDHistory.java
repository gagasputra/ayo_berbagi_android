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
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.DonasiHistory;
import com.example.ayoberbagi_mysql.donatur.model.DonasiHistoryModel;

import java.util.ArrayList;

public class AdapterViewDHistory extends RecyclerView.Adapter<AdapterViewDHistory.ViewDHistoryHolder> {
    Context context;
    private ArrayList<DonasiHistoryModel> item;
    ImageLoader imageLoader;

    public AdapterViewDHistory(Context context, ArrayList<DonasiHistoryModel> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewDHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_don_history, parent, false);
        ViewDHistoryHolder vph = new ViewDHistoryHolder(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDHistoryHolder holder, final int position) {
        final DonasiHistoryModel model = item.get(position);
        holder.nama_bencana.setText(model.getNama_bencana());
        holder.nama_relawan.setText(model.getNama());
        String Tnominal = model.getNominal();
        if (Tnominal.equalsIgnoreCase("null")) {
            holder.nominal.setTextColor(Color.parseColor("#00757c"));
            holder.nominal.setText(("- (Donasi Barang)"));
        } else {
            holder.nominal.setText(Tnominal + ",00");
        }
        holder.keterangan.setText(model.getKeterangan());
        holder.waktu_diterima.setText(model.getWaktu_diterima());


        imageLoader = ImageAdapter.getInstance(context).getImageLoader();


        imageLoader.get(config.URL_KOSONGAN + model.getUpload_path(),
                ImageLoader.getImageListener(
                        ViewDHistoryHolder.bukti,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );


        holder.bukti.setImageUrl(model.getUpload_path(), imageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DonasiHistory.class);
                i.putExtra("id_donasi", model.getId_donasi());
                i.putExtra("nama_bencana", model.getNama_bencana());
                i.putExtra("nama_relawan", model.getNama());
                i.putExtra("keterangan", model.getKeterangan());
                i.putExtra("bukti", model.getBukti());
                i.putExtra("foto", model.getFoto());
                i.putExtra("waktu_diterima", model.getWaktu_diterima());
                i.putExtra("nominal", model.getNominal());
                i.putExtra("upload_path", model.getUpload_path());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewDHistoryHolder extends RecyclerView.ViewHolder {
        TextView nama_bencana, nama_relawan, nominal, keterangan, waktu_diterima;
        public static NetworkImageView bukti;
        public ViewDHistoryHolder(@NonNull View itemView) {
            super(itemView);

            nama_bencana = (TextView) itemView.findViewById(R.id.nama_bencana);
            nama_relawan = (TextView) itemView.findViewById(R.id.nama_relawan);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            bukti = (NetworkImageView) itemView.findViewById(R.id.list_image);
            keterangan  = (TextView) itemView.findViewById(R.id.keterangan);
            waktu_diterima = (TextView) itemView.findViewById(R.id.waktu_diterima);
        }
    }
}
