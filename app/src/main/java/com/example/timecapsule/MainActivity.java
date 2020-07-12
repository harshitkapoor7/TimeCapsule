package com.example.timecapsule;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    TextView tv;
    FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    NavigationView navigation;
    List<String> display;
    Adapter myAdapter;
    AlertDialog alert;

    private TextView textViewUserEmail;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseUser user = firebaseAuth.getCurrentUser();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
        tv = (TextView) findViewById(R.id.editText);
        fab = (FloatingActionButton) findViewById(R.id.button_xml);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigation=(NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String folder = sharedPreferences.getString("email_Id", "");


         int j=folder.length()-4;
         System.out.println(folder);
         final String username=folder.substring(0,j);
         final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference().child(username);


           myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> b = new ArrayList<String>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    b.add(snap.getValue().toString());
                }
                final Adapter adapter = new Adapter(MainActivity.this,b);
                lv.setAdapter(adapter);
                adapter.addAll(b);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You have selected " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, Main2Activity.class);
                SharedPreferences sp=getSharedPreferences("mySp",MODE_PRIVATE);
                SharedPreferences.Editor spEditor=sp.edit();
                spEditor.putString("folder",parent.getItemAtPosition(position).toString());
                spEditor.commit();
                startActivity(intent2);

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.dialog_box, null,true);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        String a=userInput.getText().toString();
                                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                                        myRef.child(myRef.push().getKey()).setValue(a);

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            //AlertDialog alertDialog = alertDialogBuilder.create();
            //alertDialog.show();

        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        switch (id) {

            case R.id.folder1:

                break;
            case R.id.folder2:

                break;
            case R.id.folder3:
                FirebaseAuth fba;
                fba=FirebaseAuth.getInstance();
                FirebaseUser user =fba.getCurrentUser();
                Intent g = new Intent(this, LoginActivity.class);
                fba.signOut();
                finish();
                startActivity(g);
                break;


        }
        return false;
    }
}