package com.example.ayoberbagi_mysql;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.toolbox.ImageLoader;
import com.example.ayoberbagi_mysql.config.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegisterRelawan3 extends AppCompatActivity {

    LinearLayout info;

    Button ambil_ktp, ambil_selfie, register;

    EditText imageName;

    Bitmap FixBitmap, FixBitmap2;

    String ImageTag1 = "image_tag1";
    String ImageTag2 = "image_tag2";

    String ImageName1 = "image_data1";
    String ImageName2 = "image_data2";

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream1, byteArrayOutputStream2;

    byte[] byteArray1, byteArray2;

    String ConvertImage1, ConvertImage2;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter;

    int RC;

    BufferedReader bufferedReader;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY1 = 1, CAMERA1 = 2;
    private int GALLERY2 = 3, CAMERA2 = 4;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    private Button btnChooseFile;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;

    ImageView foto_ktp, foto_selfie;
    String nama, email, no_ktp, no_telp, bank, no_rek, username, password, pertanyaan, jawaban;
    Context context;

    private ImageLoader imageLoader;

    Intent intent;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.register_relawan_3);

        context = RegisterRelawan3.this;

        intent = getIntent();
        nama = intent.getStringExtra("nama");
        email = intent.getStringExtra("email");
        no_ktp = intent.getStringExtra("no_ktp");
        no_telp = intent.getStringExtra("no_telp");
        bank = intent.getStringExtra("bank");
        no_rek = intent.getStringExtra("no_rek");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        pertanyaan = intent.getStringExtra("pertanyaan");
        jawaban = intent.getStringExtra("jawaban");

        Log.d("intent", "hasil = " + nama+","+email+","+no_ktp+","+no_telp+","+bank+","+no_rek+","+username+
                ","+pertanyaan+","+jawaban+","+password);

        ambil_ktp = findViewById(R.id.pilihFotoKTP);
        ambil_selfie = findViewById(R.id.pilihFotoSelfie);

        foto_ktp = findViewById(R.id.hasil_ktp);
        foto_selfie = findViewById(R.id.hasil_selfie);
        register = findViewById(R.id.buttonRegister);

        ambil_ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        ambil_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog2();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadImageToServer();

            }
        });

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(galleryIntent, GALLERY1);
                                break;
                            case 1:
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA1);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showPictureDialog2() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(galleryIntent, GALLERY2);
                                break;
                            case 1:
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA2);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY1) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    byteArrayOutputStream1 = new ByteArrayOutputStream();
                    FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    byteArray1 = byteArrayOutputStream1.toByteArray();
                    ConvertImage1 = Base64.encodeToString(byteArray1, Base64.DEFAULT);

                    Log.d("gambar1", "gambar1: " + FixBitmap);
                    Log.d("gambar1", "uri" + contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    foto_ktp.setImageBitmap(FixBitmap);
                    register.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        if (requestCode == GALLERY2) {
            if (data != null) {
                Uri contentURI2 = data.getData();
                try {
                    FixBitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI2);
                    byteArrayOutputStream2 = new ByteArrayOutputStream();
                    FixBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    byteArray2 = byteArrayOutputStream2.toByteArray();
                    ConvertImage2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);

                    Log.d("gambar1", "gambar1: " + FixBitmap2);
                    Log.d("gambar1", "uri" + contentURI2);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    foto_selfie.setImageBitmap(FixBitmap2);
                    register.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == CAMERA1) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            foto_ktp.setImageBitmap(FixBitmap);
            register.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CAMERA2) {
            FixBitmap2 = (Bitmap) data.getExtras().get("data");
            foto_selfie.setImageBitmap(FixBitmap2);
            register.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public void UploadImageToServer() {

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Loading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(context, string1, Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... params) {

                RegisterRelawan3.ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put("username", username);
                HashMapParams.put("password", password);
                HashMapParams.put("pertanyaan", pertanyaan);
                HashMapParams.put("jawaban", jawaban);

                HashMapParams.put("nama", nama);
                HashMapParams.put("email", email);
                HashMapParams.put("no_ktp", no_ktp);
                HashMapParams.put("no_telp", no_telp);
                HashMapParams.put("bank", bank);
                HashMapParams.put("no_rek", no_rek);
                HashMapParams.put(ImageName1, ConvertImage1);
                HashMapParams.put(ImageName2, ConvertImage2);

                String FinalData = imageProcessClass.ImageHttpRequest(config.URL_REGISTER_RELAWAN, HashMapParams);
                Log.d("final", "Final Data: " + FinalData);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            } else {

                Toast.makeText(context, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }
}