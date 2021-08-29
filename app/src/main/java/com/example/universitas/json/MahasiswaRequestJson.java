package com.example.universitas.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MahasiswaRequestJson {
    @Expose
    @SerializedName("nim")
    private String nimmhs;

    @Expose
    @SerializedName("nama")
    private String namamhs;

    @Expose
    @SerializedName("phone")
    private String phonemhs;

    @Expose
    @SerializedName("email")
    private String emailmhs;

    @Expose
    @SerializedName("alamat")
    private String alamatmhs;

    @Expose
    @SerializedName("jekel")
    private String jekelmhs;

    @Expose
    @SerializedName("jurusan")
    private String jurusanmhs;

    @Expose
    @SerializedName("fakultas")
    private String fakultasmhs;

    @Expose
    @SerializedName("periode")
    private String periodemhs;


    public String getNimmhs() {
        return nimmhs;
    }

    public void setNimmhs(String nimmhs) {
        this.nimmhs = nimmhs;
    }

    public String getNamamhs() {
        return namamhs;
    }

    public void setNamamhs(String namamhs) {
        this.namamhs = namamhs;
    }

    public String getPhonemhs() {
        return phonemhs;
    }

    public void setPhonemhs(String phonemhs) {
        this.phonemhs = phonemhs;
    }

    public String getEmailmhs() {
        return emailmhs;
    }

    public void setEmailmhs(String emailmhs) {
        this.emailmhs = emailmhs;
    }

    public String getAlamatmhs() {
        return alamatmhs;
    }

    public void setAlamatmhs(String alamatmhs) {
        this.alamatmhs = alamatmhs;
    }

    public String getJekelmhs() {
        return jekelmhs;
    }

    public void setJekelmhs(String jekelmhs) {
        this.jekelmhs = jekelmhs;
    }

    public String getJurusanmhs() {
        return jurusanmhs;
    }

    public void setJurusanmhs(String jurusanmhs) {
        this.jurusanmhs = jurusanmhs;
    }

    public String getFakultasmhs() {
        return fakultasmhs;
    }

    public void setFakultasmhs(String fakultasmhs) {
        this.fakultasmhs = fakultasmhs;
    }

    public String getPeriodemhs() {
        return periodemhs;
    }

    public void setPeriodemhs(String periodemhs) {
        this.periodemhs = periodemhs;
    }
}
