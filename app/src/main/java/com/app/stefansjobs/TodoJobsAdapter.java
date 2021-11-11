package com.app.stefansjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoJobsAdapter extends RecyclerView.Adapter<TodoJobsAdapter.MyHolder> {
    Context context;
    List<TodoJobs> todoJobsList;

    TodoJobsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_job_info, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TodoJobs todoJob = todoJobsList.get(position);

        holder.tvId.setText(String.valueOf(todoJob.getId()));
        holder.tvName.setText(todoJob.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return todoJobsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvId, tvName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setData(List<TodoJobs> todoJobsList) {
        this.todoJobsList = todoJobsList;
    }
}
