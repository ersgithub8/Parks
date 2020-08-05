package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookticket.R;
import com.example.bookticket.UserSide.Signin;

public class EmployeeAssignment extends AppCompatActivity {
    Button pendingassignment,viewrecords,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_assignment);
    pendingassignment=(Button)findViewById(R.id.btnpaea);
        viewrecords=(Button)findViewById(R.id.btnvrea);

        logout=findViewById(R.id.log3);
    pendingassignment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(EmployeeAssignment.this,PendingAssignment.class);
            startActivity(intent);
        }
    });
    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences;
            sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.commit();
            editor.apply();
            startActivity(new Intent(EmployeeAssignment.this, Signin.class));
            finish();
        }
    });
    viewrecords.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(EmployeeAssignment.this,ViewRecord.class);
            startActivity(intent);
        }
    });
    }

}
