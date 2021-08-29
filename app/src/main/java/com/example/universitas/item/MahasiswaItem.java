package com.example.universitas.item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universitas.R;
import com.example.universitas.activity.DetailActivity;
import com.example.universitas.activity.UpdateActivity;
import com.example.universitas.models.Mahasiswa;

import java.util.List;

public class MahasiswaItem extends RecyclerView.Adapter<MahasiswaItem.ItemRowHolder>
{

    private Context mContext;
    private List<Mahasiswa> datalist;
    private int rowLayout;

    public MahasiswaItem(Context mContext, List<Mahasiswa> datalist, int rowLayout) {
        this.mContext = mContext;
        this.datalist = datalist;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        final Mahasiswa singleItem = datalist.get(position);
        holder.phone.setText("nomor telepon: " + singleItem.getPhonemhs());
        holder.nim.setText("nim mahasiswa : "+ singleItem.getNimmhs());
        holder.nama.setText("nama mahasiswa : "+singleItem.getNamamhs());
        holder.email.setText("email mahasiswa : "+singleItem.getEmailmhs());
        holder.jender.setText("jenis kelamin : "+singleItem.getJekelmhs());
        holder.alamat.setText("alamat tempat tinggal : "+singleItem.getAlamatmhs());
        holder.periode.setText("tanggal masuk : "+singleItem.getPeriodemhs());
        holder.jurusan.setText("jurusan mahasiswa : "+singleItem.getJurusanmhs());
        holder.fakultas.setText("fakultas mahasiswa : "+singleItem.getFakultasmhs());

        holder.clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(mContext, UpdateActivity.class);
                detail.putExtra("id",singleItem.getId());
                detail.putExtra("nama",singleItem.getNamamhs());
                detail.putExtra("phone",singleItem.getPhonemhs());
                detail.putExtra("nim",singleItem.getNimmhs());
                detail.putExtra("email",singleItem.getEmailmhs());
                detail.putExtra("jekel",singleItem.getJekelmhs());
                detail.putExtra("alamat",singleItem.getAlamatmhs());
                detail.putExtra("periode",singleItem.getPeriodemhs());
                detail.putExtra("jurusan",singleItem.getJurusanmhs());
                detail.putExtra("fakultas",singleItem.getFakultasmhs());
                detail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder{

        RelativeLayout clicked;
        TextView phone,nim,nama,email,alamat,periode,jurusan,fakultas,jender;
        public ItemRowHolder(@NonNull View itemView) {
            super(itemView);

            clicked = itemView.findViewById(R.id.clickedline);
            phone = itemView.findViewById(R.id.phonemahasiswa);
            nim = itemView.findViewById(R.id.nimmahasiswa);
            nama = itemView.findViewById(R.id.namamahasiswa);
            email = itemView.findViewById(R.id.emailmahasiswa);
            alamat = itemView.findViewById(R.id.alamatmahasiswa);
            periode = itemView.findViewById(R.id.periodemahasiswa);
            jurusan = itemView.findViewById(R.id.jurusanmahasiswa);
            fakultas = itemView.findViewById(R.id.fakultasmahasiswa);
            jender = itemView.findViewById(R.id.jkmahasiswa);
        }
    }
}
