package model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookaticket.Login_Fragment;
import com.example.bookaticket.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class FirebaseModel {

    FirebaseAuth mAuth;

    FirebaseModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email, String password,Model.LoginListener callback){
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
