package com.example.universitas.utils.api.service;

import com.example.universitas.json.DeleteMahasiswaRequetsJson;
import com.example.universitas.json.GetDataMahasiswaResponseJson;
import com.example.universitas.json.MahasiswaRequestJson;
import com.example.universitas.json.MahasiswaResponseJson;
import com.example.universitas.json.ResponseJson;
import com.example.universitas.json.UpdateRequestJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UniversitasService {

    @POST("mahasiswa/tambahdata")
    Call<MahasiswaResponseJson> add (@Body MahasiswaRequestJson param);

    @GET("mahasiswa/lihatdata")
    Call<GetDataMahasiswaResponseJson> getMahasiswa();

    @POST("mahasiswa/edit_mahasiswa")
    Call<ResponseJson> editmahasiswa (@Body UpdateRequestJson param);

    @POST("mahasiswa/delete_mahasiswa")
    Call<ResponseJson> deletemahasiswa (@Body DeleteMahasiswaRequetsJson param);

}
