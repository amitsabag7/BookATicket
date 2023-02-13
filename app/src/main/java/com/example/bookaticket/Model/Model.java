package com.example.bookaticket.Model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.example.bookaticket.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.io.Console;
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
//        List<Comment> comments = new LinkedList<>();
//        Comment c1 = new Comment("aml eisami", 1, "ya ya ya", R.drawable.avatar);
//        Comment c2 = new Comment("user2", 4, "very good" ,R.drawable.avatar);
//        Comment c3 = new Comment("user3", 1, "boring",R.drawable.avatar);
//        Comment c4 = new Comment("user4", 4, "nice",R.drawable.avatar);
//        Comment c5 = new Comment("aml eisami", 5, "amazing aml", R.drawable.avatar);
//        Comment c6 = new Comment("aml eisami", 3, "very good aml" ,R.drawable.avatar);
//        Comment c7 = new Comment("user1", 5, "amazing", R.drawable.avatar);
//        comments.add(c1);
//        comments.add(c2);
//        comments.add(c3);
//        comments.add(c4);
//        comments.add(c5);
//        comments.add(c6);
//        comments.add(c7);

//        addBook(new Book("Harry Potter 1",
//                "res/drawable/harry_potter1.png",
//                1999,
//                "J K Rolling",
//                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written " +
//                        "by British author J. K. Rowling. The first novel in the Harry Potter series" +
//                        " and Rowling's debut novel, it follows Harry Potter, a young wizard who " +
//                        "discovers his magical heritage on his eleventh birthday, when he receives" +
//                        " a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. " +
//                        "Harry makes close friends and a few enemies during his first year at the " +
//                        "school and with the help of his friends, Ron Weasley and Hermione Granger," +
//                        " he faces an attempted comeback by the dark wizard Lord Voldemort, who " +
//                        "killed Harry's parents, but failed to kill Harry when he was just 15 months old."
//                , comments, false));
//
//        addBook(new Book("Harry Potter 2",
//                "res/drawable/harry_potter1.png",
//                1999,
//                "J K Rolling",
//                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written " +
//                        "by British author J. K. Rowling. The first novel in the Harry Potter series" +
//                        " and Rowling's debut novel, it follows Harry Potter, a young wizard who " +
//                        "discovers his magical heritage on his eleventh birthday, when he receives" +
//                        " a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. " +
//                        "Harry makes close friends and a few enemies during his first year at the " +
//                        "school and with the help of his friends, Ron Weasley and Hermione Granger," +
//                        " he faces an attempted comeback by the dark wizard Lord Voldemort, who " +
//                        "killed Harry's parents, but failed to kill Harry when he was just 15 months old."
//                , comments, true));
//
//        addBook(new Book("Harry Potter 3",
//                "res/drawable/harry_potter1.png",
//                1999,
//                "J K Rolling",
//                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written " +
//                        "by British author J. K. Rowling. The first novel in the Harry Potter series" +
//                        " and Rowling's debut novel, it follows Harry Potter, a young wizard who " +
//                        "discovers his magical heritage on his eleventh birthday, when he receives" +
//                        " a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. " +
//                        "Harry makes close friends and a few enemies during his first year at the " +
//                        "school and with the help of his friends, Ron Weasley and Hermione Granger," +
//                        " he faces an attempted comeback by the dark wizard Lord Voldemort, who " +
//                        "killed Harry's parents, but failed to kill Harry when he was just 15 months old."
//                , comments, true));
//
//        addBook(new Book("Harry Potter 4",
//                "res/drawable/harry_potter1.png",
//                1999,
//                "J K Rolling",
//                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written " +
//                        "by British author J. K. Rowling. The first novel in the Harry Potter series" +
//                        " and Rowling's debut novel, it follows Harry Potter, a young wizard who " +
//                        "discovers his magical heritage on his eleventh birthday, when he receives" +
//                        " a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. " +
//                        "Harry makes close friends and a few enemies during his first year at the " +
//                        "school and with the help of his friends, Ron Weasley and Hermione Granger," +
//                        " he faces an attempted comeback by the dark wizard Lord Voldemort, who " +
//                        "killed Harry's parents, but failed to kill Harry when he was just 15 months old."
//                , comments, false));
//
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

    public void updateProfileImage(String email,String profileImg){
        for (int user = 0; user < users.size(); user++) {
            if (users.get(user).email == email){
                users.get(user).setProfileImg(profileImg);
            }
        }
    }

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

        Long localLastUpdate = Station.getLocalLastUpdate();
        Log.d("tag","local"+localLastUpdate.toString());


       firebaseModel.getAllStationsSince(localLastUpdate,list-> {
           executor.execute(()->{
               Log.d("tag","firebasr return "+list.size() );
               Long time = localLastUpdate;
               for(Station st: list) {
                   localDb.stationDao().insertAll(st);
                   if(time <= st.getLastUpdated()) {
                       time = st.getLastUpdated();
                   }
               }
               Station.setLocalLastUpdate(time);
              List<Station> complete = localDb.stationDao().getAll();
               mainHandler.post(()->{
                   callback.onComplete(complete);
                   Log.d("TAG",complete.toString());

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
    }

    public void getAllBookInstancesByStationID(String stationId, Listener<List<BookInstance>> callback) {
        Long localLastUpdate = BookInstance.getLocalLastUpdated();

        firebaseModel.getAllBookInstancesByStationIDSince(stationId, localLastUpdate, list -> {

            //firebaseModel.getAllBookInstancesByStationIDSince(stationId, localLastUpdate, list -> {
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
                mainHandler.post(() -> {
                    callback.onComplete(complete);
                });
            });
        });
    }

    public void getAllBookInstancesByUserEmail(String userEmail, Listener<List<BookInstance>> callback) {
//        firebaseModel.getAllBookInstancesByStationID(userEmail, callback);
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
}