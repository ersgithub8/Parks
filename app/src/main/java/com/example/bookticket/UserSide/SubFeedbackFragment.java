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

public class SubFeedbackFragment extends Fragment {
    Button addfeedback,viewfeedback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_subfeedback,container,false);
        addfeedback=(Button)view.findViewById(R.id.btnafsff);
        viewfeedback=(Button)view.findViewById(R.id.btnvrsff);

        addfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new NewFeedbackFragment()).addToBackStack("SubFeedbackFragment").commit();
            }
        });
        viewfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ViewuserfeedbackFragment()).addToBackStack("SubFeedbackFragment").commit();
            }
        });
        return view;
    }
}
