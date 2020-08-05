package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangePassFragment extends Fragment {
    TextInputLayout oldpass,newpass,newpass2;
    SharedPreferences sharedPreferences;

    Button update;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_changepass,container,false);
        oldpass=view.findViewById(R.id.op);
        newpass=view.findViewById(R.id.np1);
        newpass2=view.findViewById(R.id.np2);
        update=view.findViewById(R.id.btnupdate);
        sharedPreferences=getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatedata(oldpass,newpass,newpass2)){
                    changepass(sharedPreferences.getString("email",null),
                            sharedPreferences.getString("password",null)
                             ,newpass2.getEditText().getText().toString().trim());
                }
            }
        });
        return view;
    }

    private void changepass(String email, String password, final String newPass) {

        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("email",email);
        params.put("old_password",password);
        params.put("password",newPass);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/update_password"
                , params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");
                    String data=response.getString("data");
                    if (status){
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("password",newPass);
                        editor.commit();
                        oldpass.getEditText().setText("");
                        newpass.getEditText().setText("");
                        newpass2.getEditText().setText("");
                        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(getContext(), e+"", Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext(),error+"",Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }

    public  boolean validatedata(TextInputLayout oldpass,TextInputLayout newpassword,TextInputLayout newpass2){
        int i=0;
        if(!oldpass.getEditText().getText().toString().equals(sharedPreferences.getString("password",null))){
            oldpass.setError("Incorrect Password");
        }else {
            i++;
        }
        if (newpassword.getEditText().getText().toString().trim().isEmpty()){
            newpassword.setError("Field can't be empty");
        }else{
            i++;
        }
        if (newpass2.getEditText().getText().toString().trim().isEmpty()){
            newpass2.setError("Field can't be empty");
        }else if (!newpass2.getEditText().getText().toString().equals(newpassword.getEditText().getText().toString()))
        {
            newpass2.setError("Password didn't matched");
        }
        else{
            i++;
        }

        if(i==3){
            return true;
        }else
            return false;
    }
}
