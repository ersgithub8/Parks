package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class PendingAssignmentedit extends AppCompatActivity {
    TextView date,task;
    Spinner spinner;
    CharSequence s;
    Button update;
    String id;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_assignmentedit);
        spinner=findViewById(R.id.spinnerpae);
        date=findViewById(R.id.datepae);
        task=findViewById(R.id.taskpae);
        update=findViewById(R.id.btnupdatepae);
        Date d = new Date();
        s = DateFormat.format("dd-MM-yyyy", d.getTime());
        date.setText(String.valueOf(s));
        task.setText(getIntent().getStringExtra("task"));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItemId()==0)
                {
                        update.setEnabled(false);
                }else             if(spinner.getSelectedItemId()==1)
                {
                        update.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id=getIntent().getStringExtra("id");
                updatedata(id);
            }
        });
    }

    private void updatedata(String id) {

            final AlertDialog loading=new ProgressDialog(PendingAssignmentedit.this);
            loading.setMessage("Loading...");
            loading.show();

            Map<String,String> params=new Hashtable<String, String>();
            params.put("id",id);
            CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                    ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/change_assignment_status"
                    , params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();

                    Boolean status = null;
                    try{
                        status = response.getBoolean("response");
                        String data=response.getString("data");
                        if (status){
                            Toast.makeText(PendingAssignmentedit.this,data,Toast.LENGTH_SHORT).show();
                            update.setEnabled(false);
                        }
                        else {
                            Toast.makeText(PendingAssignmentedit.this,data,Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(PendingAssignmentedit.this, e+"", Toast.LENGTH_LONG).show();
                    }
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(PendingAssignmentedit.this,error+"",Toast.LENGTH_SHORT).show();

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
            RequestQueue queue = Volley.newRequestQueue(PendingAssignmentedit.this);
            queue.add(jsonObjectRequest);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PendingAssignmentedit.this,PendingAssignment.class));
        finish();
    }
}
