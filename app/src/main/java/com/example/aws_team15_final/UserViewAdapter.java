package com.example.aws_team15_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<AdminUserModel> adminUserModels;

    public UserViewAdapter(Context context, ArrayList<AdminUserModel> adminUserModels){
        this.context = context;
        this.adminUserModels = adminUserModels;
    }

    @NonNull
    @Override
    public UserViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rows_userlist,parent,false);
        return new UserViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewAdapter.MyViewHolder holder, int position) {
        System.out.println(adminUserModels.get(position).getUsername());

        holder.tvuser.setText(adminUserModels.get(position).getUsername());
        holder.tvcoin.setText(adminUserModels.get(position).getCoin());


    }

    @Override
    public int getItemCount() {
        return adminUserModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvuser;
        TextView tvcoin;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvuser = itemView.findViewById(R.id.textView17);
            tvcoin = itemView.findViewById(R.id.Coin);

        }
    }
}
