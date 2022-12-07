package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.DoubleSummaryStatistics;

public class DataDosen extends AppCompatActivity {

    EditText DosNameEditText, DosAddressEditText, DosUpdateNameEditText, DosUpdateAddressEditText;
    Button btnInsert, btnUpdate, btnRead;

    Dosen mDosen;
    DatabaseReference DosDatabaseReference;
    String keyDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_dosen);

        //EditText
        DosNameEditText = findViewById(R.id.nameDos_edittext);
        DosAddressEditText = findViewById(R.id.addressDos_edittext);
        DosUpdateNameEditText = findViewById(R.id.update_nameDos_edittext);
        DosUpdateAddressEditText = findViewById(R.id.update_addressDos_edittext);

        //Button
        btnRead =  findViewById(R.id.read_btnDos);
        btnInsert = findViewById(R.id.btninsertDos);
        btnUpdate = findViewById(R.id.updateDos_btn);

        //Firebase DB
        DosDatabaseReference = FirebaseDatabase.getInstance().getReference(Dosen.class.getSimpleName());

        //setOnClick
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readDataDosen();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertDataDosen();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateDataDosen();
            }
        });
    }

    public void readDataDosen(){
        mDosen = new Dosen();
        DosDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot currentData : snapshot.getChildren()){
                        keyDos = currentData.getKey();
                        mDosen.setName(currentData.child("name").getValue().toString());
                        mDosen.setAddress(currentData.child("address").getValue().toString());
                    }
                }

                DosUpdateNameEditText.setText(mDosen.getName());
                DosUpdateAddressEditText.setText(mDosen.getAddress());
                Toast.makeText(DataDosen.this, "Data has been shown!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insertDataDosen(){
        Dosen insertDataDosen = new Dosen();
        String name = DosNameEditText.getText().toString();
        String address = DosAddressEditText.getText().toString();

        if (name != "" && address != ""){
            insertDataDosen.setName(name);
            insertDataDosen.setName(address);

            DosDatabaseReference.push().setValue(insertDataDosen);
            Toast.makeText(this, "Successfilly insert data!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDataDosen(){
        Dosen updateDataDosen = new Dosen();
        updateDataDosen.setName(DosUpdateNameEditText.getText().toString());
        updateDataDosen.setAddress(DosUpdateAddressEditText.getText().toString());

        DosDatabaseReference.child(keyDos).setValue(updateDataDosen);
    }
}