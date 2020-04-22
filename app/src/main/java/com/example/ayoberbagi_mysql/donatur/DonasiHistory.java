package com.example.ayoberbagi_mysql.donatur;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.config.NotificationReceiver;
import com.example.ayoberbagi_mysql.config.config;

public class DonasiHistory extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    private Button btnChooseFile;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;


    TextView id_bencana, nama_bencana, nama_relawan, nominal, keterangan;
    ImageView bukti;
    Context context;

    private ImageLoader imageLoader;
    EditText ETnominal;

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

        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));
        nama_relawan.setText(intent.getStringExtra("nama_relawan"));
        nominal.setText(intent.getStringExtra("nominal"));
        keterangan.setText(intent.getStringExtra("keterangan"));
        String buktinya = intent.getStringExtra("upload_path");
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        NetworkImageView imageView1 = (NetworkImageView)findViewById(R.id.bukti);

        if (buktinya.equalsIgnoreCase("null")){
            imageView1.setImageUrl(config.URL_NOIMAGE, imageLoader);
            Log.d("response ", "ini null " + config.URL_NOIMAGE);
            Log.d("error image ", "ini null " + imageLoader);
            Log.d("bukti ", buktinya);
//            imageView1.setImageUrl(config.URL_NOIMAGE, imageLoader);
        } else {
            imageView1.setImageUrl(config.URL_KOSONGAN + buktinya, imageLoader);
            Log.d("response ", "berhasil " + buktinya);
            Log.d("bukti ", buktinya);
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

    public void identified(){
        this.id_bencana = findViewById(R.id.id_bencana);
        this.nama_bencana = findViewById(R.id.nama_bencana);
        this.nama_relawan = findViewById(R.id.nama_relawan);
        this.nominal = findViewById(R.id.nominal);
        this.keterangan = findViewById(R.id.keterangan);
        ETnominal = (EditText) findViewById(R.id.total_donasi);
    }
}
