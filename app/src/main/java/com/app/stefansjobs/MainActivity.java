package com.app.stefansjobs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoJobsAdapter.Listener{
    List<TodoJobs> todoJobsList;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    Cursor data;
    private TodoJobsAdapter adapterToDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPref.init(this);
        setTitle(SharedPref.read(SharedPref.KEY_NAME,"Stefan Jobs"));
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        todoJobsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        adapterToDos = new TodoJobsAdapter(this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterToDos);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityAddToDo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_edit){

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_dialog, null);
            dialogBuilder.setView(dialogView);

            EditText editText = (EditText) dialogView.findViewById(R.id.et_name);
            editText.setText(getTitle());


            AlertDialog alertDialog = dialogBuilder.create();

            dialogView.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().trim().isEmpty()){
                        editText.setError("Please enter name");
                        editText.requestFocus();
                        return;
                    }
                    SharedPref.write(SharedPref.KEY_NAME,editText.getText().toString().trim());
                    setTitle(editText.getText().toString().trim());
                    alertDialog.dismiss();
                }
            });

            dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterToDos.setData(databaseHelper.getToDoList());
    }

    @Override
    public void onLongClick(TodoJobs todoJobs) {
        databaseHelper.delete(todoJobs.getId());
        Toast.makeText(this,"Deleted todo successfully",Toast.LENGTH_LONG).show();
        adapterToDos.setData(databaseHelper.getToDoList());

    }

}