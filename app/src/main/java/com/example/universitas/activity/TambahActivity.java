package com.example.universitas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.universitas.R;
import com.example.universitas.constants.Constant;
import com.example.universitas.json.MahasiswaRequestJson;
import com.example.universitas.json.MahasiswaResponseJson;
import com.example.universitas.utils.api.ServiceGenerator;
import com.example.universitas.utils.api.service.UniversitasService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    ImageView backbtn;
    EditText phone,nik,nama, email,alamat,jurusan,fakultas;
    TextView tanggal,countryCode,textnotif;
    Button submit;
    RelativeLayout rlnotif;
    Spinner gender;
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String dateview, disableback;
    String[] spinnergender;
    String country_iso_code = "en";
    String verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        nama = findViewById(R.id.namamitra);
        nik = findViewById(R.id.nikmitra);
        email = findViewById(R.id.email);
        tanggal = findViewById(R.id.tanggalmitra);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        gender = findViewById(R.id.karyawanjender);
        alamat = findViewById(R.id.alamatmitra);
        jurusan = findViewById(R.id.jurusanKampus);
        fakultas = findViewById(R.id.fakultasKampus);


        spinnergender = getResources().getStringArray(R.array.jender_karyawan);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        countryCode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countryCode.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        //jender spinner
        ArrayAdapter<String> genderSpinner = new ArrayAdapter<>(this, R.layout.spinner, spinnergender);
        genderSpinner.setDropDownViewResource(R.layout.spinner);
        gender.setAdapter(genderSpinner);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailvalidate = email.getText().toString();

                 if (TextUtils.isEmpty(phone.getText().toString())) {
                    notif(getString(R.string.phoneempty));
                } else if (TextUtils.isEmpty(nama.getText().toString())) {
                    notif(getString(R.string.namaempty));
                } else if (TextUtils.isEmpty(nik.getText().toString())) {
                    notif(getString(R.string.nikempty));
                }else if (TextUtils.isEmpty(email.getText().toString())) {
                    notif(getString(R.string.emailempty));
                } else if (gender.getSelectedItemPosition() == 0) {
                    notif(getString(R.string.jenderempty));
                }else if (TextUtils.isEmpty(alamat.getText().toString())) {
                    notif(getString(R.string.alamatempty));
                }else if (TextUtils.isEmpty(tanggal.getText().toString())) {
                    notif(getString(R.string.birtdayempty));
                } else if (TextUtils.isEmpty(jurusan.getText().toString())) {
                    notif(getString(R.string.jurusanempty));
                }  else if (TextUtils.isEmpty(fakultas.getText().toString())) {
                    notif(getString(R.string.fakultasempty));
                }  else if (!emailvalidate.matches(emailPattern)) {
                    notif("wrong email format!");
                }   else {
                    upload();
                }
            }
        });


        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTanggal();
            }
        });
        disableback = "false";
        verify = "false";
    }



    private void showTanggal() {

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        tanggal.setText(dateFormatterview.format(date_ship_millis));
                        dateview = dateFormatter.format(date_ship_millis);
                    }
                }
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.parentcolor));
        datePicker.show(getFragmentManager(), "Tanggal");
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void upload() {
        MahasiswaRequestJson request = new MahasiswaRequestJson();
        request.setNamamhs(nama.getText().toString());
        request.setPhonemhs(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setNimmhs(nik.getText().toString());
        request.setEmailmhs(email.getText().toString());
        request.setAlamatmhs(alamat.getText().toString());
        request.setJekelmhs(String.valueOf(gender.getSelectedItem()));
        request.setPeriodemhs(tanggal.getText().toString());
        request.setFakultasmhs(fakultas.getText().toString());
        request.setJurusanmhs(jurusan.getText().toString());

        UniversitasService service = ServiceGenerator.createService(UniversitasService.class, request.getNamamhs(),request.getNimmhs());
        service.add(request).enqueue(new Callback<MahasiswaResponseJson>() {
            @Override
            public void onResponse(Call<MahasiswaResponseJson> call, Response<MahasiswaResponseJson> response)
            {
                if (response.isSuccessful()){
                    Intent main = new Intent(TambahActivity.this,MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    notif(response.body().getData());
                }else
                {
                    notif("error");
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponseJson> call, Throwable t)
            {
                t.printStackTrace();
                notif("error!");
            }
        });

    }
}