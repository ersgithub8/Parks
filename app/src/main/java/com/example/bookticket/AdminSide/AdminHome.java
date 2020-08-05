package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticket.R;
import com.example.bookticket.UserSide.HomeUser;
import com.example.bookticket.UserSide.Signin;

public class AdminHome extends AppCompatActivity {
    Button employmentassignment,vehicleassignment,watersamitation,logout;
    SharedPreferences sharedPreferences;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        employmentassignment=(Button)findViewById(R.id.btneaah);
        vehicleassignment=(Button)findViewById(R.id.btnvaah);
        watersamitation=(Button)findViewById(R.id.btnwsah);
        sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
        logout=(Button)findViewById(R.id.logout);
        name=findViewById(R.id.ahname);

        name.setText(sharedPreferences.getString("emp_name",null).trim());
        employmentassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,EmployeeAssignment.class));
            }
        });
        vehicleassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,VehicleAssignment.class));
            }
        });

        watersamitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(AdminHome.this,WaterSanitation.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                startActivity(new Intent(AdminHome.this, Signin.class));
                finish();
            }
        });
    }


}
