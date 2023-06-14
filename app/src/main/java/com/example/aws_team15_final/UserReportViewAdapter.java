package com.example.aws_team15_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserReportViewAdapter extends RecyclerView.Adapter<UserReportViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<UserReportModel> UserReportModels;

    public UserReportViewAdapter(Context context, ArrayList<UserReportModel> UserReportModels){
        this.context = context;
        this.UserReportModels = UserReportModels;
    }

    @NonNull
    @Override
    public UserReportViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rows_userreport,parent,false);
        return new UserReportViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReportViewAdapter.MyViewHolder holder, int position) {

        holder.tvtransaction.setText(UserReportModels.get(position).getTransaction());
        holder.tvtimestamp.setText(UserReportModels.get(position).getTimeStamp());
        holder.tvitem.setText(UserReportModels.get(position).getItem());
        holder.tvcnt.setText(UserReportModels.get(position).getCnt());

    }

    @Override
    public int getItemCount() {
        return UserReportModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvtransaction, tvitem, tvtimestamp, tvcnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtransaction = itemView.findViewById(R.id.usertranstv);
            tvtimestamp = itemView.findViewById(R.id.usertimestamp);
            tvitem = itemView.findViewById(R.id.useritemtv);
            tvcnt = itemView.findViewById(R.id.usercnttv);
        }
    }
}
