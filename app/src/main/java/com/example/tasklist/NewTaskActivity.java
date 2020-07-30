package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {
    TextView titlePage, addTitle, addDescription,addDate;
    EditText titleTask, taskDescription, taskTargetDate;
    Button   btnSaveTask, btnCancelTask;
    DatabaseReference databaseReference;

    AwesomeValidation awesomeValidation;
    DatePickerDialog.OnDateSetListener setListener;

    Integer taskNum = new Random().nextInt();
    String taskKey = Integer.toString(taskNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        /*
        Initialization Validation Style
         */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        titlePage = findViewById(R.id.titlepage);

        addTitle = findViewById(R.id.addTitle);
        addDescription = findViewById(R.id.addDescription);
        addDate = findViewById(R.id.addDate);

        titleTask =findViewById(R.id.titleTask);
        taskDescription = findViewById(R.id.taskDescription);
        taskTargetDate = findViewById(R.id.taskTargetDate);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancelTask = findViewById(R.id.btnCancelTask);

        //Add Validation For Title
        awesomeValidation.addValidation(this,R.id.titleTask,
                RegexTemplate.NOT_EMPTY,R.string.invalid_title);

        awesomeValidation.addValidation(this,R.id.taskDescription,
                RegexTemplate.NOT_EMPTY,R.string.invalid_description);

        awesomeValidation.addValidation(this, R.id.taskTargetDate,
                "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|" +
                        "1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:" +
                        "(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]" +
                        "|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])" +
                        "|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.invalid_date);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        taskTargetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        taskTargetDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

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
                //Check Validation
                if (awesomeValidation.validate()){
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
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please Check field(s) befor submit.",Toast.LENGTH_SHORT).show();
                }
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