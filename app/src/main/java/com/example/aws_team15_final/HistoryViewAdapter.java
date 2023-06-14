package com.example.aws_team15_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<AdminHistoryModel> adminHistoryModels;

    public HistoryViewAdapter(Context context, ArrayList<AdminHistoryModel> adminHistoryModels){
        this.context = context;
        this.adminHistoryModels = adminHistoryModels;
    }

    @NonNull
    @Override
    public HistoryViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_recycler_row,parent,false);
        return new HistoryViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewAdapter.MyViewHolder holder, int position) {

        holder.tvtransaction.setText(adminHistoryModels.get(position).getTransaction());
        holder.tvtimestamp.setText(adminHistoryModels.get(position).getTimeStamp().toString());
        holder.tvname.setText(adminHistoryModels.get(position).getUsername());
        holder.tvitem.setText(adminHistoryModels.get(position).getItem());
        holder.tvcnt.setText(adminHistoryModels.get(position).getCnt());

    }

    @Override
    public int getItemCount() {
        return adminHistoryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvtransaction, tvname, tvitem, tvtimestamp, tvcnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtransaction = itemView.findViewById(R.id.transtv);
            tvtimestamp = itemView.findViewById(R.id.timetv);
            tvname = itemView.findViewById(R.id.usertv);
            tvitem = itemView.findViewById(R.id.itemtv);
            tvcnt = itemView.findViewById(R.id.cnttv);
        }
    }
}
