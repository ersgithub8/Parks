package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class PendingVehicleEdit extends AppCompatActivity {
    TextView driver,coordinator,date,monitor;
    Button update;
    Spinner spinner;
    String id;
    int i=0;
    CharSequence s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_vehicle_edit);
        driver=findViewById(R.id.dpve);
        coordinator=findViewById(R.id.cpve);
        monitor=findViewById(R.id.mpve);
        date=findViewById(R.id.datepve);
        spinner=findViewById(R.id.spinnerpve);
        Date d = new Date();
        s = DateFormat.format("dd-MM-yyyy", d.getTime());
        date.setText(String.valueOf(s));
        update=findViewById(R.id.btnupdatepve);
        driver.setText(getIntent().getStringExtra("driver"));
        coordinator.setText(getIntent().getStringExtra("coordinator"));
        monitor.setText(getIntent().getStringExtra("monitor"));
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

        final AlertDialog loading=new ProgressDialog(PendingVehicleEdit.this);
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("id",id);
        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/change_veh_assignment_status"
                , params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");

                    String data=response.getString("data");
                    if (status){
                         Toast.makeText(PendingVehicleEdit.this,data,Toast.LENGTH_SHORT).show();
                        update.setEnabled(false);
                    }
                    else {
                        Toast.makeText(PendingVehicleEdit.this,data,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(PendingVehicleEdit.this, e+"", Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(PendingVehicleEdit.this,error+"",Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(PendingVehicleEdit.this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PendingVehicleEdit.this,PendingVehicle.class));
        finish();
    }
}
