package com.example.universitas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.universitas.R;
import com.example.universitas.json.DeleteMahasiswaRequetsJson;
import com.example.universitas.json.ResponseJson;
import com.example.universitas.json.UpdateRequestJson;
import com.example.universitas.utils.api.ServiceGenerator;
import com.example.universitas.utils.api.service.UniversitasService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    String id_mhs,nama_mhs,phone_mhs,nim_mhs,email_mhs,jekel_mhs,alamat_mhs,periode_mhs,jurusan_mhs,fakultas_mhs;
    ImageView backbtn;
    EditText phone,nik,nama, email,alamat,jurusan,fakultas;
    TextView tanggal,countryCode,textnotif;
    Button Update,Delete;
    RelativeLayout rlnotif;
    EditText gender;
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String dateview, disableback;
    String[] spinnergender;
    String country_iso_code = "en";
    String verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //mengambil data intent dari class mahasiswaitem
        Intent intent = getIntent();
        id_mhs = intent.getStringExtra("id");
        nama_mhs = intent.getStringExtra("nama");
        phone_mhs = intent.getStringExtra("phone");
        nim_mhs = intent.getStringExtra("nim");
        email_mhs = intent.getStringExtra("email");
        jekel_mhs = intent.getStringExtra("jekel");
        alamat_mhs = intent.getStringExtra("alamat");
        periode_mhs = intent.getStringExtra("periode");
        jurusan_mhs = intent.getStringExtra("jurusan");
        fakultas_mhs = intent.getStringExtra("fakultas");

        //definisi variabel id dari layout update
        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumberupdate);
        nama = findViewById(R.id.namaupdate);
        nik = findViewById(R.id.nikupdate);
        email = findViewById(R.id.emailupdate);
        tanggal = findViewById(R.id.tanggalupdate);
        Update = findViewById(R.id.update);
        Delete = findViewById(R.id.delete);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycodeupdate);
        gender = findViewById(R.id.karyawanupdate);
        alamat = findViewById(R.id.alamatupdate);
        jurusan = findViewById(R.id.jurusanupdate);
        fakultas = findViewById(R.id.fakultasupdate);

        //menampilkan data ke halaman update
        Log.e("MahasiswaID", id_mhs );
        phone.setText(phone_mhs);
        nama.setText(nama_mhs);
        nik.setText(nim_mhs);
        email.setText(email_mhs);
        tanggal.setText(periode_mhs);
        gender.setText(jekel_mhs);
        alamat.setText(alamat_mhs);
        jurusan.setText(jurusan_mhs);
        fakultas.setText(fakultas_mhs);

        //onclick update

        Update.setOnClickListener(new View.OnClickListener() {
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
                } else if (TextUtils.isEmpty(gender.getText().toString())) {
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
                    ClickUpdate();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickDelete();
            }
        });

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
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTanggal();
            }
        });
        disableback = "false";
        verify = "false";
    }

    private void ClickDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogStyle);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.drawable.student1);
        builder.setMessage(getString(R.string.deleteinfo));
        builder.setPositiveButton(getString(R.string.ya), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void delete() {
        DeleteMahasiswaRequetsJson requets = new DeleteMahasiswaRequetsJson();
        requets.setId(id_mhs);
        UniversitasService service = ServiceGenerator.createService(UniversitasService.class,"admin","admin");
        service.deletemahasiswa(requets).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
               if (response.isSuccessful()){
                   Intent home = new Intent(UpdateActivity.this,DetailActivity.class);
                   home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(home);
               }else
               {
                   notif("error");
               }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                t.printStackTrace();
                notif("error!!");
            }
        });
    }

    private void ClickUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogStyle);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.drawable.student1);
        builder.setMessage(getString(R.string.updateinfo));
        builder.setPositiveButton(getString(R.string.ya), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update();
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

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

    private void update()
    {
        UpdateRequestJson request = new UpdateRequestJson();
        request.setId(id_mhs);
        request.setNamamhs(nama.getText().toString());
        request.setPhonemhs(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setNimmhs(nik.getText().toString());
        request.setEmailmhs(email.getText().toString());
        request.setAlamatmhs(alamat.getText().toString());
        request.setJekelmhs(gender.getText().toString());
        request.setPeriodemhs(tanggal.getText().toString());
        request.setFakultasmhs(fakultas.getText().toString());
        request.setJurusanmhs(jurusan.getText().toString());

        UniversitasService service = ServiceGenerator.createService(UniversitasService.class, request.getNamamhs(),request.getNimmhs());
        service.editmahasiswa(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()){
                    Intent main = new Intent(UpdateActivity.this,DetailActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                }else
                {
                    notif("error");
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                t.printStackTrace();
                notif("error!!");
            }
        });
    }
}