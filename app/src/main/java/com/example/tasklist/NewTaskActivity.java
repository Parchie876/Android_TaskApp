package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {
    TextView titlePage, addTitle, addDescription,addDate;
    EditText titleTask, taskDescription, taskTargetDate;
    Button   btnSaveTask, btnCancelTask;
    DatabaseReference databaseReference;

    TaskAdapter taskAdapter;
    Integer taskNum = new Random().nextInt();
    String taskKey = Integer.toString(taskNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlePage = findViewById(R.id.titlepage);

        addTitle = findViewById(R.id.addTitle);
        addDescription = findViewById(R.id.addDescription);
        addDate = findViewById(R.id.addDate);

        titleTask =findViewById(R.id.titleTask);
        taskDescription = findViewById(R.id.taskDescription);
        taskTargetDate = findViewById(R.id.taskTargetDate);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancelTask = findViewById(R.id.btnCancelTask);

        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(NewTaskActivity.this,MainActivity.class);
                startActivity(a);
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // insert data to database
                databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskList").
                        child("Task"+taskNum);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("taskTitle").setValue(titleTask.getText().toString());
                        dataSnapshot.getRef().child("taskDesc").setValue(taskDescription.getText().toString());
                        dataSnapshot.getRef().child("taskDate").setValue(taskTargetDate.getText().toString());
                        dataSnapshot.getRef().child("taskKey").setValue(taskKey);

                        Intent a = new Intent( NewTaskActivity.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        /*
        import @fonts */
        Typeface mLight = Typeface.createFromAsset(getAssets(),"fonts/montserratLight.ttf");
        Typeface mMedium = Typeface.createFromAsset(getAssets(),"fonts/montserratMedium.ttf");

        /*
         * Customize Font*/
        titlePage.setTypeface(mMedium);

        addTitle.setTypeface(mLight);
        titleTask.setTypeface(mMedium);

        addDescription.setTypeface(mLight);
        taskDescription.setTypeface(mMedium);

        addDate.setTypeface(mLight);
        taskTargetDate.setTypeface(mMedium);

        btnSaveTask.setTypeface(mMedium);
        btnCancelTask.setTypeface(mLight);
    }
}