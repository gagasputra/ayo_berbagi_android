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
import com.example.ayoberbagi_mysql.donatur.DetailDProses;
import com.example.ayoberbagi_mysql.donatur.model.DonasiProsesModel;

import java.util.ArrayList;

public class AdapterViewDProses extends RecyclerView.Adapter<AdapterViewDProses.ViewDProsesHolder> {
    Context context;
    private ArrayList<DonasiProsesModel> item;
    ImageLoader imageLoader;


    public AdapterViewDProses(Context context, ArrayList<DonasiProsesModel> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewDProsesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_don_proses, parent, false);
        ViewDProsesHolder vph = new ViewDProsesHolder(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDProsesHolder holder, final int position) {
        final DonasiProsesModel model = item.get(position);
        holder.nama_bencana.setText(model.getNama_bencana());
        holder.nama_relawan.setText(model.getNama());
        holder.waktu_donasi.setText(model.getWaktu_donasi());
        String Tnominal = model.getNominal();
        if (Tnominal.equalsIgnoreCase("null")) {
            holder.nominal.setTextColor(Color.parseColor("#00757c"));
            holder.nominal.setText(("- (Donasi Barang)"));
        } else {
            holder.nominal.setText(Tnominal + ",00");
        }
        String Tketerangan = model.getKeterangan();
        if (Tketerangan.equalsIgnoreCase("Belum Upload")) {
            holder.keterangan.setTextColor(Color.parseColor("#FF0000"));
            holder.keterangan.setText(Tketerangan);
        } else {
            holder.keterangan.setText(Tketerangan);
        }

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(config.URL_KOSONGAN + model.getUpload_path(),
                ImageLoader.getImageListener(
                        ViewDProsesHolder.bukti,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        R.drawable.noimage //Error image if requested image dose not found on server.
                )
        );

        holder.bukti.setImageUrl(model.getUpload_path(), imageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailDProses.class);
                i.putExtra("id_donasi", model.getId_donasi());
                i.putExtra("id_donasi_barang", model.getId_donasi_barang());
                i.putExtra("id_donatur", model.getId_donatur());
                i.putExtra("id_bencana", model.getId_bencana());
                i.putExtra("nama_bencana", model.getNama_bencana());
                i.putExtra("nama_relawan", model.getNama());
                i.putExtra("nominal", model.getNominal());
                i.putExtra("waktu_donasi", model.getWaktu_donasi());
                i.putExtra("keterangan", model.getKeterangan());
                i.putExtra("bukti", model.getBukti());
                i.putExtra("upload_path", model.getUpload_path());
                i.putExtra("foto", model.getFoto());
                i.putExtra("jumlah_total", model.getJumlah_total());
                i.putExtra("path_foto", model.getPath_foto());
                i.putExtra("jml_pakaian", model.getJml_pakaian());
                i.putExtra("jml_selimut", model.getJml_selimut());
                i.putExtra("jml_buku", model.getJml_buku());
                i.putExtra("jml_sembako", model.getJml_sembako());
                i.putExtra("jml_makan_minum", model.getJml_makan_minum());
                i.putExtra("jml_medis_obat", model.getJml_medis_obat());
                i.putExtra("jml_mainan", model.getJml_mainan());
                i.putExtra("jml_alat_rt", model.getJml_alat_rt());
                i.putExtra("barang_lain", model.getBarang_lain());
                i.putExtra("jml_lain", model.getJml_lain());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewDProsesHolder extends RecyclerView.ViewHolder {
        TextView nama_bencana, nama_relawan, nominal, keterangan, waktu_donasi;
        public static NetworkImageView bukti;

        public ViewDProsesHolder(@NonNull View itemView) {
            super(itemView);

            nama_bencana = (TextView) itemView.findViewById(R.id.nama_bencana);
            nama_relawan = (TextView) itemView.findViewById(R.id.nama_relawan);
            waktu_donasi = (TextView) itemView.findViewById(R.id.waktu_donasi);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            bukti = (NetworkImageView) itemView.findViewById(R.id.list_image);
            keterangan = (TextView) itemView.findViewById(R.id.keterangan);
        }
    }
}
