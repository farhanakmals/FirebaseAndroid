package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;

public class Home extends AppCompatActivity {

    Button mahasiswa, dosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mahasiswa = findViewById(R.id.btn_mhs);
        dosen = findViewById(R.id.btn_dos);

        mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mhs = new Intent(Home.this, MainActivity.class);
                startActivity(mhs);
            }
        });

        dosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dos = new Intent(Home.this, DataDosen.class);
                startActivity(dos);
            }
        });
    }
}