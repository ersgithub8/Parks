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

public class HomeFragment extends Fragment {
    Button bookticket,contactus,tracklocation,tips;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_home,container,false);
        bookticket=view.findViewById(R.id.btnbthf);
        contactus=view.findViewById(R.id.btncuhf);
        tracklocation=view.findViewById(R.id.btntlhf);
        tips=view.findViewById(R.id.btnthf);
        bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new BookTicket()).addToBackStack("HomeFragment").commit();
            }
        });
        tracklocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new TrackFragment()).addToBackStack("HomeFragment").commit();
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ContactFragment()).addToBackStack("HomeFragment").commit();
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new TipsFragment()).addToBackStack("HomeFragment").commit();
            }
        });
        return view;

    }
}
