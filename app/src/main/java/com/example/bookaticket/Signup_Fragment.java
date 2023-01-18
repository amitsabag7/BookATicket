package com.example.bookaticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookaticket.Model.Model;

public class Signup_Fragment extends Fragment {

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    Button mSignupBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.main_menu_private);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_, container, false);
        mUsername = view.findViewById(R.id.signup_username_edit);
        mEmail = view.findViewById(R.id.signup_email_edit);
        mPassword = view.findViewById(R.id.signup_password_edit);
        mSignupBtn = view.findViewById(R.id.signup_signup_btn);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    mUsername.setError("Username is required");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6) {
                    mPassword.setError("Password ust be >= 6 characters");
                    return;
                }

                //TODO :progress bar
                Model.instance().signupUser(username,email,password, (task)-> {
                    if(task.isSuccessful()) {
                        Toast.makeText(getContext(),"Signup successfully",Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.login_Fragment);
                    } else {
                        Toast.makeText(getContext(),"This email already exist",Toast.LENGTH_LONG).show();
                    }

                });


            }
        });
        return view;
    }
}