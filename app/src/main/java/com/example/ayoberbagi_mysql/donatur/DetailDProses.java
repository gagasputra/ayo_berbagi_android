package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.ImageLoader;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.NotificationReceiver;
import com.example.ayoberbagi_mysql.config.config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailDProses extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    Button btnChooseFile;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;


    EditText nama_relawan, nominal, keterangan, tanggal_donasi;
    TextView id_donasi, id_bencana, nama_bencana, TV_nominal, tv_file_path, CB9, Tjumlah_total;
    ImageView bukti, foto;
    Button detailDonasi;
    Context context;

    private ImageLoader imageLoader;
    EditText ETnominal;

    String Tkategori, Tnominal, Tketerangan;
    String Tcb1, Tcb2, Tcb3, Tcb4, Tcb5, Tcb6, Tcb7, Tcb8, Tcb9, upload_path, Tet9, path_foto, jumlah_total;
    EditText ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9;

    Intent intent;
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_donasi_proses);

//        btnChooseFile = findViewById(R.id.btn_choose_file);
//        tvItemPath =  findViewById(R.id.tv_file_path);

        identified();

        detailDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBarang();
            }
        });
        intent = getIntent();
        context = DetailDProses.this;

        id_donasi.setText(intent.getStringExtra("id_donasi"));
        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        nama_relawan.setText(intent.getStringExtra("nama_relawan"));
        Tnominal = intent.getStringExtra("nominal");
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
        jumlah_total = intent.getStringExtra("jumlah_total");


        if (Tnominal.equalsIgnoreCase("null")) {
            nominal.setText(("- (Donasi Barang)"));
            detailDonasi.setVisibility(View.VISIBLE);
            tv_file_path.setText("Upload Bukti Pengiriman");
        } else {
            nominal.setText(Tnominal + ",00");
        }
        tanggal_donasi.setText(intent.getStringExtra("waktu_donasi"));

        Tketerangan = intent.getStringExtra("keterangan");
        if (Tketerangan.equalsIgnoreCase("Belum Upload")) {
            keterangan.setTextColor(Color.parseColor("#FF0000"));
            keterangan.setText(Tketerangan);
        } else {
            keterangan.setText(Tketerangan);
        }

        upload_path = intent.getStringExtra("upload_path");
//        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        bukti = findViewById(R.id.bukti);

        if (upload_path.equalsIgnoreCase("null")) {
            setProfileImage(config.URL_NOIMAGE);
        } else {
            setProfileImage(config.URL_KOSONGAN + upload_path);
        }
    }

    public PendingIntent getLaunchIntent(int notificationId, Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notificationId", notificationId);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void notif() {

        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "0")
                .setSmallIcon(R.drawable.logo)
                .setColor(getResources().getColor(R.color.button_login))
                .setContentTitle("Donasi")
                .setContentText("Anda telah melakukan donasi, silahkan lanjutkan ke proses pembayaran")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent launchIntent = getLaunchIntent(NOTIFICATION_ID, getBaseContext());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getResources().getString(R.string.notifikasi)));
        builder.setAutoCancel(true);
        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);
        builder.setContentIntent(launchIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void identified() {
        this.id_bencana = findViewById(R.id.id_bencana);
        this.id_donasi = findViewById(R.id.id_donasi);
        this.nama_relawan = findViewById(R.id.nama_relawan);
        this.nominal = findViewById(R.id.nominal);
        this.keterangan = findViewById(R.id.keterangan);
        this.tanggal_donasi = findViewById(R.id.tanggal_donasi);
        btnChooseFile = (Button) findViewById(R.id.btn_choose_file);
        tv_file_path = findViewById(R.id.tv_file_path);
        ETnominal = (EditText) findViewById(R.id.total_donasi);
        detailDonasi = (Button) findViewById(R.id.detailDonasi);
        TV_nominal = findViewById(R.id.textView6);
        nama_bencana = findViewById(R.id.nama_bencana);
        Tjumlah_total = findViewById(R.id.jumlah_total);
    }

    public boolean upload_bukti(View view) {
        if (Tketerangan.equalsIgnoreCase("Sudah Upload")) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Apakah anda ingin upload bukti lagi?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(context, UploadBukti.class);
                            i.putExtra("nama_bencana", nama_bencana.getText().toString());
                            if (Tnominal.equalsIgnoreCase("null")) {
                                i.putExtra("nominal", "Donasi Barang");
                            } else {
                                i.putExtra("nominal", nominal.getText().toString());
                            }
                            i.putExtra("id_donasi", id_donasi.getText().toString());
                            context.startActivity(i);
                        }

                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        } else {
            Intent i = new Intent(context, UploadBukti.class);
            i.putExtra("nama_bencana", nama_bencana.getText().toString());
            if (Tnominal.equalsIgnoreCase("null")) {
                i.putExtra("nominal", "Donasi Barang");
            } else {
                i.putExtra("nominal", nominal.getText().toString());
            }
            i.putExtra("id_donasi", id_donasi.getText().toString());
            context.startActivity(i);
        }
        return true;
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(DetailDProses.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }

    public void dialogBarang() {
        dialog = new AlertDialog.Builder(DetailDProses.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_donasi_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
//        Intent i = new Intent(DetailDProses.this, DetailDBarang.class);
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
        Tjumlah_total = dialogView.findViewById(R.id.jumlah_total);
        ET1.setText(Tcb1);
        ET2.setText(Tcb2);
        ET3.setText(Tcb3);
        ET4.setText(Tcb4);
        ET5.setText(Tcb5);
        ET6.setText(Tcb6);
        ET7.setText(Tcb7);
        ET8.setText(Tcb8);
        ET9.setText(Tet9);
        Tjumlah_total.setText(jumlah_total);
        if(Tcb9.equalsIgnoreCase("-")){
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
