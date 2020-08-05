package com.example.bookticket.UserSide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookticket.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrackFragment extends Fragment {
    Button trackpark,findtrack,traficfollow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_track,container,false);
        trackpark=(Button)view.findViewById(R.id.btntptf);
        findtrack=(Button)view.findViewById(R.id.btnfttf);

        findtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new FindTrackFragment()).addToBackStack("TrackFragment").commit();

            }
        });
        trackpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new TrackParkFragment()).addToBackStack("TrackFragment").commit();
            }
        });

        return view;
    }
}
