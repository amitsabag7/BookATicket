package com.example.bookaticket.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookaticket.Login_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class FirebaseModel {

    FirebaseAuth mAuth;

    FirebaseModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email, String password, Model.LoginListener callback){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("tag","Login successesful");
                    callback.onComplete(task);
                } else {
                    callback.onComplete(task);
                }
            }
        });

    }
    public void signupUser(String username, String email, String password, Model.SignupListener callback) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("tag","Signup successesful");
                    callback.onComplete(task);
                } else {
                    callback.onComplete(task);
                }
            }
        });
    }




}
