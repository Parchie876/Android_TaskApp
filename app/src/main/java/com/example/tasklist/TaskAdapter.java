package com.example.tasklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.ArrayLinkedVariables;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTask> myTask;

    public TaskAdapter(Context c, ArrayList<MyTask> p){
        context = c;
        myTask = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.taskTitle.setText(myTask.get(position).getTaskTitle());
        myViewHolder.taskDesc.setText(myTask.get(position).getTaskDesc());
        myViewHolder.taskDate.setText(myTask.get(position).getTaskDate());
    }

    @Override
    public int getItemCount() {
        return myTask.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle, taskDesc,taskDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDesc = (TextView) itemView.findViewById(R.id.taskDesc);
            taskDate = (TextView) itemView.findViewById(R.id.taskDate);
        }
    }


}
