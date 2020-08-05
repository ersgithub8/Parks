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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewFeedbackFragment extends Fragment {
    TextInputLayout feedback;
    Button submit;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_newfeedback,container,false);
        feedback=view.findViewById(R.id.etfbnf);
        submit=view.findViewById(R.id.btnsfnf);
        sharedPreferences=getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback.getEditText().getText().toString().isEmpty()){
                    feedback.setError("Field can't be empty");
                }else {
                    addfeedback(feedback.getEditText().getText().toString().trim());
                }
            }
        });


        return view;
    }

    public void addfeedback(final String feedback1) {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("name",sharedPreferences.getString("name",null));
        params.put("email",sharedPreferences.getString("email",null));
        params.put("detail",feedback1);
        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/add_feedback", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");
                    String msg = response.getString("data");
                    if (status){
                        feedback.getEditText().setText("");
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

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
}
