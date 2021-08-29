package com.example.universitas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.universitas.R;
import com.example.universitas.item.MahasiswaItem;
import com.example.universitas.json.GetDataMahasiswaResponseJson;
import com.example.universitas.models.Mahasiswa;
import com.example.universitas.utils.api.ServiceGenerator;
import com.example.universitas.utils.api.service.UniversitasService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    RecyclerView rvMahasiswa;
    List<Mahasiswa> mahasiswasmodels;
    MahasiswaItem mahasiswaItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rvMahasiswa = findViewById(R.id.rv_mahasiswa);
        rvMahasiswa.setHasFixedSize(true);
        rvMahasiswa.setNestedScrollingEnabled(false);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        getDataMahasiswa();
    }

    private void getDataMahasiswa() {
        UniversitasService service = ServiceGenerator.createService(UniversitasService.class,"admin","admin");
        service.getMahasiswa().enqueue(new Callback<GetDataMahasiswaResponseJson>() {
            @Override
            public void onResponse(Call<GetDataMahasiswaResponseJson> call, Response<GetDataMahasiswaResponseJson> response) {
                if (response.isSuccessful()){
                    mahasiswasmodels = response.body().getData();
                    mahasiswaItem = new MahasiswaItem(getApplicationContext(),mahasiswasmodels,R.layout.layout_item_mahasiswa);
                    rvMahasiswa.setAdapter(mahasiswaItem);
                }else
                {
                    Toast.makeText(DetailActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDataMahasiswaResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}