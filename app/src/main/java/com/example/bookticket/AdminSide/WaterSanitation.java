package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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

public class WaterSanitation extends AppCompatActivity {
    Spinner spinner;
    TextInputLayout feedback;
    String area;
    SharedPreferences sharedPreferences;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sanitation);
        sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
        spinner=(Spinner)findViewById(R.id.spinner);
        feedback=findViewById(R.id.etfb);
        submit=findViewById(R.id.btnfb);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItemId()==0)
                {
                        area="East";
                }else             if(spinner.getSelectedItemId()==1)
                {
                        area="West";
                }else             if(spinner.getSelectedItemId()==2)
                {
                        area="North";

                }else             if(spinner.getSelectedItemId()==3)
                {
                        area="South";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback.getEditText().getText().toString().trim().isEmpty()){
                    feedback.setError("Field can't be empty");
                }else{
                    addfeedback( area,feedback.getEditText().getText().toString());
                }
            }
        });
    }

    private void addfeedback(String area, final String feedback1) {
        final AlertDialog loading=new ProgressDialog(WaterSanitation.this);
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("emp_name",sharedPreferences.getString("emp_name",null));
        params.put("emp_id",sharedPreferences.getString("emp_id",null));
        params.put("site",area);
        params.put("feedback",feedback1);
        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/water_sanitation_feedback", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");
                    String msg = response.getString("data");
                    if (status){
                        feedback.getEditText().setText("");
                        Toast.makeText(WaterSanitation.this,msg,Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(WaterSanitation.this,msg,Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(WaterSanitation.this,error+"",Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(WaterSanitation.this);
        queue.add(jsonObjectRequest);
    }
}
