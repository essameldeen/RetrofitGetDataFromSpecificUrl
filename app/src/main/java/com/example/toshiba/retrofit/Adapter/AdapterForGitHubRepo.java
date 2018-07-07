package com.example.toshiba.retrofit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.toshiba.retrofit.R;
import com.example.toshiba.retrofit.service.gitHubRepo;

import java.util.List;

public class AdapterForGitHubRepo extends RecyclerView.Adapter<AdapterForGitHubRepo.holder> {


    private Context context;
    private List<gitHubRepo> values;

    public AdapterForGitHubRepo(Context context, List<gitHubRepo> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public AdapterForGitHubRepo.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent,false);
        AdapterForGitHubRepo.holder holder = new AdapterForGitHubRepo.holder(row);
        return  holder;
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        holder.name.setText(values.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class holder  extends RecyclerView.ViewHolder {

        TextView name;
        public holder(View itemView) {
            super(itemView);
          name=(TextView)itemView.findViewById(R.id.name);
        }

    }


}
