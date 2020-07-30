package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew;
    DatePickerDialog.OnDateSetListener setListener;

    DatabaseReference databaseReference;
    RecyclerView ourTask;
    ArrayList<MyTask> taskList;
    TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);

        /*
        import @fonts */
        Typeface mLight = Typeface.createFromAsset(getAssets(),"fonts/montserratLight.ttf");
        Typeface mMedium = Typeface.createFromAsset(getAssets(),"fonts/montserratMedium.ttf");

        /*
        * Customize Font*/
        titlepage.setTypeface(mMedium);
        subtitlepage.setTypeface(mLight);
        endpage.setTypeface(mLight);
        btnAddNew.setTypeface(mLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(a);
            }
        });

        /*
        Working with data
         */
        ourTask = findViewById(R.id.ourTask);
        ourTask.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<MyTask>();

        /*
        Get data from firebase
         */
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code to retrieve data and replace lay out
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyTask p = dataSnapshot1.getValue(MyTask.class);
                    taskList.add(p);
                }
                taskAdapter = new TaskAdapter(MainActivity.this,taskList);
                ourTask.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //set code to show an error
                Toast.makeText(getApplicationContext(),"No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}