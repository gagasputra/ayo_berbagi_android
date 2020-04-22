package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.config.NotificationReceiver;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Donasi extends AppCompatActivity {

    TextView id_bencana, nama_bencana, nama_relawan, deadline;
    EditText ETtgl_kejadian, ETlokasi, ETdeadline, ETnama_relawan;

    EditText DETtgl_kejadian, DETlokasi, DETdeskripsi, DETjml_korban, DETkerugian, DETbatas_akhir, DETnama_relawan;
    ImageView gambar, gambar2, gambar3;
    Context context;

    private ImageLoader imageLoader;
    EditText ETnominal;

    String Tid_bencana, Tnama_bencana, Ttgl_kejadian, Tlokasi, Tdeskripsi, Tjml_korban, Tkerugian, Tbatas_akhir, Tnama_relawan;

    Intent intent;
    ArrayList<DonaturModel> item;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donasi_baru);

        identified();

        intent = getIntent();
        context = Donasi.this;

        Tid_bencana = intent.getStringExtra("id_bencana");
        Tnama_bencana = intent.getStringExtra("nama_bencana");
        Ttgl_kejadian = intent.getStringExtra("tgl_kejadian");
        Tlokasi = intent.getStringExtra("lokasi");
        Tdeskripsi = intent.getStringExtra("deskripsi");
        Tjml_korban = intent.getStringExtra("jumlah_korban");
        Tkerugian = intent.getStringExtra("kerugian");
        Tbatas_akhir = intent.getStringExtra("batas_akhir");
        Tnama_relawan = intent.getStringExtra("nama_relawan");

        id_bencana.setText(Tid_bencana);
        nama_bencana.setText(Tnama_bencana);
        ETnama_relawan.setText(Tnama_relawan);
        ETdeadline.setText(Tbatas_akhir);
        ETlokasi.setText(Tlokasi);
        ETtgl_kejadian.setText(Ttgl_kejadian);
        String gambar = intent.getStringExtra("gambar");
        String gambar2 = intent.getStringExtra("gambar2");
        String gambar3 = intent.getStringExtra("gambar3");

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        NetworkImageView imageView1 = (NetworkImageView) findViewById(R.id.gambar);
        NetworkImageView imageView2 = (NetworkImageView) findViewById(R.id.gambar2);
        NetworkImageView imageView3 = (NetworkImageView) findViewById(R.id.gambar3);
        imageView1.setImageUrl(gambar, imageLoader);
        imageView2.setImageUrl(gambar2, imageLoader);
        imageView3.setImageUrl(gambar3, imageLoader);

        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.flipperid);
        viewFlipper.startFlipping();
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
//        nama_relawan = findViewById(R.id.nama_relawan);
//        deadline = findViewById(R.id.deadline);
        gambar = findViewById(R.id.gambar);
        gambar2 = findViewById(R.id.gambar2);
        gambar3 = findViewById(R.id.gambar3);
        ETnominal = (EditText) findViewById(R.id.total_donasi);
        ETtgl_kejadian = findViewById(R.id.tgl_kejadian);
        ETlokasi = findViewById(R.id.lokasi);
        ETdeadline = findViewById(R.id.deadline);
        ETnama_relawan = findViewById(R.id.nama_relawan);
    }

    public void donasiBarang(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_CEK_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("berhasil", "Response: " + response);
                            JSONObject user = new JSONObject(response);

                            if (user.getString("akses").equals("update")) {
                                Toast.makeText(context, "Mohon Lengkapi Profile Sebelum Donasi", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Donasi.this, ProfileActivity.class);
                                startActivity(i);
                            } else {
                                Intent intent = new Intent(Donasi.this, DonasiBarang.class);
                                intent.putExtra("id_bencana",Tid_bencana);
                                intent.putExtra("nama_bencana",Tnama_bencana);

                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(context, "The server unreachable error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Preferences pref = new Preferences(getApplicationContext());
                DonaturModel dm = pref.getUserSession();
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(config.KEY_ID_DONATUR, dm.getIdDonatur());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //        Preferences pref = new Preferences(getApplicationContext());
//        DonaturModel donaturModel = pref.getUserSession();
//        if (donaturModel.getNama().isEmpty() ||
//                donaturModel.getNo_ktp().isEmpty() ||
//                donaturModel.getEmail().isEmpty() ||
//                donaturModel.getTelp().equalsIgnoreCase("null") ||
//                donaturModel.getAlamat().equalsIgnoreCase("null")) {
//            Log.d("response:", "apa yang hilang: "+donaturModel.getNama());
//            Log.d("response:", "apa yang hilang: "+donaturModel.getNo_ktp());
//            Log.d("response:", "apa yang hilang: "+donaturModel.getEmail());
//            Log.d("response:", "apa yang hilang: "+donaturModel.getTelp());
//            Log.d("response:", "apa yang hilang: "+donaturModel.getAlamat());
//            Toast.makeText(context, "Mohon Lengkapi Profile Sebelum Donasi", Toast.LENGTH_LONG).show();
//            Intent i = new Intent(Donasi.this, ProfileActivity.class);
//            startActivity(i);
//        } else {
//    Intent intent = new Intent(this, DonasiBarang.class);
//        intent.putExtra("id_bencana",Tid_bencana);
//        intent.putExtra("nama_bencana",Tnama_bencana);
//
//    startActivity(intent);
//        }
//}

    public void donasiUang(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_CEK_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("berhasil", "Response: " + response);
                            JSONObject user = new JSONObject(response);

                            if (user.getString("akses").equals("update")) {
                                Toast.makeText(context, "Mohon Lengkapi Profile Sebelum Donasi", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Donasi.this, ProfileActivity.class);
                                startActivity(i);
                            } else {
                                Intent intent = new Intent(Donasi.this, DonasiUang.class);
                                intent.putExtra("id_bencana",Tid_bencana);
                                intent.putExtra("nama_bencana",Tnama_bencana);

                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(context, "The server unreachable error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Preferences pref = new Preferences(getApplicationContext());
                DonaturModel dm = pref.getUserSession();
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(config.KEY_ID_DONATUR, dm.getIdDonatur());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public boolean validate() {

        boolean valid_nominal = false;

        String Nominal = ETnominal.getText().toString();

        if (Nominal.isEmpty()) {
            valid_nominal = false;
            ETnominal.setError("Mohon isi nominal!");
        } else if (Nominal.length() <= 0) {
            valid_nominal = false;
            ETnominal.setError("Donasi tidak valid!");
        } else {
            valid_nominal = true;
            ETnominal.setError(null);
        }

        return valid_nominal;
    }

    public void detailBencana(View view) {

        dialog = new AlertDialog.Builder(Donasi.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_bencana, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
//        Intent i = new Intent(DetailDProses.this, DetailDBarang.class);
        nama_bencana = dialogView.findViewById(R.id.nama_bencana);
        String gambar = intent.getStringExtra("gambar");
        String gambar2 = intent.getStringExtra("gambar2");
        String gambar3 = intent.getStringExtra("gambar3");

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        NetworkImageView imageView1 = (NetworkImageView) dialogView.findViewById(R.id.gambar);
        NetworkImageView imageView2 = (NetworkImageView) dialogView.findViewById(R.id.gambar2);
        NetworkImageView imageView3 = (NetworkImageView) dialogView.findViewById(R.id.gambar3);
        imageView1.setImageUrl(gambar, imageLoader);
        imageView2.setImageUrl(gambar2, imageLoader);
        imageView3.setImageUrl(gambar3, imageLoader);

        ViewFlipper viewFlipper = (ViewFlipper) dialogView.findViewById(R.id.flipperid);
        viewFlipper.startFlipping();

        DETtgl_kejadian = dialogView.findViewById(R.id.ETtgl_kejadian);
        DETlokasi = dialogView.findViewById(R.id.ETlokasi);
        DETdeskripsi = dialogView.findViewById(R.id.ETdeskripsi);
        DETjml_korban = dialogView.findViewById(R.id.ETjml_korban);
        DETkerugian = dialogView.findViewById(R.id.ETkerugian);
        DETbatas_akhir = dialogView.findViewById(R.id.ETbatas_akhir);
        DETnama_relawan = dialogView.findViewById(R.id.ETnama_relawan);
        DETtgl_kejadian.setText(Ttgl_kejadian);
        DETlokasi.setText(Tlokasi);
        DETdeskripsi.setText(Tdeskripsi);
        DETjml_korban.setText(Tjml_korban);
        DETkerugian.setText(Tkerugian);
        DETbatas_akhir.setText(Tbatas_akhir);
        DETnama_relawan.setText(Tnama_relawan);
        nama_bencana.setText(intent.getStringExtra("nama_bencana"));

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
