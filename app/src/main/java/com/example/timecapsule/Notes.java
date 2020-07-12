package com.example.timecapsule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Notes extends AppCompatActivity {
    EditText edittext2;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        edittext2=(EditText)findViewById(R.id.textId3);
        button2=(Button) findViewById(R.id.Save_Notes);
        SharedPreferences sp=getSharedPreferences("mySp",MODE_PRIVATE);
        final String foldername = sp.getString("folder", "");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a=edittext2.getText().toString();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                final DatabaseReference myRef=database.getReference().child(foldername);
                String uuid = myRef.push().getKey();
                System.out.println(uuid);
                myRef.push().setValue(a);

                Intent intent1 = new Intent(Notes.this, Main2Activity.class);
                intent1.putExtra("text", a);
                startActivity(intent1);
                finish();

            }

        });
    }
}
