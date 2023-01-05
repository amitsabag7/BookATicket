package com.example.bookaticket.Model;

import com.example.bookaticket.R;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance= new Model();

    public static Model instance() {
        return _instance;
    }

    private Model() {
        List<Comment> comments = new LinkedList<>();
        Comment c1 = new Comment("user1", 5, "amazing", R.drawable.avatar);
        Comment c2 = new Comment("user2", 4, "very good" ,R.drawable.avatar);
        Comment c3 = new Comment("user3", 1, "boring",R.drawable.avatar);
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);

        addBook(new Book("Harry Potter 1",
                "res/drawable/harry_potter1.png",
                1999,
                "J K Rolling",
                "Harry Potter and the Philosopher's Stone is a 1997 fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. Harry makes close friends and a few enemies during his first year at the school and with the help of his friends, Ron Weasley and Hermione Granger, he faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's parents, but failed to kill Harry when he was just 15 months old.", comments));

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
}