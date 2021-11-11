package com.app.stefansjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddToDo extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        editText = findViewById(R.id.et_to_do);

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().isEmpty()){
                    editText.setError("Enter todo please");
                    editText.requestFocus();
                    return;
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(ActivityAddToDo.this);
                databaseHelper.insertTodo(editText.getText().toString().trim());
                Toast.makeText(ActivityAddToDo.this,"ToDo added successfully",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}