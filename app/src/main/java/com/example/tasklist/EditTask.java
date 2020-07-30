package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditTask extends AppCompatActivity {
    EditText taskTitle,taskDesc,taskDate;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference databaseReference;

    AwesomeValidation awesomeValidation;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        /*
        Initialization Validation Style
         */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

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

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        taskDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskList").
                        child("Task"+taskKey);
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
                if (awesomeValidation.validate()){
                databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskList").
                        child("Task"+taskKey);
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
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please Check field(s) befor submit.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}