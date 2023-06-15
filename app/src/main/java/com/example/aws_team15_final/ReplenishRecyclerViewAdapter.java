package com.example.aws_team15_final;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplenishRecyclerViewAdapter extends RecyclerView.Adapter<ReplenishRecyclerViewAdapter.replenishViewHolder>{
    private ArrayList<Integer> test_img_arr;
    private ArrayList<String> test_replenish_items_arr;
    private ArrayList<Integer> test_replenish_quantity_arr;
    private ArrayList<Integer> test_replenish_count_arr;
    private ReplenishRecyclerViewAdapter.ReplenishButtonListener replenish_buttonListener;

    public ReplenishRecyclerViewAdapter(ArrayList<Integer> test_img_arr,ArrayList<String> test_replenish_items_arr,ArrayList<Integer> test_replenish_quantity_arr,ArrayList<Integer> test_replenish_count_arr,ReplenishButtonListener replenish_buttonListener){
        this.test_img_arr = test_img_arr;
        this.test_replenish_items_arr = test_replenish_items_arr;
        this.test_replenish_quantity_arr = test_replenish_quantity_arr;
        this.test_replenish_count_arr = test_replenish_count_arr;
        this.replenish_buttonListener = replenish_buttonListener;
    }
    @NonNull
    @Override
    public ReplenishRecyclerViewAdapter.replenishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replenish_items,parent,false);
        return new replenishViewHolder(view,replenish_buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull replenishViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.replenish_imageView.setImageResource(test_img_arr.get(position));
        holder.replenish_item_textView.setText(test_replenish_items_arr.get(position));
        holder.replenish_quantity_textView.setText(test_replenish_quantity_arr.get(position).toString());
        holder.replenish_count.setText(test_replenish_count_arr.get(position).toString());
        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replenish_buttonListener != null){
                    replenish_buttonListener.minusOnClick(position);
                    holder.replenish_count.setText(test_replenish_count_arr.get(position).toString());
                    if(test_replenish_count_arr.get(position) == 0) {
                        holder.minus_btn.setEnabled(false);
                    }
                }
            }
        });
        holder.plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replenish_buttonListener != null){
                    replenish_buttonListener.plusOnclick(position);
                    holder.replenish_count.setText(test_replenish_count_arr.get(position).toString());
                    if(test_replenish_count_arr.get(position) > 0){holder.minus_btn.setEnabled(true);}
                }
            }
        });
        holder.replenish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replenish_buttonListener != null){
                    replenish_buttonListener.replenishOnClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return test_replenish_items_arr.size();
    }
    public static class replenishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView replenish_imageView;
        TextView replenish_item_textView;
        TextView replenish_quantity_textView;
        Button minus_btn;
        TextView replenish_count;
        Button plus_btn;
        Button replenish_btn;
        public replenishViewHolder(@NonNull View itemView, ReplenishRecyclerViewAdapter.ReplenishButtonListener buttonListener) {
            super(itemView);
            replenish_imageView = itemView.findViewById(R.id.replenish_items_imageView);
            replenish_item_textView = itemView.findViewById(R.id.replenish_items_textView);
            replenish_quantity_textView = itemView.findViewById(R.id.replenish_quantity_textView);
            minus_btn = itemView.findViewById(R.id.replenish_quant_minus);
            replenish_count = itemView.findViewById(R.id.replenish_count);
            plus_btn = itemView.findViewById(R.id.replenish_quant_plus);
            replenish_btn = itemView.findViewById(R.id.replenish_button);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface ReplenishButtonListener{
        void minusOnClick(int position);
        void plusOnclick(int position);
        void replenishOnClick(int position);
    }
}
