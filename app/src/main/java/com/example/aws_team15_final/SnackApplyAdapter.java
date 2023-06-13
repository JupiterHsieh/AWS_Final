package com.example.aws_team15_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SnackApplyAdapter extends RecyclerView.Adapter<SnackApplyAdapter.MyViewHolder>{

    Context context;
    ArrayList<SnackApplyModel> snackApplyModels;

    public SnackApplyAdapter(Context context, ArrayList<SnackApplyModel> snackApplyModels){
        this.context=context;
        this.snackApplyModels = snackApplyModels;

    }

    @NonNull
    @Override
    public SnackApplyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rows_admin,parent,false);
        return new SnackApplyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackApplyAdapter.MyViewHolder holder, int position) {
        holder.tvname.setText(snackApplyModels.get(position).getUsername());
        holder.tvsnackname.setText(snackApplyModels.get(position).getSnacksname());
        holder.tvquantity.setText(snackApplyModels.get(position).getSnackquantity());
        holder.imageView.setImageResource(snackApplyModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return snackApplyModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvname, tvsnackname, tvquantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView5);
            tvname = itemView.findViewById(R.id.textView17);
            tvsnackname= itemView.findViewById(R.id.SnackName);
            tvquantity = itemView.findViewById(R.id.textView6);
        }
    }
}
