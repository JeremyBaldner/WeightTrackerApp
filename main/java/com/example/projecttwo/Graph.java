package com.example.projecttwo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.time.LocalDate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.List;

public class Graph extends AppCompatActivity {
    GraphView graphView;
    Button addWeight, SevenDay, ThirtyDay, NinetyDay;
    EditText weight;
    private static LocalDate currentDate = LocalDate.now();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    // Dummy Data
    public static List<Double> listY;
    public static List<String> listDates;
    LineGraphSeries<DataPoint> weights, goal;
    private double goalY = 160;
    MyDatabaseHelper myDB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // check if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // if permission is not granted, check for denial
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Provide message to the user explaining this need for this permission.
                new AlertDialog.Builder(this).setTitle("Permission needed")
                        .setMessage("This allows the app to send a congratulations message when you reach your goal.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            // If the user clicks okay, they will receive a permission request again.
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Graph.this, new String[]
                                        {Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                            }
                            // if they click cancel, the message will be dismissed
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else {
                // a pop-up will appear asking for permission
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        // initialize graph filter buttons
        SevenDay = (Button) findViewById(R.id.sevenDBtn);
        ThirtyDay = (Button) findViewById(R.id.thirtyDBtn);
        NinetyDay = (Button) findViewById(R.id.ninetyDBtn);
        SevenDay.setEnabled(false);

        // initialize weight variables
        addWeight = (Button) findViewById(R.id.addWeightButton);
        weight = (EditText) findViewById(R.id.editDailyWeight);

        // initializing graph view.
        graphView = findViewById(R.id.GraphView);

        // initialize db
        myDB1 = new MyDatabaseHelper(Graph.this);
        listY = new ArrayList<>();
        listDates = new ArrayList<>();
        getData();

        // if data is available create line graph
        if(listY.size() > 0) {
            addPoints();
            resetGraph();
        }
    }

    // resets the weights line graph
    public void resetGraph() {
        graphView.removeSeries(weights);
        graphView.removeSeries(goal);
        if(listY.size() > 0) {
            // title for our graph.
            graphView.setTitle("Weight Tracker");
            graphView.setTitleTextSize(100);

            // Color for graph.
            graphView.setTitleColor(R.color.purple_500);
            goal.setDrawBackground(true);
            goal.setBackgroundColor(Color.argb(20, 0, 250, 0));
            weights.setColor(R.color.purple_500);
            goal.setColor(Color.GREEN);
            weights.setDrawDataPoints(true);
            weights.setDataPointsRadius(10);
            weights.setThickness(8);
            goal.setThickness(8);

            // adding data to our graph.
            graphView.addSeries(weights);
            graphView.addSeries(goal);
        }
    }

    // add weight to the graph & db
    public void AddWeight(View v) {
        // check for an empty string -- app will crash if empty string is passed
        if (!weight.getText().toString().isEmpty()) {

            MyDatabaseHelper myDB = new MyDatabaseHelper(Graph.this);
            myDB.addWeight(currentDate.toString(), Integer.valueOf(weight.getText().toString()));

            double y = Double.parseDouble(weight.getText().toString());
            double x = listY.size()+1;
            weight.setText(null);

            listY.add(y);
            listDates.add(currentDate.toString());
            addPoints();
            resetGraph();
        }
    }

    // filters graph to a seven day view
    public void sevenD(View v) {
        // set manual X bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(7);
        resetGraph();
        SevenDay.setEnabled(false);
        ThirtyDay.setEnabled(true);
        NinetyDay.setEnabled(true);
    }

    // filters graph to a 30 day view
    public void thirtyD(View v) {
        // set manual X bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(30);
        resetGraph();
        SevenDay.setEnabled(true);
        ThirtyDay.setEnabled(false);
        NinetyDay.setEnabled(true);
    }

    // filters graph to a 90 day view
    public void ninetyD(View v) {
        // set manual X bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(90);
        resetGraph();
        SevenDay.setEnabled(true);
        ThirtyDay.setEnabled(true);
        NinetyDay.setEnabled(false);
    }

    // moves to history activity when chevron is pressed
    public void HistoryView(View v) {
        Intent intent = new Intent(Graph.this, History.class);
        startActivity(intent);
    }

    // adds points to the graph
    private void addPoints() {
            // create data to see graph
            double x1 = 1.0;
            weights = new LineGraphSeries<DataPoint>();
            goal = new LineGraphSeries<DataPoint>();
            for (int i = 0; i < listY.size(); i++) {
                Double y = Double.parseDouble(listY.get(i).toString());
                weights.appendData(new DataPoint(x1, y), true, 500);
                x1 += 1;
            }
            x1=0;
            for (int i = 0; i < 90; i++) {
                goal.appendData(new DataPoint(x1, goalY), true, 500);
                x1+=1;
            }
            setGraphBounds();
    }

    // dynamically changes graph view based on weights input
    public void setGraphBounds(){
        // find max and min for y
        Double max = 0.0, min = 1000.0;
        for (int i = 0; i < listY.size(); i++) {
            Double num = Double.parseDouble(listY.get(i).toString());
            if (num > max) {
                max = num;
            }
        }
        for (int i = 0; i < listY.size(); i++) {
            Double num = Double.parseDouble(listY.get(i).toString());
            if (num < min) {
                min = num;
            }
        }
        // set manual X bounds
        if (!SevenDay.isEnabled()) {
            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(1);
            graphView.getViewport().setMaxX(7);
        } else if (!ThirtyDay.isEnabled()) {
            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(1);
            graphView.getViewport().setMaxX(30);
        }else {
            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(1);
            graphView.getViewport().setMaxX(90);
        }

        // set manual Y bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(min - 10);
        graphView.getViewport().setMaxY(max + 10);
    }

    // get data from db
    void getData() {
        Cursor cursor = myDB1.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()) {
                listDates.add(cursor.getString(1));
                listY.add(Double.parseDouble(cursor.getString(2)));
            }
        }
    }
    // prevents the user from going back to History page with old data
    @Override
    public void onBackPressed() {
        //do nothing
    }
}

