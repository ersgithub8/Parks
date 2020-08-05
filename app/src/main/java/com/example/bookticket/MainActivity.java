package com.example.bookticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.bookticket.AdminSide.AdminHome;
import com.example.bookticket.UserSide.HomeUser;
import com.example.bookticket.UserSide.Signin;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences,sharedPreferences1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("UserData",MODE_PRIVATE);
        sharedPreferences1=getSharedPreferences("AdminData",MODE_PRIVATE);
        final String id=sharedPreferences.getString("visitor_id",null);
        final String idadmin=sharedPreferences1.getString("no",null);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if (id!=null){
                startActivity(new Intent(MainActivity.this, HomeUser.class));
                finish();
            }
            else if(idadmin!=null)
            {
                startActivity(new Intent(MainActivity.this, AdminHome.class));
                finish();

            }
            else{
                startActivity(new Intent(MainActivity.this, Signin.class));
                finish();
            }

        }
    },3000);
    }
}
