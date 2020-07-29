package com.example.tasklist;

import android.content.Context;
import android.content.Intent;
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



        final  String getTaskTitle = myTask.get(position).getTaskTitle();
        final  String getTaskDesc = myTask.get(position).getTaskDesc();
        final  String getTaskDate = myTask.get(position).getTaskDate();
        final  String getTaskKey = myTask.get(position).getTaskKey();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa = new Intent(context, EditTask.class);
                aa.putExtra("taskTitle",getTaskTitle);
                aa.putExtra("taskDesc",getTaskDesc);
                aa.putExtra("taskDate",getTaskDate);
                aa.putExtra("taskKey",getTaskKey);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTask.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle, taskDesc,taskDate, taskKey;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDesc = (TextView) itemView.findViewById(R.id.taskDesc);
            taskDate = (TextView) itemView.findViewById(R.id.taskDate);
        }
    }


}
