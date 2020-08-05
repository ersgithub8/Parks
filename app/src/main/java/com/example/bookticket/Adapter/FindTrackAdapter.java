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

public class FindTrackAdapter extends RecyclerView.Adapter<FindTrackAdapter.FindTrackViewHolder> {

    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> path=new ArrayList<String>();

    Context context;
    public FindTrackAdapter(Context context,ArrayList<String> title,ArrayList<String> path)
    {
        this.context=context;
        this.path=path;
            this.title = title;
    }

    @NonNull
    @Override
    public FindTrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.pasrkstracks,parent,false);
        return new FindTrackAdapter.FindTrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindTrackViewHolder holder, final int position) {
        final String titles=title.get(position);
        holder.titletv.setText(titles);

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
        return title.size();
    }

    public class FindTrackViewHolder extends RecyclerView.ViewHolder{
        TextView titletv;
        public FindTrackViewHolder(@NonNull View itemView) {
            super(itemView);
        titletv=itemView.findViewById(R.id.title);
        }
    }

}
