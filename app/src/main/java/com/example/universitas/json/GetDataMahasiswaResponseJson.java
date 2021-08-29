package com.example.universitas.json;

import com.example.universitas.models.Mahasiswa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDataMahasiswaResponseJson {

    @Expose
    @SerializedName("home")
    private List<Mahasiswa> data = new ArrayList<>();

    public List<Mahasiswa> getData() {
        return data;
    }

    public void setData(List<Mahasiswa> data) {
        this.data = data;
    }
}
