package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.bookticket.Adapter.TrackParkAdapter;
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

public class TrackParkFragment extends Fragment {
    RecyclerView trackparkrv;
    TextView error;
    ArrayList<String> number=new ArrayList<String>();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();
    ArrayList<String> area=new ArrayList<String>();
    ArrayList<String> ownership=new ArrayList<String>();
    ArrayList<String> description=new ArrayList<String>();
    ArrayList<String> path=new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trackpark,container,false);
        trackparkrv=view.findViewById(R.id.rvftp);
        error=view.findViewById(R.id.errortp);
        gettrackparkdata();

        return view;
    }

    private void gettrackparkdata() {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/get_tracks",
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
                                    number.add(object.getString("no"));
                                    name.add(object.getString("name"));
                                    location.add(object.getString("location"));
                                    area.add(object.getString("area"));
                                    ownership.add(object.getString("ownership"));
                                    description.add(object.getString("description"));
                                    path.add(object.getString("path"));
                                }
                            }else {
                                Toast.makeText(getContext(),response.getString("data"),Toast.LENGTH_SHORT).show();

                            }
                            if(number.isEmpty()){
                                error.setVisibility(View.VISIBLE);

                            }else {
                                trackparkrv.setLayoutManager(new LinearLayoutManager(getContext()));
                                trackparkrv.setAdapter(new TrackParkAdapter(getContext(),number, name, location, area, ownership,
                                        description,path));
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
