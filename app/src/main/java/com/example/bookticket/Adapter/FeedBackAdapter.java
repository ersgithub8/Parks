package com.example.bookticket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.bookticket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.FeedBackViewHolder> {

    ArrayList<String> description=new ArrayList<String>();
    ArrayList<String> time=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();

    public FeedBackAdapter(ArrayList<String> description, ArrayList<String> time, ArrayList<String> date) {
        this.description = description;
        this.time = time;
        this.date = date;
    }

    @NonNull
    @Override
    public FeedBackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.userfeedbackitems,parent,false);
        return new FeedBackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedBackViewHolder holder, int position) {
    String description1=description.get(position);
    String date1=date.get(position);
    String time1=time.get(position);


    holder.descriptiontv.setText(description1);
    holder.datetv.setText(date1);
    holder.timetv.setText(time1);
    }

    @Override
    public int getItemCount() {
        return description.size();
    }

    public class FeedBackViewHolder extends RecyclerView.ViewHolder{
        TextView descriptiontv,datetv,timetv;
        public FeedBackViewHolder(@NonNull View itemView) {
            super(itemView);

            descriptiontv=itemView.findViewById(R.id.ufidetails);
            datetv=itemView.findViewById(R.id.ufidate);
            timetv=itemView.findViewById(R.id.ufitime);
        }
    }
}
