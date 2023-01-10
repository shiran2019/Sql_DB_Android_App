package com.im.sqlitedbtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper =new DatabaseHelper(this);
        viewAllIds();
    }

    public void viewAllIds(){
        Cursor result= databaseHelper.getIds();
        List<String> list = new ArrayList<String>();
        list.add("Select ID");
        if(result.getCount()>0){
            while (result.moveToNext()){
                list.add(result.getString(0));
            }
        }
        Spinner spinnerId = (Spinner) findViewById(R.id.drp);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerId.setAdapter(dataAdapter);
    }

    public void insertData(View view){
        EditText fName=(EditText)findViewById(R.id.fn1);
        EditText surname=(EditText)findViewById(R.id.sn1);
        EditText marks=(EditText)findViewById(R.id.mrk1);
        boolean executed= databaseHelper.insertToDb(fName.getText().toString(),surname.getText().toString(),Integer.parseInt(marks.getText().toString()));
        if(executed){
            Toast.makeText(this,"Data Inserted",Toast.LENGTH_LONG).show();
            viewAllIds();
            ((EditText) findViewById(R.id.fn1)).setText("");
            ((EditText) findViewById(R.id.sn1)).setText("");
            ((EditText) findViewById(R.id.mrk1)).setText("");


        }
        else{
            Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show();
        }
    }

    public void viewAllData(View view){
        Cursor result= databaseHelper.getAllData();
        StringBuilder message=new StringBuilder();
        if(result.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
        }
        else{
            while (result.moveToNext()){
                message.append("ID : "+result.getString(0)+"\n");
                message.append("First Name : "+result.getString(1)+"\n");
                message.append("Surname : "+result.getString(2)+"\n");
                message.append("Marks : "+result.getString(3)+"\n\n");
            }
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("Student Table");
            dialog.setMessage(message);
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public void viewDataFromId(View view){
        Spinner spinnerId=(Spinner)findViewById(R.id.drp);
        String id=spinnerId.getSelectedItem().toString();
        Cursor result= databaseHelper.getDataFromID(id);
        if(result.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
        }
        else{
            EditText fName=(EditText)findViewById(R.id.fn2);
            EditText surname=(EditText)findViewById(R.id.sn2);
            EditText marks=(EditText)findViewById(R.id.mrk2);
            while (result.moveToNext()){
                fName.setText(result.getString(1));
                surname.setText(result.getString(2));
                marks.setText(""+Integer.parseInt(result.getString(3)));
            }
        }
    }

    public void updateData(View view){
        Spinner spinnerId=(Spinner)findViewById(R.id.drp);
        String id=spinnerId.getSelectedItem().toString();
        if(!id.equals("Select ID")){
            EditText fName=(EditText)findViewById(R.id.fn2);
            EditText surname=(EditText)findViewById(R.id.sn2);
            EditText marks=(EditText)findViewById(R.id.mrk2);
            boolean executed= databaseHelper.updateData(id,fName.getText().toString(),surname.getText().toString(),Integer.parseInt(marks.getText().toString()));
            if(executed){
                Toast.makeText(this,"Data Updated",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteData(View view){
        Spinner spinnerId=(Spinner)findViewById(R.id.drp);
        String id=spinnerId.getSelectedItem().toString();
        boolean executed= databaseHelper.deleteData(id);
        if(executed){
            Toast.makeText(this,"Data Deleted",Toast.LENGTH_LONG).show();
            viewAllIds();
            EditText fName=(EditText)findViewById(R.id.fn2);
            EditText surname=(EditText)findViewById(R.id.sn2);
            EditText marks=(EditText)findViewById(R.id.mrk2);
            fName.setText("");
            surname.setText("");
            marks.setText("");
        }
        else{
            Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show();
        }
    }
}

