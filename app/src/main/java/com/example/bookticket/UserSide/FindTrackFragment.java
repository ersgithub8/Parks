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
import com.example.bookticket.Adapter.FindTrackAdapter;
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

public class FindTrackFragment extends Fragment {
    RecyclerView findtracks;
    TextView error;
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> path=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_findtrack,container,false);
        findtracks=view.findViewById(R.id.rvfft);
        error=view.findViewById(R.id.errorft);
        gettracks();


        return view;
    }

    private void gettracks() {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/find_tracks",
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

                                    path.add(object.getString("path"));
                                    title.add(object.getString("title"));
                                     }
                            }else {
                                Toast.makeText(getContext(),response.getString("data"),Toast.LENGTH_SHORT).show();

                            }
                            if(title.isEmpty()){
                                error.setVisibility(View.VISIBLE);
                            }else {
                                findtracks.setLayoutManager(new LinearLayoutManager(getContext()));
                                findtracks.setAdapter(new FindTrackAdapter(getContext(),title,path));
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e+"", Toast.LENGTH_SHORT).show();
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
