package com.example.bookticket.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookticket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GetAssignmentAdapter extends RecyclerView.Adapter<GetAssignmentAdapter.GetAssignmentViewHolder> {

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
    ArrayList<String> status=new ArrayList<String>();
    ArrayList<String> assigndate=new ArrayList<String>();
    ArrayList<String> completedate=new ArrayList<String>();
    ArrayList<String> image=new ArrayList<String>();

    public GetAssignmentAdapter(ArrayList<String> no, ArrayList<String> eid, ArrayList<String> ename,
                                ArrayList<String> monitorname, ArrayList<String> monitorid,
                                ArrayList<String> pid, ArrayList<String> pname, ArrayList<String> pcatrgory,
                                ArrayList<String> task, ArrayList<String> site,
                                ArrayList<String> status, ArrayList<String> assigndate,
                                ArrayList<String> completedate, ArrayList<String> image) {
        this.no = no;
        this.eid = eid;
        this.ename = ename;
        this.monitorname = monitorname;
        this.monitorid = monitorid;
        this.pid = pid;
        this.pname = pname;
        this.pcatrgory = pcatrgory;
        this.task = task;
        this.site = site;
        this.status = status;
        this.assigndate = assigndate;
        this.completedate = completedate;
        this.image = image;
    }

    @NonNull
    @Override
    public GetAssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.assignmentsitems,parent,false);
        return new GetAssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAssignmentViewHolder holder, int position) {
        holder.eidtv.setText(eid.get(position));
        holder.enametv.setText(ename.get(position));
        holder.monitornametv.setText(monitorname.get(position));
        holder.monidtv.setText(monitorid.get(position));
        holder.pidtv.setText(pid.get(position));
        holder.pnametv.setText(pname.get(position));
        holder.pcattv.setText(pcatrgory.get(position));
        holder.tasktv.setText(task.get(position));
        holder.sitetv.setText(site.get(position));
        holder.statustv.setText(status.get(position));
        holder.adtv.setText(assigndate.get(position));
        holder.cdtv.setText(completedate.get(position));
    }

    @Override
    public int getItemCount() {
        return no.size();
    }

    public class GetAssignmentViewHolder extends RecyclerView.ViewHolder{
        TextView eidtv,enametv,monitornametv,monidtv,pidtv,pnametv,pcattv,
                tasktv,sitetv,statustv,adtv,cdtv;
        public GetAssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            eidtv=itemView.findViewById(R.id.aiid);
            enametv=itemView.findViewById(R.id.ainame);
            monitornametv=itemView.findViewById(R.id.aimon);
            monidtv=itemView.findViewById(R.id.aimonid);
            pidtv=itemView.findViewById(R.id.aipid);
            pnametv=itemView.findViewById(R.id.aipname);
            pcattv=itemView.findViewById(R.id.aipcat);
            tasktv=itemView.findViewById(R.id.aitask);
            sitetv=itemView.findViewById(R.id.aisite);
            statustv=itemView.findViewById(R.id.aistatus);
            adtv=itemView.findViewById(R.id.aiad);
            cdtv=itemView.findViewById(R.id.aicd);
        }
    }
}


