package com.example.ayoberbagi_mysql.relawan;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

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

public class UploadBuktiDistribusi extends AppCompatActivity {

    Intent intent;

    TextView TVnama_bencana;
    Button inputImage, inputImage2, inputImage3, uploadSemua;

    ImageView gambar1, gambar2, gambar3;

    Bitmap FixBitmap, FixBitmap2, FixBitmap3;

    String ImageTag1 = "image_tag1";
    String ImageTag2 = "image_tag2";
    String ImageTag3 = "image_tag3";

    String ImageName1 = "image_data1";
    String ImageName2 = "image_data2";
    String ImageName3 = "image_data3";

    String getImageName1, getImageName2, getImageName3;

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream1, byteArrayOutputStream2, byteArrayOutputStream3;

    byte[] byteArray1, byteArray2, byteArray3;

    String ConvertImage1, ConvertImage2, ConvertImage3;

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
    private int GALLERY3 = 5, CAMERA3 = 6;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    Context context;
    String id_bencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bukti_distribusi);

        context = UploadBuktiDistribusi.this;

        TVnama_bencana = findViewById(R.id.nama_bencana);
        inputImage = findViewById(R.id.inputImage);
        inputImage2 = findViewById(R.id.inputImage2);
        inputImage3 = findViewById(R.id.inputImage3);
        gambar1 = findViewById(R.id.gambar1);
        gambar2 = findViewById(R.id.gambar2);
        gambar3 = findViewById(R.id.gambar3);
        uploadSemua = findViewById(R.id.inputSemua);

        intent = getIntent();
        id_bencana = intent.getStringExtra("id_bencana");
        TVnama_bencana.setText(intent.getStringExtra("nama_bencana"));

        Log.d("intent : ", id_bencana);

        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();
            }
        });
        inputImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog2();
            }
        });
        inputImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog3();
            }
        });

        uploadSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getImageName1 = id_bencana;
                getImageName2 = id_bencana;
                getImageName3 = id_bencana;
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

    private void showPictureDialog3() {
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

                                startActivityForResult(galleryIntent, GALLERY3);
                                break;
                            case 1:
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA3);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

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
                    gambar1.setImageBitmap(FixBitmap);
                    uploadSemua.setVisibility(View.VISIBLE);

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
                    gambar2.setImageBitmap(FixBitmap2);
                    uploadSemua.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == GALLERY3) {
            if (data != null) {
                Uri contentURI3 = data.getData();
                try {
                    FixBitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI3);

                    byteArrayOutputStream3 = new ByteArrayOutputStream();
                    FixBitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                    byteArray3 = byteArrayOutputStream3.toByteArray();
                    ConvertImage3 = Base64.encodeToString(byteArray3, Base64.DEFAULT);

                    Log.d("gambar1", "gambar1: " + FixBitmap3);
                    Log.d("gambar1", "uri" + contentURI3);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    gambar3.setImageBitmap(FixBitmap3);
                    uploadSemua.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == CAMERA1) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            gambar1.setImageBitmap(FixBitmap);
            uploadSemua.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CAMERA2) {
            FixBitmap2 = (Bitmap) data.getExtras().get("data");
            gambar2.setImageBitmap(FixBitmap2);
            uploadSemua.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CAMERA3) {
            FixBitmap3 = (Bitmap) data.getExtras().get("data");
            gambar3.setImageBitmap(FixBitmap3);
            uploadSemua.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadImageToServer() {

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(context, string1, Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, ActivityRelawan.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... params) {
                Preferences pref = new Preferences(getApplicationContext());
                RelawanModel relawanModel = pref.getRelawanSession();

                UploadBuktiDistribusi.ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put("id_bencana", id_bencana);
                HashMapParams.put("nama_bencana", intent.getStringExtra("nama_bencana"));
                HashMapParams.put("total_donasi", intent.getStringExtra("total_donasi"));
                HashMapParams.put("tgl_awal_distribusi", intent.getStringExtra("tgl_awal_distribusi"));
                HashMapParams.put("tgl_akhir_distribusi", intent.getStringExtra("tgl_akhir_distribusi"));
                HashMapParams.put("lokasi_distribusi", intent.getStringExtra("lokasi_distribusi"));
                HashMapParams.put("laporan", intent.getStringExtra("laporan"));
                HashMapParams.put(ImageTag1, getImageName1);
                HashMapParams.put(ImageTag2, getImageName2);
                HashMapParams.put(ImageTag3, getImageName3);
                HashMapParams.put(ImageName1, ConvertImage1);
                Log.d("gambar1", "gambar1: " + ConvertImage1);
                HashMapParams.put(ImageName2, ConvertImage2);
                Log.d("gambar1", "gambar2: " + ConvertImage2);
                HashMapParams.put(ImageName3, ConvertImage3);
                Log.d("gambar1", "gambar3: " + ConvertImage3);
                HashMapParams.put(config.KEY_ID_PJ, relawanModel.getIdPj());

                String FinalData = imageProcessClass.ImageHttpRequest(config.URL_UPLOAD_DISTRIBUSI, HashMapParams);
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
