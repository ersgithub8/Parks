package com.example.bookticket.UserSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.AdminSide.AdminLogin;
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class Signin extends AppCompatActivity {
    TextInputLayout email,password;
    Button adminlogin,signin;
    TextView signup;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email=(TextInputLayout) findViewById(R.id.emailsi);
        password=(TextInputLayout) findViewById(R.id.passwordsi);
        sharedPreferences=getSharedPreferences("UserData",MODE_PRIVATE);

        adminlogin=(Button)findViewById(R.id.al);
        signin=(Button)findViewById(R.id.btnsignin);
        signup=(TextView) findViewById(R.id.tvsusi);
        String text="don't have an account Signup here";
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatedata(email,password)){


                    GetData(email.getEditText().getText().toString()
                            ,password.getEditText().getText().toString());

                }
//                startActivity(new Intent(Signin.this, HomeUser.class));
//                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, Signup.class));

            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, AdminLogin.class));
                finish();

            }
        });

    }

    private void GetData(String emailgd, final String passwordgd) {
        final AlertDialog loading=new ProgressDialog(Signin.this);
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("email",emailgd);
        params.put("password",passwordgd);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/login"
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

                            String id=object.getString("visitor_id");
                            String name=object.getString("name");
                            String email=object.getString("email");
                            String contact=object.getString("contact");
                            String address=object.getString("address");



                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("visitor_id",id);
                            editor.putString("name",name);
                            editor.putString("password",password.getEditText().getText().toString());
                            editor.putString("email",email);
                            editor.putString("contact",contact);
                            editor.putString("address",address);
                            editor.apply();
                            startActivity(new Intent(Signin.this,HomeUser.class));
                            finish();
                            //Toast.makeText(Signin.this, jsonArray.getString(0), Toast.LENGTH_SHORT).show();

                        }
                       }
                    else {
                        Toast.makeText(Signin.this,response.getString("data"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(Signin.this, e+"", Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(Signin.this,error+"",Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(Signin.this);
        queue.add(jsonObjectRequest);
    }


    public  boolean validatedata(TextInputLayout email,TextInputLayout password){
        int i=0;
        if(validateEmail(email.getEditText().getText().toString().trim(),email)){
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
    public static boolean validateEmail(String emailInput ,TextInputLayout textInputLayout) {
        //String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputLayout.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputLayout.setError("Please enter a valid email address");
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }

}
