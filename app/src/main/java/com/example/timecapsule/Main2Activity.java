package com.example.timecapsule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView lv1;
    TextView tv1;
    FloatingActionButton fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv1 = (ListView) findViewById(R.id.list1);
        tv1=(TextView) findViewById(R.id.textView2);
        fab1=(FloatingActionButton) findViewById(R.id.button_xml2);


        SharedPreferences sp=getSharedPreferences("mySp",MODE_PRIVATE);
        String foldername = sp.getString("folder", "");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference().child(foldername);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> b = new ArrayList<String>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    b.add(snap.getValue().toString());
                }
                Adapter adapter = new Adapter(Main2Activity.this,b);
                lv1.setAdapter(adapter);
                adapter.addAll(b);
                adapter.notifyDataSetChanged();
            }
            //

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Notes.class);
                startActivity(intent);
                finish();

            }


        });

    }
}
