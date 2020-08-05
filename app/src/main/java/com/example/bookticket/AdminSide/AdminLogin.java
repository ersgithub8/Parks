package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.R;
import com.example.bookticket.UserSide.HomeUser;
import com.example.bookticket.UserSide.Signin;
import com.example.bookticket.volley.CustomRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class AdminLogin extends AppCompatActivity {
    TextInputLayout email,password;
    Button login;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    email =(TextInputLayout)findViewById(R.id.emailad);
    password =(TextInputLayout)findViewById(R.id.passwordad);
    login =(Button) findViewById(R.id.btnsigninad);
    sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if(validatedata(email,password)){
//            startActivity(new Intent(AdminLogin.this, AdminHome.class));
//            finish();
            getAdminData(email.getEditText().getText().toString().trim(),password.getEditText().getText().toString().trim());

        }
        }
    });

    }

    private void getAdminData(String email, String password) {
        final AlertDialog loading=new ProgressDialog(AdminLogin.this);
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("email",email);
        params.put("password",password);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/admin_login"
                , params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");

                    if (status){

                        JSONArray jsonArray=response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);

                            String no=object.getString("no");
                            String park=object.getString("park");
                            String emp_id=object.getString("emp_id");
                            String emp_name=object.getString("emp_name");
                            String emp_pw=object.getString("emp_pw");
                            String emp_cnic=object.getString("emp_cnic");
                            String emp_age=object.getString("emp_age");
                            String emp_gender=object.getString("emp_gender");
                            String emp_contact=object.getString("emp_contact");
                            String emp_email=object.getString("emp_email");
                            String emp_qualification=object.getString("emp_qualification");
                            String emp_joining_date=object.getString("emp_joining_date");
                            String emp_department=object.getString("emp_department");
                            String emp_designation=object.getString("emp_designation");
                            String emp_category=object.getString("emp_category");
                            String emp_type=object.getString("emp_type");
                            String emp_salary=object.getString("emp_salary");
                            String emp_contract_duration=object.getString("emp_contract_duration");
                            String emp_address=object.getString("emp_address");

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("no",no);
                            editor.putString("park",park);
                            editor.putString("emp_id",emp_id);
                            editor.putString("emp_name",emp_name);
                            editor.putString("emp_pw",emp_pw);

                            editor.putString("emp_cnic",emp_cnic);
                            editor.putString("emp_age",emp_age);
                            editor.putString("emp_gender",emp_gender);
                            editor.putString("emp_contact",emp_contact);
                            editor.putString("emp_email",emp_email);
                            editor.putString("emp_qualification",emp_qualification);
                            editor.putString("emp_joining_date",emp_joining_date);
                            editor.putString("emp_department",emp_department);
                            editor.putString("emp_designation",emp_designation);
                            editor.putString("emp_category",emp_category);
                            editor.putString("emp_type",emp_type);
                            editor.putString("emp_salary",emp_salary);
                            editor.putString("emp_contract_duration",emp_contract_duration);
                            editor.putString("emp_address",emp_address);
                            editor.apply();
                            startActivity(new Intent(AdminLogin.this, AdminHome.class));
                            finish();
                            //Toast.makeText(Signin.this, jsonArray.getString(0), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(AdminLogin.this,response.getString("data"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(AdminLogin.this, e+"", Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(AdminLogin.this,error+"",Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(AdminLogin.this);
        queue.add(jsonObjectRequest);
    }
    public  boolean validatedata(TextInputLayout email,TextInputLayout password){
        int i=0;
        if(Signin.validateEmail(email.getEditText().getText().toString().trim(),email)){
            i++;
        }
        if (password.getEditText().getText().toString().trim().isEmpty()){
            password.setError("Field can't be empty");
        }else{
            i++;
        }
        if(i==2){
            return true;
        }else
            return false;
    }



    private void getAdminData1(final String email, final String password) {
        final AlertDialog loading=new ProgressDialog(AdminLogin.this);
        loading.setMessage("Loading...");
        loading.show();

//        Map<String,String> params=new Hashtable<String, String>();
//        params.put("email",email);
//        params.put("password",password);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/admin_login"
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");

                    if (status){

                        JSONArray jsonArray=response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);

                            String no=object.getString("no");
                            String park=object.getString("park");
                            String emp_id=object.getString("emp_id");
                            String emp_name=object.getString("emp_name");
                            String emp_pw=object.getString("emp_pw");
                            String emp_cnic=object.getString("emp_cnic");
                            String emp_age=object.getString("emp_age");
                            String emp_gender=object.getString("emp_gender");
                            String emp_contact=object.getString("emp_contact");
                            String emp_email=object.getString("emp_email");
                            String emp_qualification=object.getString("emp_qualification");
                            String emp_joining_date=object.getString("emp_joining_date");
                            String emp_department=object.getString("emp_department");
                            String emp_designation=object.getString("emp_designation");
                            String emp_category=object.getString("emp_category");
                            String emp_type=object.getString("emp_type");
                            String emp_salary=object.getString("emp_salary");
                            String emp_contract_duration=object.getString("emp_contract_duration");
                            String emp_address=object.getString("emp_address");

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("no",no);
                            editor.putString("park",park);
                            editor.putString("emp_id",emp_id);
                            editor.putString("emp_name",emp_name);
                            editor.putString("emp_pw",emp_pw);

                            editor.putString("emp_cnic",emp_cnic);
                            editor.putString("emp_age",emp_age);
                            editor.putString("emp_gender",emp_gender);
                            editor.putString("emp_contact",emp_contact);
                            editor.putString("emp_email",emp_email);
                            editor.putString("emp_qualification",emp_qualification);
                            editor.putString("emp_joining_date",emp_joining_date);
                            editor.putString("emp_department",emp_department);
                            editor.putString("emp_designation",emp_designation);
                            editor.putString("emp_category",emp_category);
                            editor.putString("emp_type",emp_type);
                            editor.putString("emp_salary",emp_salary);
                            editor.putString("emp_contract_duration",emp_contract_duration);
                            editor.putString("emp_address",emp_address);
                            editor.apply();
                            startActivity(new Intent(AdminLogin.this, AdminHome.class));
                            finish();
                            //Toast.makeText(Signin.this, jsonArray.getString(0), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {

                        Toast.makeText(AdminLogin.this,response.getString("data"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(AdminLogin.this, e+"", Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(AdminLogin.this,error+"",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String,String> params=new Hashtable<String, String>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(AdminLogin.this);
        queue.add(jsonObjectRequest);
    }
}
