package com.example.projecttwo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    TextView weight_input;
    EditText newWeight;
    Button update_button,delete_button;
    String id, date, weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        weight_input = findViewById(R.id.prvWeightView);
        update_button = findViewById(R.id.updateBtn);
        newWeight = findViewById(R.id.upd_wt_txt);
        delete_button = findViewById(R.id.deleteBtn);

        // get data for update screen
        getAndSetIntentData();

        // update weight in db and return to history screen
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                weight = newWeight.getText().toString();
                if (!weight.isEmpty()) {
                    myDB.updateData(id, weight);
                    finish();
                    Intent intent = new Intent(UpdateActivity.this, History.class);
                    startActivity(intent);
                }
            }
        });

        // delete weight from db and return to history screen
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    // gets data from db
    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("date") && getIntent().hasExtra("weight")) {
            // get
            id = getIntent().getStringExtra("id");
            date = getIntent().getStringExtra("date");
            weight = getIntent().getStringExtra("weight");
            //set
            weight_input.setText(weight_input.getText().toString() + weight);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    // pop-up to confirm delete
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + weight + " from " + date);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteWeight(id);
                finish();
                Intent intent = new Intent(UpdateActivity.this, History.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void BackToHistory(View v) {
        finish();
    }

    // prevents user from going back to old data
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(UpdateActivity.this, History.class);
        startActivity(intent);
    }
}
