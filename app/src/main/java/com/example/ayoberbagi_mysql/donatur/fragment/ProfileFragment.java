package com.example.ayoberbagi_mysql.donatur.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class ProfileFragment extends Fragment{

    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sp;

    NetworkImageView gambar;
    private ImageLoader imageLoader;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

//        btn_logout = view.findViewById(R.id.btn_logout);
//        btn_logout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        sp = this.getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        id = this.getActivity().getIntent().getStringExtra(config.TAG_ID);
        username = this.getActivity().getIntent().getStringExtra(config.TAG_USERNAME);

        txt_username = view.findViewById(R.id.nama_donatur);
        gambar = (NetworkImageView) view.findViewById(R.id.gambar);

        imageLoader = ImageAdapter.getInstance(getContext()).getImageLoader();

        setToView(view);
        Logout(view);

        return view;
    }

    private void Logout(View view) {


        Preferences pref = new Preferences(getContext());
        pref.destroyUserSession();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    private void setToView(View view) {
        final View v = view;
        Preferences pref = new Preferences(getContext());
        DonaturModel donaturModel = pref.getUserSession();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_PROFILE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject user = new JSONObject(response);
                            txt_username.setText(user.getString("nama"));

                            String userImg = user.getString("foto");
                            String imgUrl = config.URL_GAMBAR;
                            setProfileImage(v, imgUrl + userImg);
                            Log.d("response", "berhasil " + response);
                        } catch(JSONException e){
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Preferences pref = new Preferences(getContext());
                DonaturModel donaturModel = pref.getUserSession();
                //Adding parameters to request
                params.put(config.KEY_ID_DONATUR, donaturModel.getIdDonatur());
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void setProfileImage(View view, String imgUrl){
        ImageView profileImage = view.findViewById(R.id.foto);
        Picasso.get().load(imgUrl).into(profileImage);
    }
}