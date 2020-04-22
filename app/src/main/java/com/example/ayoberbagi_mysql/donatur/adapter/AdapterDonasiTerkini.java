package com.example.ayoberbagi_mysql.donatur.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.donatur.model.DonasiModel;

import java.util.ArrayList;

public class AdapterDonasiTerkini extends RecyclerView.Adapter<AdapterDonasiTerkini.ViewDonasiTerkini> {
    Context context;
    private ArrayList<DonasiModel> item;
    private final int limit = 6;

    public AdapterDonasiTerkini(Context context, ArrayList<DonasiModel> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewDonasiTerkini onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donasi_terkini, parent, false);
        ViewDonasiTerkini vdt = new ViewDonasiTerkini(view);
        return vdt;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDonasiTerkini holder, final int position) {
        DonasiModel model = item.get(position);

        if (model.getAnonim().equalsIgnoreCase("1")) {
            holder.nama_donatur.setText("Anonim");
        } else {
            holder.nama_donatur.setText(model.getNama_donatur());
        }


        if (model.getNominal().equalsIgnoreCase("null")) {
            holder.nominal.setTextColor(Color.parseColor("#FF0000"));
            holder.nominal.setText("Donasi barang");
        } else {
            holder.nominal.setText(model.getNominal());
        }

    }

    @Override
    public int getItemCount() {
        if (item.size() > limit) {
            return limit;
        } else {
            return item.size();
        }
    }

    static class ViewDonasiTerkini extends RecyclerView.ViewHolder {
        TextView nama_donatur, nominal;

        public ViewDonasiTerkini(@NonNull View itemView) {
            super(itemView);

            nama_donatur = (TextView) itemView.findViewById(R.id.nama_donatur);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
        }
    }
}
