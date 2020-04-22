//package com.example.ayoberbagi_mysql;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.ayoberbagi_mysql.donatur.adapter.AdapterViewBencana;
//import com.example.ayoberbagi_mysql.config.config;
//import com.example.ayoberbagi_mysql.donatur.model.Model;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class view_bencana extends AppCompatActivity {
//
//    SharedPreferences sp;
//    SharedPreferences.Editor editor;
//    RecyclerView recyclerView;
//    RecyclerView.Adapter mAdapter;
//    RecyclerView.LayoutManager mManager;
//    ProgressDialog pd;
//    ArrayList<Model> mItems;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_bencana);
//
//        pd = new ProgressDialog(view_bencana.this);
//        recyclerView = (RecyclerView) findViewById(R.id.list_data);
//        mItems = new ArrayList<>();
//        mManager = new LinearLayoutManager(view_bencana.this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(mManager);
//        mAdapter = new AdapterViewBencana(view_bencana.this, mItems);
//        recyclerView.setAdapter(mAdapter);
//
//        loadjson();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.new_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.profile:
//                Toast.makeText(this, "Menu Profile", Toast.LENGTH_LONG).show();
//                return true;
//            case  R.id.logout:
//                sp = this.getSharedPreferences("sp", MODE_PRIVATE);
//                editor = sp.edit();
//                editor.clear().commit();
//
//                Intent i = new Intent(this, LoginActivity.class);
//                startActivity(i);
//                finish();
//
//            default:
//                return true;
//        }
//    }
//
//    public void loadjson(){
//        pd.setMessage("Loading");
//        pd.setCancelable(false);
//        pd.show();
//
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, config.URL_VIEW_BENCANA,null,
//                new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                pd.cancel();
//                Log.d("volley", "response : " + response.toString());
//                for (int i=0; i < response.length(); i++){
//                    try {
//                        JSONObject data = response.getJSONObject(i);
//                        Model md = new Model();
//                        md.setNama_bencana(data.getString("nama_bencana"));
//                        md.setNama_relawan(data.getString("nama"));
//                        md.setTotal_donasi(data.getString("total_donasi"));
//                        md.setGambar_bencana(config.URL_GAMBAR + data.getString("gambar"));
//                        md.setDeadline(data.getString("deadline"));
//
//                        mItems.add(md);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pd.cancel();
//                Log.d("volley", "error : " + error.getMessage());
//            }
//        });
//        Volley.newRequestQueue(this).add(arrayRequest);
//    }
//
//
//
//
//}
