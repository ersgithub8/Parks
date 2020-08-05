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

public class ContactFragment extends Fragment {
    Button faq,feedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_feedback,container,false);
        faq=view.findViewById(R.id.btnfaqff);
        feedback=view.findViewById(R.id.btnfbff);

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new FaqFragment()).addToBackStack("ContactFragment").commit();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new SubFeedbackFragment()).addToBackStack("ContactFragment").commit();
            }
        });
        return view;
    }
}
