package com.techietest.techiedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText edtName, edtSurname, edtMarks, edtId;
    Button btnAddData, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        edtName = (EditText) findViewById(R.id.edtName);
        edtSurname = (EditText) findViewById(R.id.edtSurname);
        edtMarks = (EditText) findViewById(R.id.edtMarks);
        edtId = (EditText) findViewById(R.id.edtId);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnView = (Button) findViewById(R.id.btnView);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        ViewAll();
        AddData();
        UpdateData();
        DeleteData();


    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty() || edtSurname.getText().toString().isEmpty() || edtMarks.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all the data", Toast.LENGTH_SHORT).show();

                }
                else {

                    boolean isInserted = myDb.insertData(edtName.getText().toString(),
                            edtSurname.getText().toString(),
                            edtMarks.getText().toString());
                    if (isInserted == true) {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        edtId.setText("");
                        edtName.setText("");
                        edtSurname.setText("");
                        edtMarks.setText("");
                    }
                    else
                        Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ViewAll() {
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    //show message
                    showMessage("Error", "Nothing found");
                    return;
                }


                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n");
                    buffer.append("Surname :" + res.getString(2) + "\n");
                    buffer.append("Mark :" + res.getString(3) + "\n\n");
                }
                // Show all data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        boolean isUpdate = myDb.updateData(edtId.getText().toString(),edtName.getText().toString(),edtSurname.getText().toString(),edtMarks.getText().toString());

                        if(isUpdate == true) {
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                            edtId.setText("");
                            edtName.setText("");
                            edtSurname.setText("");
                            edtMarks.setText("");
                        }
                        else
                            Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = myDb.deleteData(edtId.getText().toString());

                if(isDeleted == true) {
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    edtId.setText("");
                    edtName.setText("");
                    edtSurname.setText("");
                    edtMarks.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Error When Deleting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}