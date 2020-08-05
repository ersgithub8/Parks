package com.example.bookticket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookticket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrackParkAdapter extends RecyclerView.Adapter<TrackParkAdapter.TrackParkViewHolder> {
    ArrayList<String> number=new ArrayList<String>();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();
    ArrayList<String> area=new ArrayList<String>();
    ArrayList<String> ownership=new ArrayList<String>();
    ArrayList<String> description=new ArrayList<String>();
    ArrayList<String> path=new ArrayList<String>();
    Context context;
    public TrackParkAdapter(Context context,ArrayList<String> number, ArrayList<String> name, ArrayList<String> location, ArrayList<String> area,
                            ArrayList<String> ownership, ArrayList<String> description,ArrayList<String> path) {
        this.number = number;
        this.context=context;
        this.name = name;
        this.location = location;
        this.area = area;
        this.ownership = ownership;
        this.description = description;
        this.path=path;
    }

    @NonNull
    @Override
    public TrackParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.trackparkitems,parent,false);
        return new TrackParkAdapter.TrackParkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackParkViewHolder holder, final int position) {
        String number1=number.get(position);
        String name1=name.get(position);
        String location1=location.get(position);
        String area1=area.get(position);
        String ownership1=ownership.get(position);
        String description1=description.get(position);




        holder.nametv.setText(name1);
        holder.locationtv.setText(location1);
        holder.areatv.setText(area1);
        holder.ownershiptv.setText(ownership1);
        holder.descriptiontv.setText(description1);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(path.get(position)));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return number.size();
    }

    public class  TrackParkViewHolder extends RecyclerView.ViewHolder{
        TextView nametv,locationtv,areatv,ownershiptv,descriptiontv;
        public TrackParkViewHolder(@NonNull View itemView) {
            super(itemView);

            nametv=itemView.findViewById(R.id.tpiname);
            locationtv=itemView.findViewById(R.id.tpilocation);
            areatv=itemView.findViewById(R.id.tpiarea);
            ownershiptv=itemView.findViewById(R.id.tpiownership);
            descriptiontv=itemView.findViewById(R.id.tpidescription);

        }
    }
}
