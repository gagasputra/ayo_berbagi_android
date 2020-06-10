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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.Preferences;
import com.example.ayoberbagi_mysql.config.config;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;
import com.squareup.picasso.Picasso;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    Button GetImageFromGalleryButton, saveProfile;

    ImageView foto;

    EditText imageName;

    Bitmap FixBitmap;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    Context context;

    TextView TVusername;

    EditText ETnama, ETno_ktp, ETemail, ETnohp, ETalamat;

    String hasil_id_relawan;

    String Nama = "nama";
    String No_KTP = "no_ktp";
    String Email = "email";
    String NoHP = "telp";
    String Alamat = "alamat";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_relawan);
        context = EditProfile.this;

        TVusername = findViewById(R.id.username);
        ETnama = findViewById(R.id.ETeditNama);
        ETno_ktp = findViewById(R.id.ETeditKTP);
        ETemail = findViewById(R.id.ETeditEmail);
        ETnohp = findViewById(R.id.ETeditTelp);
        ETalamat = findViewById(R.id.ETeditAlamat);
        GetImageFromGalleryButton = (Button)findViewById(R.id.pilihGambar);
        saveProfile = (Button)findViewById(R.id.saveProfile);
        foto = (ImageView)findViewById(R.id.foto);

        Intent i = getIntent();
        hasil_id_relawan = i.getStringExtra("id_relawan");
        TVusername.setText(i.getStringExtra("username"));
        ETnama.setText(i.getStringExtra("nama_relawan"));
        ETno_ktp.setText(i.getStringExtra("no_ktp"));
        ETemail.setText(i.getStringExtra("email"));
        ETnohp.setText(i.getStringExtra("nohp"));
        ETalamat.setText(i.getStringExtra("alamat"));
        String userImg = i.getStringExtra("foto");
        setProfileImage(config.URL_KOSONGAN + userImg);

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();


            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameFromEditText = hasil_id_relawan;
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
                    foto.setImageBitmap(FixBitmap);
                    saveProfile.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(FixBitmap);
            saveProfile.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public void UploadImageToServer() {

        if (validate()) {

            FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byteArray = byteArrayOutputStream.toByteArray();

            ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

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

                    Intent i = new Intent(context, ActivityRelawan.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);

                }

                @Override
                protected String doInBackground(Void... params) {

                    Preferences pref = new Preferences(getApplicationContext());
                    RelawanModel relawanModel = pref.getRelawanSession();

                    EditProfile.ImageProcessClass imageProcessClass = new ImageProcessClass();

                    HashMap<String, String> HashMapParams = new HashMap<String, String>();
                    HashMapParams.put(Nama, ETnama.getText().toString());
                    HashMapParams.put(No_KTP, ETno_ktp.getText().toString());
                    HashMapParams.put(Email, ETemail.getText().toString());
                    HashMapParams.put(NoHP, ETnohp.getText().toString());
                    HashMapParams.put(Alamat, ETalamat.getText().toString());
                    HashMapParams.put(ImageTag, GetImageNameFromEditText);
                    HashMapParams.put(config.KEY_ID_PJ, hasil_id_relawan);
                    HashMapParams.put(ImageName, ConvertImage);
                    String FinalData = imageProcessClass.ImageHttpRequest(config.URL_UPLOAD_PP_RELAWAN, HashMapParams);
                    Log.d("final:", FinalData);

                    return FinalData;
                }
            }
            AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
            AsyncTaskUploadClassOBJ.execute();
        }
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

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(EditProfile.this);
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

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Nama = ETnama.getText().toString();

        //Handling validation for Email field
        if (Nama.isEmpty()){
            valid = false;
            ETnama.setError("Nama Tidak Boleh Kosong!");
        } else {
            valid = true;
            ETnama.setError(null);
        }

        return valid;
    }
}
