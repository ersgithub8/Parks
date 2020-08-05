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
import com.example.bookticket.Adapter.PendingAssignmentAdapter;
import com.example.bookticket.Adapter.PendingVehicleAdapter;
import com.example.bookticket.Interfacee;
import com.example.bookticket.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingAssignment extends AppCompatActivity implements Interfacee {
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> eid=new ArrayList<String>();
    ArrayList<String> ename=new ArrayList<String>();
    ArrayList<String> monitorname=new ArrayList<String>();
    ArrayList<String> monitorid=new ArrayList<String>();
    ArrayList<String> pid=new ArrayList<String>();
    ArrayList<String> pname=new ArrayList<String>();
    ArrayList<String> pcatrgory=new ArrayList<String>();
    ArrayList<String> task=new ArrayList<String>();
    ArrayList<String> site=new ArrayList<String>();
    ArrayList<String> statuss=new ArrayList<String>();
    ArrayList<String> assigndate=new ArrayList<String>();
    ArrayList<String> completedate=new ArrayList<String>();
    ArrayList<String> image=new ArrayList<String>();
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    PendingAssignmentAdapter pendingAssignmentAdapter;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_assignment);
        sharedPreferences=getSharedPreferences("AdminData",MODE_PRIVATE);
        recyclerView=findViewById(R.id.rvpa);
        error=findViewById(R.id.errorpa);
        pendingAssignmentAdapter=new PendingAssignmentAdapter(getApplicationContext(),no,eid,ename,monitorname,monitorid,pid,
                pname,pcatrgory,task,site,statuss,assigndate,completedate,image,PendingAssignment.this);
        getAssignmentdata();

    }

    private void getAssignmentdata() {
        final AlertDialog loading=new ProgressDialog(PendingAssignment.this);
        loading.setMessage("Loading...");
        loading.show();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/pending_assignments?e_id="+sharedPreferences.getString("emp_id",null),
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
                                    eid.add(object.getString("e_id"));
                                    ename.add(object.getString("e_name"));
                                    monitorname.add(object.getString("monitor_name"));
                                    monitorid.add(object.getString("monitor_id"));
                                    pid.add(object.getString("p_id"));
                                    pname.add(object.getString("p_name"));
                                    pcatrgory.add(object.getString("p_category"));
                                    task.add(object.getString("task"));
                                    site.add(object.getString("site"));
                                    statuss.add(object.getString("status"));
                                    assigndate.add(object.getString("assign_date"));
                                    completedate.add(object.getString("complete_date"));
                                    image.add(object.getString("image"));



                                }
                            } else {
                                Toast.makeText(PendingAssignment.this, response.getString("data"), Toast.LENGTH_SHORT).show();
                            }
                            if(completedate.isEmpty()){
                                error.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{

                                recyclerView.setLayoutManager(new LinearLayoutManager(PendingAssignment.this));
                                recyclerView.setAdapter(pendingAssignmentAdapter);
                            }
                        } catch (Exception e) {
                            Toast.makeText(PendingAssignment.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(PendingAssignment.this, error + "", Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(PendingAssignment.this);
        queue.add(jsonObjectRequest);
    }



    @Override
    public void onitemClick(String id, String task) {
        Intent intent=new Intent(PendingAssignment.this, PendingAssignmentedit.class);
        {
            intent.putExtra("id", id);
            intent.putExtra("task", task);
            startActivity(intent);
            finish();
        }
        }
}
