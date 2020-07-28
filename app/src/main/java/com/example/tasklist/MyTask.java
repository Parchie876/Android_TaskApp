package com.example.tasklist;

public class MyTask {

    String taskTitle;
    String taskDesc;
    String taskDate;

    public MyTask() {
    }

    public MyTask(String taskTitle, String taskDesc, String taskDate) {
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.taskDate = taskDate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
}
