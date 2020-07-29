package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTask extends AppCompatActivity {
    EditText taskTitle,taskDesc,taskDate;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskTitle = findViewById(R.id.titleTask);
        taskDesc = findViewById(R.id.taskDescription);
        taskDate = findViewById(R.id.taskTargetDate);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdateTask);
        btnDelete = findViewById(R.id.btnDeleteTask);

        /**
         * get  values from previous page
         */
        taskTitle.setText(getIntent().getStringExtra("taskTitle"));
        taskDesc.setText(getIntent().getStringExtra("taskDesc"));
        taskDate.setText(getIntent().getStringExtra("taskDate"));
        final String taskKey = getIntent().getStringExtra("taskKey");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskList").
                child("Task"+taskKey);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTask.this,MainActivity.class);
                            startActivity(a);
                        }else {
                            Toast.makeText(getApplicationContext(),"Fail To Delete Task", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // insert data to database

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("taskTitle").setValue(taskTitle.getText().toString());
                        dataSnapshot.getRef().child("taskDesc").setValue(taskDesc.getText().toString());
                        dataSnapshot.getRef().child("taskDate").setValue(taskDate.getText().toString());
                        dataSnapshot.getRef().child("taskKey").setValue(taskKey);

                        Intent a = new Intent( EditTask.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}