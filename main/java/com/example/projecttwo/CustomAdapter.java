package com.example.projecttwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList weight_ids, weight_dates, weights;

    CustomAdapter(Activity activity, Context context, ArrayList weight_ids, ArrayList weight_dates, ArrayList weights) {
        this.activity = activity;
        this.context = context;
        this.weight_ids = weight_ids;
        this.weight_dates = weight_dates;
        this.weights = weights;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    // creates cards for history screen
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.weight_id_text.setText(String.valueOf(weight_ids.get(position)));
        holder.weight_date_text.setText(String.valueOf(weight_dates.get(position)));
        holder.weight_text.setText(String.valueOf(weights.get(position)) + " lbs");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(weight_ids.get(position)));
                intent.putExtra("date", String.valueOf(weight_dates.get(position)));
                intent.putExtra("weight", String.valueOf(weights.get(position)) + " lbs");
                activity.startActivityForResult(intent, 1);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weight_ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView weight_id_text, weight_date_text, weight_text;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            weight_id_text = itemView.findViewById(R.id.weight_ID);
            weight_date_text = itemView.findViewById(R.id.weight_date);
            weight_text = itemView.findViewById(R.id.recordedWeight);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
