package com.example.bookaticket.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FirebaseModel {

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    FirebaseModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
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

    public boolean isLogedIn () {
        if(mAuth.getCurrentUser() != null) {
            return true;
        }
        return false;
    }

    public void logoutuser() {
       if(isLogedIn()) {
          mAuth.signOut();
       }
    }

    public void getBooksByStationId(String stationId, Model.GetBooksListener listener) {
        db.collection("books").whereEqualTo("stationId",stationId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Book> books = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Book book = document.toObject(Book.class);
                        books.add(book);
                    }
                    listener.onComplete(books);
                } else {
                    listener.onComplete(null);
                }
            }
        });
    }

    public void getStationById(String stationId, Model.GetStationListener listener) {
        db.collection("stations").document(stationId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    Station station = document.toObject(Station.class);
                    listener.onComplete(station);
                } else {
                    listener.onComplete(null);
                }
            }
        });
    }
}