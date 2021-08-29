package com.example.universitas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.universitas.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button lihat,input,info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lihat = findViewById(R.id.lihatButton);
        input = findViewById(R.id.tambahButton);
        info = findViewById(R.id.infoButton);

        lihat.setOnClickListener(this::onClick);
        input.setOnClickListener(this::onClick);
        info.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.lihatButton:
                Intent a = new Intent(MainActivity.this,DetailActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                break;
            case  R.id.tambahButton:
                Intent b = new Intent(MainActivity.this,TambahActivity.class);
                b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(b);
                break;
            case R.id.infoButton:
                Intent c = new Intent(MainActivity.this,InformasiActivity.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(c);
                break;
        }
    }
}