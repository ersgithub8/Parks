package com.example.bookticket.UserSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.bookticket.R;
import com.example.bookticket.volley.CustomRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {
    TextInputLayout noofpeople;
    TextView ticketprice,totalticketprice;
    Button Amount;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_booking,container,false);
        noofpeople=view.findViewById(R.id.etnopfb);
        ticketprice=view.findViewById(R.id.tvtpbf);
        totalticketprice=view.findViewById(R.id.tvtopbf);
        sharedPreferences=getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Amount=view.findViewById(R.id.btntotal);
        gtPrice();

        noofpeople.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!noofpeople.getEditText().getText().toString().isEmpty()){
                    int total,nop,price;
                    nop=Integer.valueOf(noofpeople.getEditText().getText().toString().trim());
                    price=Integer.valueOf(ticketprice.getText().toString().trim());
                    total=nop*price;

                    totalticketprice.setText(total+"");

                }
                else {
                    totalticketprice.setText("0");
                }
                }
        });
        Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookorder(noofpeople.getEditText().getText().toString().trim(),ticketprice.getText().toString()
                ,"100");
            }
        });
        return view;

    }

    private void bookorder(String people, String ticketprice, String s) {
        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        Map<String,String> params=new Hashtable<String, String>();
        params.put("email",sharedPreferences.getString("email",null));
        params.put("people",people);
        params.put("ticket_price",ticketprice);
        params.put("cash",s);

        CustomRequest jsonObjectRequest=new CustomRequest(Request.Method.POST
                ,"http://admotors-admin.bazar.com.pk/ci/index.php/api/add_booking"
                , params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Boolean status = null;
                try{
                    status = response.getBoolean("response");

                    String data=response.getString("data");
                    if (status){

                        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                        noofpeople.getEditText().setText("");
                    }
                    else {
                        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(getContext(), e+"", Toast.LENGTH_LONG).show();
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

    private void gtPrice() {

        final AlertDialog loading=new ProgressDialog(getContext());
        loading.setMessage("Loading...");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                "http://admotors-admin.bazar.com.pk/ci/index.php/api/get_ticket_price",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();

                        Boolean status=null;

                        try {
                            status = response.getBoolean("response");
                            JSONArray jsonArray=response.getJSONArray("data");
                            if (status){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    int price=Integer.valueOf(object.getString("ticket_price"));

                                    ticketprice.setText(price+"");
                                }
                            }else {
                                Toast.makeText(getContext(),response.getString("data"),Toast.LENGTH_SHORT).show();

                            }



                        } catch (Exception e) {
                            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
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
