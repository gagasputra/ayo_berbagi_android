package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.LoginActivity;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.ImageAdapter;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    Button btn_logout;
    View view;
    TextView txt_id, txt_nama, txt_noktp, txt_username, txt_nohp, txt_email, txt_alamat;
    String id, username, userImg;
    SharedPreferences sp;
    Intent i;

    CircleImageView foto;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        Intent i = getIntent();
        id = i.getStringExtra(config.TAG_ID);
//        username = i.getStringExtra(config.TAG_USERNAME);

        txt_username = findViewById(R.id.username);
        txt_nama = findViewById(R.id.nama_donatur);
        txt_noktp = findViewById(R.id.no_ktp);
        txt_nohp = findViewById(R.id.nohp);
        txt_email = findViewById(R.id.email);
        txt_alamat = findViewById(R.id.alamat);

        foto = findViewById(R.id.foto);

        imageLoader = ImageAdapter.getInstance(this).getImageLoader();
        Preferences pref = new Preferences(getApplicationContext());
        DonaturModel donaturModel = pref.getUserSession();
        username = donaturModel.getUsername();
        txt_username.setText("@" + username);
        setToView();

//        btn_logout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Preferences pref = new Preferences();
//                pref.destroyUserSession();
//
//                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
//                finish();
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            Logout(item);
        }
        return true;
    }

    private void setToView() {
        Preferences pref = new Preferences(getApplicationContext());
        DonaturModel donaturModel = pref.getUserSession();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_PROFILE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject user = new JSONObject(response);
                            txt_nama.setText(user.getString("nama"));
                            txt_noktp.setText(user.getString("no_ktp"));
                            String no_hp = user.getString("telp");
                            if (no_hp.equalsIgnoreCase("null")) {
                                txt_nohp.setText("-");
                            } else {
                                txt_nohp.setText(no_hp);
                            }
                            txt_email.setText(user.getString("email"));
                            String TValamat = user.getString("alamat");
                            if (no_hp.equalsIgnoreCase("null")) {
                                txt_alamat.setText("-");
                            } else {
                                txt_alamat.setText(TValamat);
                            }

                            userImg = user.getString("foto");
                            setProfileImage(config.URL_KOSONGAN + userImg);
                            Log.d("response", "berhasil " + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Preferences pref = new Preferences(getApplicationContext());
                DonaturModel donaturModel = pref.getUserSession();
                //Adding parameters to request
                params.put(config.KEY_ID_DONATUR, donaturModel.getIdDonatur());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(ProfileActivity.this);
        Picasso.get().load(imgUrl).into(foto);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleImageView view = (CircleImageView) v;
                imagePopup.viewPopup();
            }
        });
    }

    public boolean Logout(MenuItem item) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle("Apakah anda yakin ingin logout?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preferences pref = new Preferences(getApplicationContext());
                        pref.destroyUserSession();

                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
        return true;
    }

    public boolean editProfile(MenuItem item) {
        Preferences pref = new Preferences(getApplicationContext());
        DonaturModel donaturModel = pref.getUserSession();
        Intent i = new Intent(ProfileActivity.this, EditProfile.class);
        i.putExtra("id_donatur", donaturModel.getIdDonatur());
        i.putExtra("username", txt_username.getText());
        i.putExtra("nama_donatur", txt_nama.getText());
        i.putExtra("no_ktp", txt_noktp.getText());
        i.putExtra("email", txt_email.getText());
        i.putExtra("nohp", txt_nohp.getText());
        i.putExtra("alamat", txt_alamat.getText());
        i.putExtra("foto", userImg);
        startActivity(i);
        Log.d("intent", "intent: " + donaturModel.getIdDonatur());
        return true;
    }

    public boolean editAkun(MenuItem item) {
        Preferences pref = new Preferences(getApplicationContext());
        DonaturModel donaturModel = pref.getUserSession();
        Intent i = new Intent(ProfileActivity.this, EditAkun.class);
        i.putExtra("id_donatur", donaturModel.getIdDonatur());
        i.putExtra("username", txt_username.getText());
        startActivity(i);
        Log.d("intent", "intent: " + donaturModel.getIdDonatur());
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }
}
