package com.example.bookticket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookticket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GetVehicleAdapter extends RecyclerView.Adapter<GetVehicleAdapter.GetVehicleViewHolder> {

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
    ArrayList<String> status=new ArrayList<String>();
    ArrayList<String> assigntime=new ArrayList<String>();
    ArrayList<String> assigndate=new ArrayList<String>();
    ArrayList<String> completedate=new ArrayList<String>();

    public GetVehicleAdapter(ArrayList<String> no, ArrayList<String> park,
                             ArrayList<String> vehicletype, ArrayList<String> registrationno,
                             ArrayList<String> capacity, ArrayList<String> task, ArrayList<String> coveragearea,
                             ArrayList<String> driverid, ArrayList<String> coordinatorid, ArrayList<String> monitorid,
                             ArrayList<String> status,
                             ArrayList<String> assigntime, ArrayList<String> assigndate, ArrayList<String> completedate) {
        this.no = no;
        this.park = park;
        this.vehicletype = vehicletype;
        this.registrationno = registrationno;
        this.capacity = capacity;
        this.task = task;
        this.coveragearea = coveragearea;
        this.driverid = driverid;
        this.coordinatorid = coordinatorid;
        this.monitorid = monitorid;
        this.status = status;
        this.assigntime = assigntime;
        this.assigndate = assigndate;
        this.completedate = completedate;
    }

    @NonNull
    @Override
    public GetVehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.getvehivleitems,parent,false);
        return new GetVehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetVehicleViewHolder holder, int position) {
        holder.parktv.setText(park.get(position));
        holder.vehicletypetv.setText(vehicletype.get(position));
        holder.regtv.setText(registrationno.get(position));
        holder.captv.setText(capacity.get(position));
        holder.tasktv.setText(task.get(position));
        holder.coveragetv.setText(coveragearea.get(position));
        holder.deivertv.setText(driverid.get(position));
        holder.coordinatortv.setText(coordinatorid.get(position));
        holder.monitortv.setText(monitorid.get(position));
        holder.statustv.setText(status.get(position));
        holder.attv.setText(assigntime.get(position));
        holder.adtv.setText(assigndate.get(position));
        holder.cdtv.setText(completedate.get(position));
    }

    @Override
    public int getItemCount() {
        return no.size();
    }

    public class GetVehicleViewHolder extends RecyclerView.ViewHolder{
        TextView parktv,vehicletypetv,regtv,captv,tasktv,coveragetv,deivertv,
        coordinatortv,monitortv,statustv,attv,adtv,cdtv;
        public GetVehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            parktv=itemView.findViewById(R.id.gvipark);
            vehicletypetv=itemView.findViewById(R.id.gvivt);
            regtv=itemView.findViewById(R.id.gvireg);
            captv=itemView.findViewById(R.id.gvicap);
            tasktv=itemView.findViewById(R.id.gvitask);
            coveragetv=itemView.findViewById(R.id.gvica);
            deivertv=itemView.findViewById(R.id.gvidi);
            coordinatortv=itemView.findViewById(R.id.gvici);
            monitortv=itemView.findViewById(R.id.gvimi);
            statustv=itemView.findViewById(R.id.gvistatus);
            attv=itemView.findViewById(R.id.gviat);
            adtv=itemView.findViewById(R.id.gviad);
            cdtv=itemView.findViewById(R.id.gvicd);
        }
    }
}
