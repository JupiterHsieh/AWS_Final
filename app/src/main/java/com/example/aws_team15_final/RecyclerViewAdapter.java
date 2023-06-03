package com.example.aws_team15_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.reserveViewHolder> {

    ArrayList<Integer> test_arr;
    ArrayList<String> test_items_arr;
    ArrayList<Integer> test_quantity_arr;
    ButtonClickListener buttonClickListener;

    public RecyclerViewAdapter(ArrayList<Integer> test_arr,ArrayList<String> test_items_arr,ArrayList<Integer> test_quantity_arr,ButtonClickListener buttonClickListener) {
        this.test_arr = test_arr;
        this.test_items_arr = test_items_arr;
        this.test_quantity_arr = test_quantity_arr;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public reserveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avaliable_items,parent,false);
        return new reserveViewHolder(view,buttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull reserveViewHolder holder, int position) {
        holder.imageView.setImageResource(test_arr.get(position));
        holder.items_textView.setText(test_items_arr.get(position));
        holder.quantity_textView.setText(test_quantity_arr.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return test_arr.size();
    }
    public static class reserveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView imageView;
        TextView items_textView;
        TextView quantity_textView;
        Button reserve_btn;
        ButtonClickListener buttonClickListener;
        public reserveViewHolder(@NonNull View itemView,ButtonClickListener buttonClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.items_imageView);
            items_textView = itemView.findViewById(R.id.items_textView);
            quantity_textView = itemView.findViewById(R.id.quantity_textView);
            reserve_btn = itemView.findViewById(R.id.reserve_button);
            this.buttonClickListener = buttonClickListener;
            reserve_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            buttonClickListener.onButtonClick(getAbsoluteAdapterPosition());
        }
    }
    public interface ButtonClickListener{
        void onButtonClick(int position);
    }
}
