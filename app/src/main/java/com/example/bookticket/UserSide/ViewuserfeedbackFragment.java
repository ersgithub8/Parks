package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.Adapter.FeedBackAdapter;
import com.example.bookticket.Adapter.ViewCrowdAdapter;
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewuserfeedbackFragment extends Fragment {
    RecyclerView viewuserfbrv;
    TextView error;
    ArrayList<String> description=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_viewuserfeedback,container,false);
        viewuserfbrv=view.findViewById(R.id.rvfvuf);
        sharedPreferences=getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        error=view.findViewById(R.id.errorvuf);
        getuserfeedbackdata();
        return view;
    }

    private void getuserfeedbackdata() {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();


        Map<String,String> params=new Hashtable<String, String>();
        params.put("email",sharedPreferences.getString("email",null));

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/get_feedbck",
                params,
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
                                    description.add(object.getString("complaint"));

                                    time.add(object.getString("comp_time"));
                                    date.add(object.getString("comp_date"));


                                }
                            }else {
                                Toast.makeText(getContext(),response.getString("data"),Toast.LENGTH_SHORT).show();

                            }
                            if(description.isEmpty()){
                                error.setVisibility(View.VISIBLE);
                                viewuserfbrv.setVisibility(View.GONE);
                            }else
                            {
                                viewuserfbrv.setLayoutManager(new LinearLayoutManager(getContext()));
                                viewuserfbrv.setAdapter(new FeedBackAdapter(description,time,date));
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
