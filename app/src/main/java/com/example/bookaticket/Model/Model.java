package com.example.bookaticket.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance= new Model();

    public static Model instance() {
        return _instance;
    }

    private Model() {
        List<Comment> comments = new LinkedList<>();
        Comment c1 = new Comment("user1", 5, "amazing");
        Comment c2 = new Comment("user2", 4, "very good");
        comments.add(c1);
        comments.add(c2);

        addBook(new Book("Harry Potter 1", "res/drawable/HarryPotter1Cover.png", 1999, "J K Rolling", "magic book bla bla bla", comments));

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