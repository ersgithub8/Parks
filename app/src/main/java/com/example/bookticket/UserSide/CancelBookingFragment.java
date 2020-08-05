package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.Volley;
import com.example.bookticket.Adapter.CancelRecordAdapter;
import com.example.bookticket.Adapter.ViewRecordAdapter;
import com.example.bookticket.Interfac;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CancelBookingFragment extends Fragment implements Interfac {
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> park=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();
    ArrayList<String> timezone=new ArrayList<String>();
    ArrayList<String> ticketprice=new ArrayList<String>();
    ArrayList<String> tptalperson=new ArrayList<String>();
    ArrayList<String> totalprice=new ArrayList<String>();
    ArrayList<String> statuss=new ArrayList<String>();

    SharedPreferences sharedPreferences;
    TextView error;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cancelbooking,container,false);
        recyclerView=view.findViewById(R.id.rvcb);
        sharedPreferences=getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        error=view.findViewById(R.id.errorcb);
        getbookingrecord();
        return view;
    }
    private void getbookingrecord() {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        final Map<String,String> params=new Hashtable<String, String>();
        params.put("email",sharedPreferences.getString("email",null));

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/get_cancel_bookings",
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            no.add(object.getString("no"));
                            park.add(object.getString("park"));
                            time.add(object.getString("rec_date"));
                            date.add(object.getString("rec_time"));
                            timezone.add(object.getString("time_zone"));
                            ticketprice.add(object.getString("ticket_price"));
                            tptalperson.add(object.getString("total_persons"));
                            totalprice.add(object.getString("total_price"));
                            statuss.add(object.getString("status"));



                        }
                    }
                    else{
                        Toast.makeText(getContext(), response.getString("data"), Toast.LENGTH_SHORT).show();
                    }
                    if(park.isEmpty()){
                        error.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }else
                    {

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new CancelRecordAdapter(getContext(),no,park,date,time,timezone,ticketprice,
                                tptalperson,totalprice,statuss,CancelBookingFragment.this));

                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), e+"", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onitemClick(String id) {
            deleteitem(id);
    }
    public  void deleteitem(final String no5){
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        final Map<String,String> params=new Hashtable<String, String>();
        params.put("booking_id",no5);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/cancel_booking",
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");

                    String data=response.getString("data");
                    if (status) {

//                        getActivity().getSupportFragmentManager().beginTransaction().replace(CancelBookingFragment.this.getId(),
//                                new CancelBookingFragment()).commit();

                        getActivity().onBackPressed();
                        Toast.makeText(getContext().getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext().getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext().getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext().getApplicationContext(),error+"",Toast.LENGTH_SHORT).show();

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
