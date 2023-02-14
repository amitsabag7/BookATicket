package com.example.bookaticket.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bookaticket.Login_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

public class FirebaseModel {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
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

    public void saveUser(String username, String email) {
        User user = new User(username,"", email, "");
        Map<String, Object> json = user.toJson();
        db.collection("users").document(email).set(json).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("tag","The user "+username+" saved in FireBase");
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

    public String getCurentUserEmail() {
        if(mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }

        return "";
    }

    public void getAllStationsSince(Long since, Model.Listener<List<Station>> callback){
        db.collection("stations").whereGreaterThanOrEqualTo(Station.LAST_UPDATED, new Timestamp(since,0))
                                             .get()
                                             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Station> list = new LinkedList<>();
                if (task.isSuccessful()){
                    Log.d("TAG","successful");
                    QuerySnapshot jsonsList = task.getResult();
                                                                                                                                                                                          for (DocumentSnapshot json: jsonsList){
                        Station st = Station.fromJson(json.getData());
                        list.add(st);
                    }
                }
                callback.onComplete(list);
            }
        });
    }

    public void getBookById(String id, Model.Listener<Book> listener) {
        db.collection("books").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    Book book = document.toObject(Book.class);
                    listener.onComplete(book);
                } else {
                    listener.onComplete(null);
                }
            }
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Model.UploadImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("profileImages/" + name + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

    public void getAllBookInstancesByStationIDSince(String stationID, Long since, Model.Listener<List<BookInstance>> callback) {
        db.collection("bookInstance")
                .whereEqualTo("stationID", stationID)
//                .whereEqualTo("book_instance_local_last_update", new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<BookInstance> list = new LinkedList<>();
                if (task.isSuccessful()){
                    Log.d("TAG"," found " + list.size() + "bookInstances successful for stationID: " + stationID);
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList){
                        BookInstance bi = BookInstance.fromJson(json.getData());
                        list.add(bi);
                    }
                }
                else {

                }
                callback.onComplete(list);
            }
        });
    }

    public void takeBookFromStation(String bookInstanceID, String userEmail){
        Map<String,Object> updateMap = new HashMap<>();
        updateMap.put("stationId", "");
        updateMap.put("userEmail", userEmail);
        db.collection("bookInstance")
                .document(bookInstanceID)
                .update(updateMap);
        System.out.println("tried to update book with id " + bookInstanceID);
    }

    public void updateUserDetails(User user) {
        Map<String,Object> updateMap = user.toJson();
        db.collection("users")
                .document(user.getEmail()).update(updateMap);
    }

    public void getBookInfoByIDSince(String ID, Long since, Model.Listener<BookInfo> callback) {
        db.collection("bookInfo")
                .whereEqualTo("id", ID)
//                .whereEqualTo("book_info_local_last_update", new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        BookInfo bookInfo = new BookInfo();
                        if (task.isSuccessful()){
                            Log.d("TAG"," found " + bookInfo.getTitle() + "bookInfo successful for ID: " + ID);
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                bookInfo = BookInfo.fromJson(json.getData());
                            }
//                            DocumentSnapshot json = task.getResult().getDocuments().get(0);

                        }
                        else {

                        }
                        callback.onComplete(bookInfo);
                    }
                });
    }

    public void getUserByEmail(String userEmail, Model.Listener<User> callback) {
        db.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        User user = new User();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                user = User.fromJson(json.getData());
                                Log.d("TAG","Amit"+user.getUserName()+user.getProfileImg()+user.getHomeTown());
                            }

                        }
                        else {
                            Log.d("failfire","failfire");

                        }
                        callback.onComplete(user);
                    }
                });
    }
//    public void getAllBookInstancesByStationID(String stationID, Long since, Model.Listener<List<BookInstance>> callback) {
//        db.collection("bookInstance")
//                .whereEqualTo("stationID", stationID)
////                .whereEqualTo("book_instance_local_last_update", new Timestamp(since, 0))
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<BookInstance> list = new LinkedList<>();
//                        if (task.isSuccessful()){
//                            Log.d("TAG"," found " + list.size() + "bookInstances successful for stationID: " + stationID);
//                            QuerySnapshot jsonsList = task.getResult();
//                            for (DocumentSnapshot json: jsonsList){
//                                BookInstance st = BookInstance.fromJson(json.getData());
//                                list.add(st);
//                            }
//                        }
//                        else {
//
//                        }
//                        callback.onComplete(list);
//                    }
//                });
//    }
}