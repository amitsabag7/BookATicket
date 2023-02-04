package com.example.bookaticket.Model;

import com.example.bookaticket.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance= new Model();

    public static Model instance() {
        return _instance;
    }

    private FirebaseModel firebaseModel = new FirebaseModel();

    private Model() {
        List<Comment> comments = new LinkedList<>();
        Comment c1 = new Comment("user1", 5, "amazing", R.drawable.avatar);
        Comment c2 = new Comment("user2", 4, "very good" ,R.drawable.avatar);
        Comment c3 = new Comment("user3", 1, "boring",R.drawable.avatar);
        Comment c4 = new Comment("user4", 4, "nice",R.drawable.avatar);
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);

        addBook(new Book("Harry Potter 1",
                "res/drawable/harry_potter1.png",
                1999,
                "J K Rolling",
                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. Harry makes close friends and a few enemies during his first year at the school and with the help of his friends, Ron Weasley and Hermione Granger, he faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's parents, but failed to kill Harry when he was just 15 months old.", comments));

    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    List<Book> data = new LinkedList<>();

    public List<Book> getAllBooks() {
        return data;
    }

    public void addBook(Book book) {
        data.add(book);
    }

    public void deleteBook(int pos){
        data.remove(pos);
    }

    public void updateBook(int pos, Book book){
        data.set(pos, book);
    }

    public void getBookById(String id, Listener<Book> listener) {
       firebaseModel.getBookById(id, listener);
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
    }

    public boolean isLogedIn() {
        return firebaseModel.isLogedIn();
    }

    public void logoutuser() {
        firebaseModel.logoutuser();
    }

    public void getAllStations(Listener<List<Station>> callback){
        firebaseModel.getAllStations(callback);
    }

    public void getBooksByStationId(String stationId, Model.GetBooksListener listener) {
        firebaseModel.getBooksByStationId(stationId, listener);
    }

    public void getStationById(String stationId, Model.GetStationListener listener) {
        firebaseModel.getStationById(stationId, listener);
    }

    public interface GetBooksListener{
        void onComplete(List<Book> books);
    }

    public interface GetStationListener {
        void onComplete(Station station);
    }

    public interface AddBookToStationListener{
        void onComplete(boolean b);
    }

    public void addBookToStation(Book book, String stationId, Comment newComment, Model.AddBookToStationListener callback) {
        firebaseModel.addBookToStation(book, stationId, newComment, callback);
    }
}