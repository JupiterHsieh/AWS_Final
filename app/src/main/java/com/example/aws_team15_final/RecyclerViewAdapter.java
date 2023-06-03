package com.example.aws_team15_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.reserveViewHolder> {

    ArrayList<Integer> test_arr;

    public RecyclerViewAdapter(ArrayList<Integer> test_arr) {
        this.test_arr = test_arr;
    }

    @NonNull
    @Override
    public reserveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avaliable_items,parent,false);
        return new reserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reserveViewHolder holder, int position) {
        holder.imageView.setImageResource(test_arr.get(position));
        holder.items_textView.setText("ttt");
        holder.quantity_textView.setText("12");
    }

    @Override
    public int getItemCount() {
        return test_arr.size();
    }
    public class reserveViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView items_textView;
        TextView quantity_textView;
        Button reserve_btn;
        public reserveViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.items_imageView);
            items_textView = itemView.findViewById(R.id.items_textView);
            quantity_textView = itemView.findViewById(R.id.quantity_textView);
            reserve_btn = itemView.findViewById(R.id.reserve_button);
        }
    }
}
