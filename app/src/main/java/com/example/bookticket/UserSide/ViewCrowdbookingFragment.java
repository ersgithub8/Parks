package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.Adapter.ViewCrowdAdapter;
import com.example.bookticket.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCrowdbookingFragment extends Fragment {
    RecyclerView crowddata;
    TextView error;
    ArrayList<String> serial=new ArrayList<String>();
    ArrayList<String> season=new ArrayList<String>();
    ArrayList<String> timezone=new ArrayList<String>();
    ArrayList<String> crowd=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_viewcrowdbooking,container,false);
        crowddata=view.findViewById(R.id.rvvcvcf);
        error=view.findViewById(R.id.errorvcb);
        getData();
        return view;
    }

    private void getData() {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/get_crowd_list",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();

                        Boolean status=null;

                        try {
                            status = response.getBoolean("response");
                            if (status){
                                JSONArray jsonArray=response.getJSONArray("data");

                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    serial.add(object.getString("no"));
                                    season.add(object.getString("season"));
                                    timezone.add(object.getString("time_zone"));
                                    crowd.add(object.getString("crowd"));
                                    time.add(object.getString("c_time"));
                                    date.add(object.getString("c_date"));



                                }
                            }else {
                                Toast.makeText(getContext(),response.getString("data"),Toast.LENGTH_SHORT).show();

                            }
                            if(crowd.isEmpty()){
                                error.setVisibility(View.VISIBLE);
                                crowddata.setVisibility(View.GONE);

                            }else {
                                crowddata.setLayoutManager(new LinearLayoutManager(getContext()));
                                crowddata.setAdapter(new ViewCrowdAdapter(serial, season, timezone, crowd, date, time));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext(),error+"",Toast.LENGTH_SHORT).show();
            }
        }
        );



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
