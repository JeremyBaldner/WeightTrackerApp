package com.example.projecttwo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History extends Graph{

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    static ArrayList<String> weight_id, weight_date, weight;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        recyclerView = findViewById(R.id.recyclerView);

        // get data from db
        myDB = new MyDatabaseHelper(History.this);
        weight_id = new ArrayList<>();
        weight_date = new ArrayList<>();
        weight = new ArrayList<>();
        storeData();

        // dynamic view of historical weights -- utilizes recycler view
        customAdapter = new CustomAdapter(History.this, this, weight_id, weight_date, weight);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
    }

    // recreate view after new results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }

    // return to graph view
    public void BackToGraph(View v){
        finish();
        Intent intent = new Intent(History.this, Graph.class);
        startActivity(intent);
    }

    // get data from db
    void storeData() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()) {
                weight_id.add(cursor.getString(0));
                weight_date.add(cursor.getString(1));
                weight.add(cursor.getString(2));
            }
        }
    }

    // prevents app from going back to UpdateActivity.java and bringing up old data
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(History.this, Graph.class);
        startActivity(intent);
    }
}