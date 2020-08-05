package com.example.bookticket.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bookticket.Interfac;
import com.example.bookticket.R;
import com.example.bookticket.UserSide.BookingFragment;
import com.example.bookticket.UserSide.CancelBookingFragment;
import com.example.bookticket.UserSide.HomeUser;
import com.example.bookticket.volley.CustomRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CancelRecordAdapter extends RecyclerView.Adapter<CancelRecordAdapter.CancelRecordViewHolder> {
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> park=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();
    ArrayList<String> ticketprice=new ArrayList<String>();
    ArrayList<String> tptalperson=new ArrayList<String>();
    ArrayList<String> totalprice=new ArrayList<String>();
    ArrayList<String> statuss=new ArrayList<String>();

    ArrayList<String> timezone=new ArrayList<String>();
    Interfac interfac;
    Context context;
    public CancelRecordAdapter(Context context, ArrayList<String> no, ArrayList<String> park, ArrayList<String> time, ArrayList<String> date, ArrayList<String> timezone
            , ArrayList<String> ticketprice,
                               ArrayList<String> tptalperson, ArrayList<String> totalprice, ArrayList<String> status,Interfac interfac) {
        this.context=context;
        this.no=no;
        this.park = park;
        this.time = time;
        this.date = date;
        this.timezone=timezone;
        this.ticketprice = ticketprice;
        this.tptalperson = tptalperson;
        this.totalprice = totalprice;
        this.statuss = status;
        this.interfac=interfac;
    }

    @NonNull
    @Override
    public CancelRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.viewrecorditems,parent,false);
        return new CancelRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelRecordViewHolder holder, int position) {
        final String no1=no.get(position);
        String park1=park.get(position);
        String date1=date.get(position);
        String time1=time.get(position);
        String tp=ticketprice.get(position);
        String top=tptalperson.get(position);
        String ttp=totalprice.get(position);
        String statuss1=statuss.get(position);
        String timezone1=timezone.get(position);

            holder.parktv.setText(park1);
            holder.datetv.setText(date1);
            holder.timetv.setText(time1);
            holder.tickettv.setText(tp);
            holder.persontv.setText(top);
            holder.totaltv.setText(ttp);
            holder.timezonetv.setText(timezone1);
            holder.status.setText(statuss1);
            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfac.onitemClick(no1);
                }
            });

    }

    @Override
    public int getItemCount() {
        return park.size();
    }

        public class CancelRecordViewHolder extends RecyclerView.ViewHolder{
        TextView parktv,datetv,timetv,tickettv,persontv,totaltv,status,timezonetv;
        ImageButton delete;
        public CancelRecordViewHolder(@NonNull View itemView) {
            super(itemView);
        delete=itemView.findViewById(R.id.delete);
        parktv=itemView.findViewById(R.id.vripark);
        datetv=itemView.findViewById(R.id.vridate);
        timetv=itemView.findViewById(R.id.vritime);
        timezonetv=itemView.findViewById(R.id.vritz);
        tickettv=itemView.findViewById(R.id.vritp);
        persontv=itemView.findViewById(R.id.vritop);
        totaltv=itemView.findViewById(R.id.vrittp);
        status=itemView.findViewById(R.id.vristatus);
        }
    }


}
