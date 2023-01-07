package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookaticket.Model.Model;


public class Login_Fragment extends Fragment {

    EditText mEmail;
    EditText mPassword;
    Button mLoginBtn;
    View msignupBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        mEmail = view.findViewById(R.id.login_email_edit);
        mPassword = view.findViewById(R.id.login_password_edit);
        mLoginBtn = view.findViewById(R.id.login_login_btn);
        msignupBtn = view.findViewById(R.id.login_signup_link);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                Model.instance().loginUser(email,password, (task)-> {
                    if(task.isSuccessful()) {
                        Toast.makeText(getContext(),"Hello " + email,Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view1).navigate(R.id.homePage_Fragment);
                    } else {
                        Toast.makeText(getContext(),"Mail or Password incorrect",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        msignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.signup_Fragment);
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Model.instance().isLogedIn()) {
            Navigation.findNavController(getView()).navigate(R.id.homePage_Fragment);
        }
    }
}