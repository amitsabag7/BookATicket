package com.example.bookaticket.Model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance= new Model();

    public static Model instance() {
        return _instance;
    }

    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDB.getAppDb();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());


    private Model() {
        User user1 = new User("aml eisami","daliyat al carmel",
                "amleisami2@gmail.com","gs://bookaticket-6ce0a.appspot.com/profileImages/amleisami2@gmail.com.jpg");

        users.add(user1);
    }




    public interface Listener<T>{
        void onComplete(T data);
    }

    List<Book> data = new LinkedList<>();
    List<User> users = new ArrayList<>();

    User user = new User("aml eisami","daliyat al carmel",
            "amleisami2@gmail.com","gs://bookaticket-6ce0a.appspot.com/profileImages/amleisami2@gmail.com.jpg");

//    public void updateProfileImage(String email,String profileImg){
//        for (int user = 0; user < users.size(); user++) {
//            if (users.get(user).email == email){
//                users.get(user).setProfileImg(profileImg);
//            }
//        }
//    }

    public User getUser(){
        return user;
    }

    public List<User> getUsers(){
        return users;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getAllBooks() {
        return data;
    }


    public interface LoginListener{
        void onComplete(Task<AuthResult> task);
    }

    public void loginUser(String email, String password, Model.LoginListener callback){
        firebaseModel.loginUser(email,password,callback);
    }

    public interface SignupListener{
        void onComplete(Task<AuthResult> task);
    }

    public void signupUser(String username, String email, String password, Model.SignupListener callback) {
        firebaseModel.signupUser(username,email,password,callback);
        firebaseModel.saveUser(username,email);
    }


    public boolean isLogedIn() {
        return firebaseModel.isLogedIn();
    }

    public void logoutuser() {
        firebaseModel.logoutuser();
    }

    public String getCurentUserEmail() {
       return firebaseModel.getCurentUserEmail();
    }

    public void getAllStations(Listener<List<Station>> callback){
//        executor.execute(()->{
//            List<Station> complete = localDb.stationDao().getAll();
//            Log.d("TAG",complete.toString());
//            mainHandler.post(()->{
//                callback.onComplete(complete);
//            });
//        });


        Long localLastUpdate = Station.getLocalLastUpdate();
        Log.d("tag","local"+localLastUpdate.toString());


       firebaseModel.getAllStationsSince(localLastUpdate,list-> {
           executor.execute(()->{
               Log.d("tag","firebasr return "+list.size() );
               Long time = localLastUpdate;
               for(Station st: list) {
                   localDb.stationDao().insertAll(st);
                   if(time > st.getLastUpdated()) {
                       time = st.getLastUpdated();
                   }
               }
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {

               }
               Station.setLocalLastUpdate(time);
              List<Station> complete = localDb.stationDao().getAll();
                   Log.d("TAG",complete.toString());
               mainHandler.post(()->{
                   callback.onComplete(complete);
               });
           });
       });
    }

    public interface UploadImageListener{
        void onComplete(String url);
    }

    public void uploadImage(String name, Bitmap bitmap,UploadImageListener listener){
        firebaseModel.uploadImage(name,bitmap,listener);
    }


    public void takeBookFromStation(String bookInstanceID, String userEmail){
        firebaseModel.takeBookFromStation(bookInstanceID, userEmail);
        executor.execute(() -> {
            localDb.bookInstanceDao().takeBookFromStation(bookInstanceID, userEmail);
        });
    }

    public void updateUserDetails(User user) {
        firebaseModel.updateUserDetails(user);
    }

    public void getAllBookInstancesByStationID(String stationId, Listener<List<BookInstance>> callback) {
        Long localLastUpdate = BookInstance.getLocalLastUpdated();

        firebaseModel.getAllBookInstancesByStationIDSince(stationId, localLastUpdate, list -> {
            if (list != null) {
                executor.execute(() -> {
                    Log.d("tag","firebase return "+list.size() );
                    Long time = localLastUpdate;
                    for (BookInstance bookInstance:list) {
                        localDb.bookInstanceDao().insertAll(bookInstance);
                        if (time < bookInstance.getLastUpdated()) {
                            time = bookInstance.getLastUpdated();
                        }
                    }
                    BookInstance.setLocalLastUpdated(time);
                    // needs to only find specific books
                    List<BookInstance> complete = localDb.bookInstanceDao().getBookInstanceByStationID(stationId);
                    Log.d("TAG", "Local book Instances:" + complete.size());
                    mainHandler.post(() -> {
                        callback.onComplete(complete);
                    });
                });
            }
        });
    }

    public void getAllBookInstancesByUserEmail(String userEmail, Listener<List<BookInstance>> callback) {
        Long localLastUpdate = BookInstance.getLocalLastUpdated();

        firebaseModel.getAllBookInstancesUserEmailSince(userEmail, localLastUpdate, list -> {

            executor.execute(() -> {
                Log.d("tag","firebase return "+list.size() );
                Long time = localLastUpdate;
                for (BookInstance bookInstance:list) {
                    localDb.bookInstanceDao().insertAll(bookInstance);
                    if (time < bookInstance.getLastUpdated()) {
                        time = bookInstance.getLastUpdated();
                    }
                }
                BookInstance.setLocalLastUpdated(time);
                // needs to only find specific books
                List<BookInstance> complete = localDb.bookInstanceDao().getBookInstanceByUserEmail(userEmail);
                mainHandler.post(() -> {
                    callback.onComplete(complete);
                });
            });
        });
    }


    public void getUserByEmail(String userEmail, Model.Listener<User> callback) {
        firebaseModel.getUserByEmail(userEmail, callback);
    }
    public void getBookInfoByID(String bookInfoID, Listener<BookInfo> callback) {
        Long localLastUpdate = BookInfo.getLocalLastUpdated();

        firebaseModel.getBookInfoByIDSince(bookInfoID, localLastUpdate, bookInfo -> {
            executor.execute(() -> {
                Log.d("tag","firebase return "+ bookInfo.getTitle() );
                Long time = localLastUpdate;
                localDb.bookInfoDao().insertAll(bookInfo);
                if ((bookInfo.getLastUpdated() != null) && (time < bookInfo.getLastUpdated())) {
                    time = bookInfo.getLastUpdated();
                }
                BookInfo.setLocalLastUpdated(time);

                BookInfo complete = localDb.bookInfoDao().getBookInfoByID(bookInfoID);
                mainHandler.post(() -> {
                    callback.onComplete(complete);
                });
            });
        });
    }


    public void getAllCommentsByBookInfoID(String bookInfoId, Listener<List<Comment>> callback) {
        Long localLastUpdate = Comment.getLocalLastUpdated();

        firebaseModel.getAllCommentsByBookInfoID(bookInfoId, localLastUpdate, list -> {

            executor.execute(() -> {
                Log.d("tag","firebase return "+list.size() + "comments");
                Long time = localLastUpdate;
                for (Comment comment:list) {
                    localDb.commentsDao().insertAll(comment);
                    if (time < comment.getLastUpdated()) {
                        time = comment.getLastUpdated();
                    }
                }
                Comment.setLocalLastUpdated(time);
                List<Comment> complete = localDb.commentsDao().getCommentsBybookInfoID(bookInfoId);
                mainHandler.post(() -> {
                    callback.onComplete(complete);
                });
            });
        });
    }

    public void getAllBookInfosByStationID(String stationId, Listener<List<BookInfo>> callback) {
        firebaseModel.getAllBookInfosByStationID(stationId, callback);
    }

    public void addBookInfo(BookInfo bookInfo, Listener<String> callback) {
        firebaseModel.addBookInfo(bookInfo, callback);
    }

    public void addBookInstanceToStation(BookInstance newBookInstance, Listener<Boolean> callback) {
        firebaseModel.addBookInstanceToStation(newBookInstance, callback);
    }

    public void addNewComment(Comment newComment, Listener<Boolean> booleanListener) {
        firebaseModel.addNewComment(newComment, booleanListener);
    }

}