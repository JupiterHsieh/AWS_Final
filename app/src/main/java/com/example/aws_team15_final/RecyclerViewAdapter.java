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
    ArrayList<Integer> test_cnt_arr;
    ButtonClickListener buttonClickListener_res;

    public RecyclerViewAdapter(ArrayList<Integer> test_arr,ArrayList<String> test_items_arr,ArrayList<Integer> test_quantity_arr, ArrayList<Integer> test_cnt_arr, ButtonClickListener buttonClickListener) {
        this.test_arr = test_arr;
        this.test_items_arr = test_items_arr;
        this.test_quantity_arr = test_quantity_arr;
        this.test_cnt_arr = test_cnt_arr;
        this.buttonClickListener_res = buttonClickListener;
    }

    @NonNull
    @Override
    public reserveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avaliable_items,parent,false);
        return new reserveViewHolder(view,buttonClickListener_res);
    }

    @Override
    public void onBindViewHolder(@NonNull reserveViewHolder holder, int position) {
        holder.imageView.setImageResource(test_arr.get(position));
        holder.items_textView.setText(test_items_arr.get(position));
        holder.quantity_textView.setText(test_quantity_arr.get(position).toString());
        holder.reserve_cnt.setText(test_cnt_arr.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return test_quantity_arr.size();
    }
    public static class reserveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView imageView;
        TextView items_textView;
        TextView quantity_textView;
        TextView reserve_cnt;
        Button reserve_btn;

        Button cnt_plus;
        Button cnt_minus;
        ButtonClickListener buttonClickListener;
        public reserveViewHolder(@NonNull View itemView,ButtonClickListener buttonClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.items_imageView);
            items_textView = itemView.findViewById(R.id.items_textView);
            quantity_textView = itemView.findViewById(R.id.quantity_textView);
            reserve_cnt = itemView.findViewById(R.id.reserve_cnt);
            cnt_plus = itemView.findViewById(R.id.cnt_plus);
            cnt_plus.setOnClickListener(this);

            cnt_minus = itemView.findViewById(R.id.cnt_minus);
            cnt_minus.setOnClickListener(this);

            reserve_btn = itemView.findViewById(R.id.reserve_button);
            reserve_btn.setOnClickListener(this);

            this.buttonClickListener = buttonClickListener;
        }

        @Override
        public void onClick(View view) {
            int buttonId = view.getId();
            buttonClickListener.onButtonClick(getAbsoluteAdapterPosition(), buttonId);
        }
    }
    public interface ButtonClickListener{
//        void onButtonClick(int position);

        void onButtonClick(int position, int buttonId);
    }
}
