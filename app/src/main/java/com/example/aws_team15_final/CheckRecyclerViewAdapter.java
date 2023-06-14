package com.example.aws_team15_final;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewAdapter.grabCheckViewHolder> {
    ArrayList<String> test_check_items_arr;
    ArrayList<Integer> test_check_quantity_arr;
    private ButtonListener buttonListener;
    public CheckRecyclerViewAdapter(ArrayList<String> test_check_items_arr,ArrayList<Integer> test_check_quantity_arr,ButtonListener buttonListener){
        this.test_check_items_arr = test_check_items_arr;
        this.test_check_quantity_arr = test_check_quantity_arr;
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public CheckRecyclerViewAdapter.grabCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_items,parent,false);
        return new grabCheckViewHolder(view,buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull grabCheckViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.check_quantity_textView.setText(test_check_quantity_arr.get(position).toString());
        holder.check_item_textView.setText(test_check_items_arr.get(position));
        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonListener != null){
                    buttonListener.minusOnClick(position);
                    holder.check_quantity_textView.setText(test_check_quantity_arr.get(position).toString());
                    if(test_check_quantity_arr.get(position) == 0) {
                        holder.minus_btn.setEnabled(false);
                    }
                }
            }
        });
        holder.plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonListener != null){
                    buttonListener.plusOnclick(position);
                    holder.check_quantity_textView.setText(test_check_quantity_arr.get(position).toString());
                    if(test_check_quantity_arr.get(position) > 0){holder.minus_btn.setEnabled(true);}
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return test_check_items_arr.size();
    }

    public static class grabCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button minus_btn;
        TextView check_quantity_textView;
        Button plus_btn;
        TextView check_item_textView;
        public grabCheckViewHolder(@NonNull View itemView, ButtonListener buttonListener) {
            super(itemView);
            minus_btn = itemView.findViewById(R.id.minus_btn);
            check_quantity_textView = itemView.findViewById(R.id.check_quantity_textView);
            plus_btn = itemView.findViewById(R.id.plus_button);
            check_item_textView = itemView.findViewById(R.id.check_item_textView);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface ButtonListener{
        void minusOnClick(int position);
        void plusOnclick(int position);
    }
}
