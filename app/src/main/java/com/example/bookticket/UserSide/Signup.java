package com.example.bookticket.UserSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class Signup extends AppCompatActivity {
    TextInputLayout name,contact,email,address,password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(TextInputLayout)findViewById(R.id.namesu);
        contact=(TextInputLayout)findViewById(R.id.Contactsu);
        email=(TextInputLayout)findViewById(R.id.emailsu);
        address=(TextInputLayout)findViewById(R.id.addressu);
        password=(TextInputLayout)findViewById(R.id.passwordsu);
        button=(Button)findViewById(R.id.btnssignup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatedata(name,contact,address,email,password)){
                    //Toast.makeText(Signup.this, email.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();


                    GetData(name.getEditText().getText().toString(),contact.getEditText().getText().toString(),
                            email.getEditText().getText().toString(),address.getEditText().getText().toString()
                            ,password.getEditText().getText().toString());
                }

            }
        });

    }

    public void GetData(String namegd, String contactgd, String emailgd, String addressgd, String passwordgd) {
            final AlertDialog loading=new ProgressDialog(Signup.this);
            loading.setMessage("Loading...");
            loading.show();

            Map<String,String> params=new Hashtable<String, String>();
            params.put("name",namegd);
            params.put("contact",contactgd);
        params.put("email",emailgd);
        params.put("address",addressgd);
        params.put("password",passwordgd);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/signup", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");
                    String msg = response.getString("data");
                    if (status){
                        Toast.makeText(Signup.this,msg,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this,Signin.class));
                    }
                    else {
                        Toast.makeText(Signup.this,msg,Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(Signup.this,error+"",Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(Signup.this);
        queue.add(jsonObjectRequest);
    }

    private boolean validatedata(TextInputLayout name,TextInputLayout contact,TextInputLayout address,
                                 TextInputLayout email,TextInputLayout password){
        int i=0;
        if(Signin.validateEmail(email.getEditText().getText().toString().trim(),email) ){
            i++;
        }

        if(name.getEditText().getText().toString().trim().isEmpty()){
            name.setError("Field can't be empty");
        }else
            i++;
        if(contact.getEditText().getText().toString().trim().isEmpty()){
            contact.setError("Field can't be empty");
        }else if (contact.getEditText().getText().toString().trim().length()<11)
        {
            contact.setError("Invalid Contact");

        }
            else
            i++;
        if(address.getEditText().getText().toString().trim().isEmpty()){
            address.setError("Field can't be empty");
        }else
            i++;
        if(password.getEditText().getText().toString().trim().isEmpty()){
            password.setError("Field can't be empty");
        }else
            i++;
    if(i==5){
        return true;
    }else
    {
        return false;
    }

    }
}
