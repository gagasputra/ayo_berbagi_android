package com.example.ayoberbagi_mysql.relawan.adapter;

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
import com.example.ayoberbagi_mysql.relawan.DetailDistribusi;
import com.example.ayoberbagi_mysql.relawan.model.DistribusiModel;

import java.util.ArrayList;

public class AdapterViewDistribusi extends RecyclerView.Adapter<AdapterViewDistribusi.ViewBencanaHolder> {
    Context context;
    private ArrayList<DistribusiModel> item;
    ImageLoader imageLoader;

    public AdapterViewDistribusi(Context context, ArrayList<DistribusiModel> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewBencanaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_distribusi, parent, false);
        ViewBencanaHolder vph = new ViewBencanaHolder(view);
        return vph;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBencanaHolder holder, final int position) {
        final DistribusiModel model = item.get(position);
        holder.nama_bencana.setText(model.getNama_bencana());
        String Sstatus = model.getStatus();
        if(Sstatus.equalsIgnoreCase("0")){
            holder.status.setText("Donasi Belum Selesai");
        } else if(Sstatus.equalsIgnoreCase("1")){
            holder.status.setText("Belum Didistribusikan");
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.status.setText("Telah Didistribusikan");
            holder.status.setTextColor(Color.parseColor("#10bcc9"));
        }
        holder.total_donasi.setText("Rp. " + model.getTotal_donasi() + ",00");

        holder.jumlah_donatur.setText(model.getJumlah_donasi());

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(config.URL_KOSONGAN + model.getGambar_bencana(),
                ImageLoader.getImageListener(
                        ViewBencanaHolder.gambar_bencana,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        R.drawable.noimage //Error image if requested image dose not found on server.
                )
        );

        holder.gambar_bencana.setImageUrl(model.getGambar_bencana(), imageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailDistribusi.class);
                i.putExtra("id_bencana", model.getId_bencana());
                i.putExtra("id_pj", model.getId_pj());
                i.putExtra("nama_bencana", model.getNama_bencana());
                i.putExtra("tgl_kejadian", model.getTgl_kejadian());
                i.putExtra("lokasi", model.getLokasi());
                i.putExtra("deskripsi", model.getDeskripsi());
                i.putExtra("total_donasi", model.getTotal_donasi());
                i.putExtra("jumlah_korban", model.getJumlah_korban());
                i.putExtra("jumlah_donasi", model.getJumlah_donasi());
                i.putExtra("kerugian", model.getKerugian());
                i.putExtra("batas_akhir", model.getBatas_akhir());
                i.putExtra("nama_relawan", model.getNama_relawan());
                i.putExtra("deadline", model.getDeadline());
                i.putExtra("gambar1", model.getGambar_bencana());
                i.putExtra("gambar2", model.getGambar_bencana2());
                i.putExtra("gambar3", model.getGambar_bencana3());
                i.putExtra("tanggal_distribusi", model.getTanggal_distribusi());
                i.putExtra("tgl_akhir_distribusi", model.getTgl_akhir_distribusi());
                i.putExtra("lokasi_distribusi", model.getLokasi_distribusi());
                i.putExtra("laporan", model.getLaporan());
                i.putExtra("konfirmasi", model.getKonfirmasi());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class ViewBencanaHolder extends RecyclerView.ViewHolder {
        TextView nama_bencana, status, total_donasi, jumlah_donatur;
        public static NetworkImageView gambar_bencana;
        public ViewBencanaHolder(@NonNull View itemView) {
            super(itemView);

            nama_bencana = (TextView) itemView.findViewById(R.id.nama_bencana);
            status = (TextView) itemView.findViewById(R.id.status_distribusi);
            total_donasi = (TextView) itemView.findViewById(R.id.total_donasi);
            gambar_bencana = (NetworkImageView) itemView.findViewById(R.id.list_image);
            jumlah_donatur = (TextView) itemView.findViewById(R.id.jumlah_donatur);
        }
    }
}
