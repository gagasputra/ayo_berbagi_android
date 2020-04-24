package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DonasiHistory extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    private Button btnChooseFile;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;

    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    TextView id_bencana, nama_bencana, CB9;
    EditText nama_relawan, tanggal_diterima, nominal, keterangan, ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9;
    String Tcb1, Tcb2, Tcb3, Tcb4, Tcb5, Tcb6, Tcb7, Tcb8, Tcb9, upload_path, Tet9, path_foto;
    ImageView bukti, foto;
    Context context;
    Button detailDonasi;

    Intent intent;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donasi_history);

//        btnChooseFile = findViewById(R.id.btn_choose_file);
//        tvItemPath =  findViewById(R.id.tv_file_path);

        identified();

        intent = getIntent();
        context = DonasiHistory.this;

        detailDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBarang();
            }
        });

        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        tanggal_diterima.setText(intent.getStringExtra("waktu_diterima"));
        nama_relawan.setText(intent.getStringExtra("nama_relawan"));
        String Tnominal = intent.getStringExtra("nominal");
        if (Tnominal.equalsIgnoreCase("null")) {
            nominal.setText(("- (Donasi Barang)"));
            detailDonasi.setVisibility(View.VISIBLE);
        } else {
            nominal.setText(Tnominal + ",00");
        }

        keterangan.setText(intent.getStringExtra("keterangan"));
        upload_path = intent.getStringExtra("upload_path");

        if (upload_path.equalsIgnoreCase("null")) {
            setProfileImage(config.URL_NOIMAGE);
        } else {
            setProfileImage(config.URL_KOSONGAN + upload_path);
        }

        Tcb1 = intent.getStringExtra("jml_pakaian");
        Tcb2 = intent.getStringExtra("jml_selimut");
        Tcb3 = intent.getStringExtra("jml_buku");
        Tcb4 = intent.getStringExtra("jml_sembako");
        Tcb5 = intent.getStringExtra("jml_makan_minum");
        Tcb6 = intent.getStringExtra("jml_medis_obat");
        Tcb7 = intent.getStringExtra("jml_mainan");
        Tcb8 = intent.getStringExtra("jml_alat_rt");
        Tcb9 = intent.getStringExtra("barang_lain");
        Tet9 = intent.getStringExtra("jml_lain");
        path_foto = intent.getStringExtra("path_foto");

    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(DonasiHistory.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }

    public void identified() {
        id_bencana = findViewById(R.id.id_bencana);
        nama_bencana = findViewById(R.id.nama_bencana);
        nama_relawan = findViewById(R.id.nama_relawan);
        nominal = findViewById(R.id.nominal);
        keterangan = findViewById(R.id.keterangan);
        bukti = findViewById(R.id.bukti);
        detailDonasi = findViewById(R.id.detailDonasi);
        tanggal_diterima = findViewById(R.id.tanggal_diterima);
    }

    public void dialogBarang() {
        dialog = new AlertDialog.Builder(DonasiHistory.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_donasi_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        nama_bencana = dialogView.findViewById(R.id.nama_bencana);
        foto = dialogView.findViewById(R.id.bukti);
        ET1 = dialogView.findViewById(R.id.et1);
        ET2 = dialogView.findViewById(R.id.et2);
        ET3 = dialogView.findViewById(R.id.et3);
        ET4 = dialogView.findViewById(R.id.et4);
        ET5 = dialogView.findViewById(R.id.et5);
        ET6 = dialogView.findViewById(R.id.et6);
        ET7 = dialogView.findViewById(R.id.et7);
        ET8 = dialogView.findViewById(R.id.et8);
        ET9 = dialogView.findViewById(R.id.et9);
        CB9 = dialogView.findViewById(R.id.cb9);
        ET1.setText(Tcb1);
        ET2.setText(Tcb2);
        ET3.setText(Tcb3);
        ET4.setText(Tcb4);
        ET5.setText(Tcb5);
        ET6.setText(Tcb6);
        ET7.setText(Tcb7);
        ET8.setText(Tcb8);
        ET9.setText(Tet9);
        if (Tcb9.equalsIgnoreCase("-")) {
            CB9.setText("Barang Lainnya");
        } else {
            CB9.setText(Tcb9);
        }
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        setProfileImage2(config.URL_KOSONGAN + path_foto);

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setProfileImage2(String imgUrl) {
        Picasso.get().load(imgUrl).into(foto);
    }
}
