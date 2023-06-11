package com.example.aws_team15_final;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    Button plus_btn;
    Button reserve_btn;
    Button minus_btn;

    ViewHolder(View itemView) {
        super(itemView);
        plus_btn = itemView.findViewById(R.id.cnt_plus);
        reserve_btn = itemView.findViewById(R.id.reserve_button);
        minus_btn = itemView.findViewById(R.id.cnt_minus);

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Log.i("button", "btn + " + String.valueOf(position));
            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Log.i("button", "btn + " + String.valueOf(position));
            }
        });
    }
}
