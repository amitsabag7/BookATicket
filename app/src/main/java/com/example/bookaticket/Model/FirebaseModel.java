package com.example.bookaticket.Model;

import android.util.Log;

import androidx.annotation.NonNull;

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
                }
            }
        });

    }




}
