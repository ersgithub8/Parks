package com.example.bookticket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookticket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCrowdAdapter extends RecyclerView.Adapter<ViewCrowdAdapter.ViewCrowdViewHolder> {
    ArrayList<String> serial=new ArrayList<String>();
    ArrayList<String> season=new ArrayList<String>();
    ArrayList<String> timezone=new ArrayList<String>();
    ArrayList<String> crowd=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();

    public ViewCrowdAdapter(ArrayList<String> serial, ArrayList<String> season, ArrayList<String> timezone,
                            ArrayList<String> crowd, ArrayList<String> date, ArrayList<String> time) {
        this.serial = serial;
        this.season = season;
        this.timezone = timezone;
        this.crowd = crowd;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public ViewCrowdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.crowdviewdata,parent,false);
        return new ViewCrowdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCrowdViewHolder holder, int position) {
        String sr=serial.get(position);
        String sseasonb=season.get(position);
        String timezoneb=timezone.get(position);
        String crowdb=crowd.get(position);
        String timeb=time.get(position);
        String dateb=date.get(position);

        holder.seasontv.setText(sseasonb);
        holder.timezonetv.setText(timezoneb);
        if(!crowdb.equals("1")){

            holder.crowdtv.setText(crowdb+" People");

        }else {

            holder.crowdtv.setText(crowdb+" Person");

        }
        holder.timetv.setText(timeb);
        holder.datetv.setText(dateb);
    }

    @Override
    public int getItemCount() {
        return serial.size();
    }

    public class ViewCrowdViewHolder extends RecyclerView.ViewHolder {
        TextView seasontv,timezonetv,crowdtv,timetv,datetv;
        public ViewCrowdViewHolder(@NonNull View itemView) {
            super(itemView);
            seasontv=itemView.findViewById(R.id.cvseason);
            timezonetv=itemView.findViewById(R.id.cvtz);
            crowdtv=itemView.findViewById(R.id.cvcrowd);
            timetv=itemView.findViewById(R.id.cvtime);
            datetv=itemView.findViewById(R.id.cvdate);

        }
    }
}
