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

public class BookTicket extends Fragment {
    Button bookticket,cancelbooking,viewrecord,viewcrowd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_book_ticket,container,false);
        bookticket=(Button) view.findViewById(R.id.btnbtbt);
        cancelbooking=(Button) view.findViewById(R.id.btncbbt);
        viewrecord=(Button) view.findViewById(R.id.btnvrbt);
        viewcrowd=(Button) view.findViewById(R.id.btnvcbt);


        bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new BookingFragment()).addToBackStack("BookTicket").commit();
            }
        });
        cancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new CancelBookingFragment()).addToBackStack("BookTicket").commit();
            }
        });
        viewrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ViewRecordBookingFragment()).addToBackStack("BookTicket").commit();
            }
        });
        viewcrowd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ViewCrowdbookingFragment()).addToBackStack("BookTicket").commit();
            }
        });
        return view;
    }
}
