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

public class MainActivity extends AppCompatActivity {

    EditText mNameEditText, mAddAddressEditText, mUpdateNameEditText, mUpdateAddressEditText;

    DatabaseReference mDatabaseReference;
    Student mStudent;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Student.class.getSimpleName());

        mNameEditText = findViewById(R.id.name_edittext);
        mAddAddressEditText = findViewById(R.id.address_edittext);
        mUpdateAddressEditText = findViewById(R.id.update_address_edittext);
        mUpdateNameEditText = findViewById(R.id.update_name_edittext);

        findViewById(R.id.btninsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
            }
        });

        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateData();
            }
        });

        findViewById(R.id.read_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readData();
            }
        });
    }

    private void readData() {

        mStudent = new Student();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot currentData : snapshot.getChildren()){
                        key = currentData.getKey();
                        mStudent.setName(currentData.child("name").getValue().toString());
                        mStudent.setAddress(currentData.child("address").getValue().toString());
                    }
                }

                mUpdateNameEditText.setText(mStudent.getName());
                mUpdateAddressEditText.setText(mStudent.getAddress());
                Toast.makeText(MainActivity.this, "Data has been shown!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData() {
        Student updateData = new Student();
        updateData.setName(mUpdateNameEditText.getText().toString());
        updateData.setAddress(mUpdateAddressEditText.getText().toString());

        mDatabaseReference.child(key).setValue(updateData);
    }

    private void insertData() {
        Student newStudent = new Student();
        String name = mNameEditText.getText().toString();
        String address = mAddAddressEditText.getText().toString();

        if (name != "" && address != ""){
            newStudent.setName(name);
            newStudent.setAddress(address);

            mDatabaseReference.push().setValue(newStudent);
            Toast.makeText(this, "Successfilly insert data!", Toast.LENGTH_SHORT).show();
        }
    }
}