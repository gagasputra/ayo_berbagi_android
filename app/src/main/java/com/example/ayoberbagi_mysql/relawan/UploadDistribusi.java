package com.example.ayoberbagi_mysql.relawan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ayoberbagi_mysql.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadDistribusi extends AppCompatActivity {

    TextView TVnama_bencana;
    EditText ETtotal_donasi, ETtgl_awal_distribusi, ETtgl_akhir_distribusi, ETlokasi_distribusi, ETlaporan;

    Intent intent;
    String id_bencana;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_distribusi);

        TVnama_bencana = findViewById(R.id.nama_bencana);
        ETtotal_donasi = findViewById(R.id.ETtotal_donasi);
        ETtgl_awal_distribusi = findViewById(R.id.tgl_awal_distribusi);
        ETtgl_akhir_distribusi = findViewById(R.id.tgl_akhir_distribusi);
        ETlokasi_distribusi = findViewById(R.id.ETlokasi_distribusi);
        ETlaporan = findViewById(R.id.ETlaporan);

        intent = getIntent();
        id_bencana = intent.getStringExtra("id_bencana");
        TVnama_bencana.setText(intent.getStringExtra("nama_bencana"));
        ETtotal_donasi.setText("Rp. " + intent.getStringExtra("total_donasi") + ".00");

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener awal = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAwal();
            }

        };

        final DatePickerDialog.OnDateSetListener akhir = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAkhir();
            }

        };

        ETtgl_awal_distribusi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UploadDistribusi.this, awal, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ETtgl_akhir_distribusi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UploadDistribusi.this, akhir, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateAwal() {
        String myFormat = "YYYY/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ETtgl_awal_distribusi.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateAkhir() {
        String myFormat = "YYYY/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ETtgl_akhir_distribusi.setText(sdf.format(myCalendar.getTime()));
    }

    public void uploadImage(View view) {
        Intent intent = new Intent(UploadDistribusi.this, UploadBuktiDistribusi.class);
        intent.putExtra("id_bencana", id_bencana);
        intent.putExtra("nama_bencana", TVnama_bencana.getText().toString());
        intent.putExtra("total_donasi", ETtotal_donasi.getText().toString());
        intent.putExtra("tgl_awal_distribusi", ETtgl_awal_distribusi.getText().toString());
        intent.putExtra("tgl_akhir_distribusi", ETtgl_akhir_distribusi.getText().toString());
        intent.putExtra("lokasi_distribusi", ETlokasi_distribusi.getText().toString());
        intent.putExtra("laporan", ETlaporan.getText().toString());
        startActivity(intent);
    }
}
