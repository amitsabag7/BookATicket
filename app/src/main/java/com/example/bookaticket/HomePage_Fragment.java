package com.example.bookaticket;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookaticket.Model.Model;

public class HomePage_Fragment extends Fragment {

    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_, container, false);
        logout = view.findViewById(R.id.logut_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance().logoutuser();
                Navigation.findNavController(view).navigate(R.id.login_Fragment);
            }
        });
        return view;
    }
}