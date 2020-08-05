package com.example.bookticket.AdminSide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.Adapter.GetVehicleAdapter;
import com.example.bookticket.Adapter.PendingVehicleAdapter;
import com.example.bookticket.Interfaceee;
import com.example.bookticket.R;
import com.example.bookticket.UserSide.Signin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingVehicle extends AppCompatActivity implements Interfaceee {
    Button logout ;
    TextView error;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> park=new ArrayList<String>();
    ArrayList<String> vehicletype=new ArrayList<String>();
    ArrayList<String> registrationno=new ArrayList<String>();
    ArrayList<String> capacity=new ArrayList<String>();
    ArrayList<String> task=new ArrayList<String>();
    ArrayList<String> coveragearea=new ArrayList<String>();
    ArrayList<String> driverid=new ArrayList<String>();
    ArrayList<String> coordinatorid=new ArrayList<String>();
    ArrayList<String> monitorid=new ArrayList<String>();
    ArrayList<String> statuss=new ArrayList<String>();
    ArrayList<String> assigntime=new ArrayList<String>();
    ArrayList<String> assigndate=new ArrayList<String>();
    ArrayList<String> completedate=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_vehicle);
        logout=findViewById(R.id.log2);
        sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
        error=findViewById(R.id.errorpv);
        recyclerView=findViewById(R.id.rvpv);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                startActivity(new Intent(PendingVehicle.this, Signin.class));
                finish();
            }
        });
        getvehicledata();
    }

    private void getvehicledata() {
        final AlertDialog loading=new ProgressDialog(PendingVehicle.this);
        loading.setMessage("Loading...");
        loading.show();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/pending_veh_assignments?e_id="+sharedPreferences.getString("emp_id",null),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();

                        Boolean status = null;
                        try {
                            status = response.getBoolean("response");
                            if (status) {
                                JSONArray jsonArray = response.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    no.add(object.getString("no"));
                                    park.add(object.getString("park"));
                                    vehicletype.add(object.getString("vehicle_type"));
                                    registrationno.add(object.getString("registration_no"));
                                    capacity.add(object.getString("capacity"));
                                    task.add(object.getString("task"));
                                    coveragearea.add(object.getString("coverage_area"));
                                    driverid.add(object.getString("driver_id"));
                                    coordinatorid.add(object.getString("coordinator_id"));
                                    monitorid.add(object.getString("monitor_id"));
                                    statuss.add(object.getString("status"));
                                    assigntime.add(object.getString("assign_time"));
                                    assigndate.add(object.getString("assign_date"));
                                    completedate.add(object.getString("complete_date"));



                                }
                            } else {
                                Toast.makeText(PendingVehicle.this, response.getString("data"), Toast.LENGTH_SHORT).show();
                            }
                            if(completedate.isEmpty()){
                                error.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{

                                recyclerView.setLayoutManager(new LinearLayoutManager(PendingVehicle.this));
                                recyclerView.setAdapter(new PendingVehicleAdapter(getApplicationContext(),no,park,vehicletype,registrationno,capacity
                                        ,task,coveragearea,driverid,coordinatorid,monitorid,statuss,assigntime,assigndate,completedate,PendingVehicle.this));
                            }
                        } catch (Exception e) {
                            Toast.makeText(PendingVehicle.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(PendingVehicle.this, error + "", Toast.LENGTH_SHORT).show();

            }
        });
//        {
//            @Override
//            protected Response parseNetworkResponse(NetworkResponse response) {
//                return null;
//            }
//
//            @Override
//            public int compareTo(Object o) {
//                return 0;
//            }
//        };

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
        RequestQueue queue = Volley.newRequestQueue(PendingVehicle.this);
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onitemClick(String id, String driver, String coordinator, String monitor) {
        Intent intent=new Intent(PendingVehicle.this, PendingVehicleEdit.class);
        intent.putExtra("id",id);
        intent.putExtra("driver",driver);
        intent.putExtra("coordinator",coordinator);
        intent.putExtra("monitor",monitor);
        startActivity(intent);
        finish();
    }


}
