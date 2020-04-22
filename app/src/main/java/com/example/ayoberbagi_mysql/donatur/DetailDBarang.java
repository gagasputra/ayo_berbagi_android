package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.ImageLoader;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.NotificationReceiver;
import com.example.ayoberbagi_mysql.config.config;
import com.squareup.picasso.Picasso;

public class DetailDBarang extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    Button btnChooseFile;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;


    EditText ET1,ET2,ET3,ET4,ET5,ET6,ET7,ET8,ET9;
    TextView id_donasi, id_bencana, nama_bencana, TV_nominal, tv_file_path, CB9;
    ImageView bukti;
    Button detailDonasi;
    Context context;

    private ImageLoader imageLoader;
    EditText ETnominal;

    String Tkategori, Tnominal, Tketerangan;

    Intent intent;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_donasi_barang);

//        btnChooseFile = findViewById(R.id.btn_choose_file);
//        tvItemPath =  findViewById(R.id.tv_file_path);

        identified();

        intent = getIntent();
        context = DetailDBarang.this;

        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        String Tcb1 = intent.getStringExtra("jml_pakaian");
        String Tcb2 = intent.getStringExtra("jml_selimut");
        String Tcb3 = intent.getStringExtra("jml_buku");
        String Tcb4 = intent.getStringExtra("jml_sembako");
        String Tcb5 = intent.getStringExtra("jml_makan_minum");
        String Tcb6 = intent.getStringExtra("jml_medis_obat");
        String Tcb7 = intent.getStringExtra("jml_mainan");
        String Tcb8 = intent.getStringExtra("jml_alat_rt");
        String Tcb9 = intent.getStringExtra("barang_lain");
        String Tet9 = intent.getStringExtra("jml_lain");

        String path_foto = intent.getStringExtra("path_foto");

        ET1.setText(Tcb1);
        ET2.setText(Tcb2);
        ET3.setText(Tcb3);
        ET4.setText(Tcb4);
        ET5.setText(Tcb5);
        ET6.setText(Tcb6);
        ET7.setText(Tcb7);
        ET8.setText(Tcb8);
        CB9.setText(Tcb9);
        ET9.setText(Tet9);

//        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        bukti = findViewById(R.id.bukti);

        setProfileImage(config.URL_KOSONGAN + path_foto);
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
        id_bencana = findViewById(R.id.id_bencana);
        nama_bencana = findViewById(R.id.nama_bencana);
        ET1 = findViewById(R.id.et1);
        ET2 = findViewById(R.id.et2);
        ET3 = findViewById(R.id.et3);
        ET4 = findViewById(R.id.et4);
        ET5 = findViewById(R.id.et5);
        ET6 = findViewById(R.id.et6);
        ET7 = findViewById(R.id.et7);
        ET8 = findViewById(R.id.et8);
        ET9 = findViewById(R.id.et9);
        CB9 = findViewById(R.id.cb9);
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
                            i.putExtra("id_donasi", id_donasi.getText().toString());
                            context.startActivity(i);
                        }

                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        } else {
            Intent i = new Intent(context, UploadBukti.class);
            i.putExtra("nama_bencana", nama_bencana.getText().toString());
            i.putExtra("id_donasi", id_donasi.getText().toString());
            context.startActivity(i);
        }
        return true;
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(DetailDBarang.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }
}
