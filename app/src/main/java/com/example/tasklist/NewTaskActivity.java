package com.example.tasklist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewTaskActivity extends AppCompatActivity {
    TextView titlePage, addTitle, addDescription,addDate;
    EditText titleTask, taskDescription, taskTargetDate;
    Button   btnSaveTask, btnCancelTask;
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