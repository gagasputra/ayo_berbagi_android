package com.example.ayoberbagi_mysql.donatur;

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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;

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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.net.ssl.HttpsURLConnection;

public class DonasiBarang extends AppCompatActivity {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView ShowSelectedImage;

    EditText imageName;

    Bitmap FixBitmap;

    String ImageTag = "image_tag";

    String ImageName = "image_data";

    String JumTot = "jumlah_total";

    String barang1, barang2, barang3, barang4, barang5, barang6, barang7, barang8;

    String Isi_barang = "checked";

    String barang;

    String jml_pakaian = "jml_pakaian";
    String jml_selimut = "jml_selimut";
    String jml_buku = "jml_buku";
    String jml_sembako = "jml_sembako";
    String jml_makan_minum = "jml_makan_minum";
    String jml_medis_obat = "jml_medis_obat";
    String jml_mainan = "jml_mainan";
    String jml_alat_rt = "jml_alat_rt";
    String barang_lain = "barang_lain";
    String jml_lain = "jml_lain";

    String ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ETbarang_lain, ET9;

    String jumlah_total;

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;

    String ConvertImage;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter;

    int RC;

    BufferedReader bufferedReader;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, etBarangLain;
    TextView nama_bencana;
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donasi_barang);
        identified();
        context = DonasiBarang.this;

        intent = getIntent();

        nama_bencana.setText(intent.getStringExtra("nama_bencana"));

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showPictureDialog();


            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageNameFromEditText = imageName.getText().toString();
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

    public void onCheckboxClicked(View view) {

//        int checkbox[] = {
//          R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7, R.id.cb8,
//        };
//        CheckBox[] kategoriCB = new CheckBox[checkbox.length];
//        for(int i=0; i<checkbox.length; i++){
//            kategoriCB[i] = findViewById(checkbox[i]);
//        }
//        for(int a=0; a < kategoriCB.length; a++){
//            if(kategoriCB[a].isChecked()){
//                et1.setEnabled(true);
//                barang += kategoriCB[a].getText().toString() + ";";
//            }
//        }

        if (cb1.isChecked()) {
            et1.setEnabled(true);
        } else {
            et1.setEnabled(false);
            et1.setText("0");
        }

        if (cb2.isChecked()) {
            et2.setEnabled(true);
        } else {
            et2.setEnabled(false);
            et2.setText("0");
        }

        if (cb3.isChecked()) {
            et3.setEnabled(true);
        } else {
            et3.setEnabled(false);
            et3.setText("0");
        }

        if (cb4.isChecked()) {
            et4.setEnabled(true);
        } else {
            et4.setEnabled(false);
            et4.setText("0");
        }

        if (cb5.isChecked()) {
            et5.setEnabled(true);
        } else {
            et5.setEnabled(false);
            et5.setText("0");
        }

        if (cb6.isChecked()) {
            et6.setEnabled(true);
        } else {
            et6.setEnabled(false);
            et6.setText("0");
        }

        if (cb7.isChecked()) {
            et7.setEnabled(true);
        } else {
            et7.setEnabled(false);
            et7.setText("0");
        }

        if (cb8.isChecked()) {
            et8.setEnabled(true);
        } else {
            et8.setEnabled(false);
            et8.setText("0");
        }

        if (cb9.isChecked()) {
            et9.setEnabled(true);
            etBarangLain.setEnabled(true);
        } else {
            etBarangLain.setEnabled(false);
            et9.setEnabled(false);
            etBarangLain.setText("-");
            et9.setText("0");
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
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ShowSelectedImage.setImageBitmap(FixBitmap);
                    UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            ShowSelectedImage.setImageBitmap(FixBitmap);
            UploadImageOnServerButton.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public void UploadImageToServer() {

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

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

                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... params) {
                ET1 = et1.getText().toString();
                ET2 = et2.getText().toString();
                ET3 = et3.getText().toString();
                ET4 = et4.getText().toString();
                ET5 = et5.getText().toString();
                ET6 = et6.getText().toString();
                ET7 = et7.getText().toString();
                ET8 = et8.getText().toString();
                ETbarang_lain = etBarangLain.getText().toString();
                ET9 = et9.getText().toString();

//                    jumlah_total = String.valueOf(Integer.parseInt(ET1) + Integer.parseInt(ET2) + Integer.parseInt(ET3) + Integer.parseInt(ET4) +
//                            Integer.parseInt(ET5) + Integer.parseInt(ET6) + Integer.parseInt(ET7) + Integer.parseInt(ET8));

                Preferences pref = new Preferences(getApplicationContext());
                DonaturModel donaturModel = pref.getUserSession();

                DonasiBarang.ImageProcessClass imageProcessClass = new DonasiBarang.ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<>();

                HashMapParams.put(jml_pakaian, ET1);
                HashMapParams.put(jml_selimut, ET2);
                HashMapParams.put(jml_buku, ET3);
                HashMapParams.put(jml_sembako, ET4);
                HashMapParams.put(jml_makan_minum, ET5);
                HashMapParams.put(jml_medis_obat, ET6);
                HashMapParams.put(jml_mainan, ET7);
                HashMapParams.put(jml_alat_rt, ET8);
                HashMapParams.put(barang_lain, ETbarang_lain);
                HashMapParams.put(jml_lain, ET9);


//                    HashMapParams.put(JumTot, jumlah_total);

                HashMapParams.put(ImageTag, GetImageNameFromEditText);
                HashMapParams.put(config.KEY_ID_BENCANA, intent.getStringExtra("id_bencana"));
                HashMapParams.put(config.KEY_ID_DONATUR, donaturModel.getIdDonatur());

//                    HashMapParams.put(config.KEY_ID_DONASI, intent.getStringExtra("id_donasi"));
                HashMapParams.put(ImageName, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(config.URL_DONASI_BARANG, HashMapParams);

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

    public void identified() {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);
        et8 = findViewById(R.id.et8);
        et9 = findViewById(R.id.et9);
        etBarangLain = findViewById(R.id.barang_lain);

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        cb8 = findViewById(R.id.cb8);
        cb9 = findViewById(R.id.cb9);

        nama_bencana = findViewById(R.id.nama_bencana);
        GetImageFromGalleryButton = (Button) findViewById(R.id.buttonSelect);
        UploadImageOnServerButton = (Button) findViewById(R.id.kirimDonasi);
        ShowSelectedImage = (ImageView) findViewById(R.id.imageView);
        imageName = (EditText) findViewById(R.id.imageName);
    }
}
