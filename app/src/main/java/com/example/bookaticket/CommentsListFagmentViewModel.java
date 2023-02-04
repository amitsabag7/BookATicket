package com.example.bookaticket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class CommentsListFagmentViewModel extends ViewModel {
    List<Book> books = Model.instance().getAllBooks();
    Book book1 = books.get(0);

    private List<Comment> data = book1.comments;

    public List<Comment> getData(String userName){
        List<Comment> myComments = new ArrayList<Comment>();

        for (int comment = 0; comment < data.size(); comment++){
            if ((data.get(comment).user).equals(userName)){
                myComments.add(data.get(comment));
            }
        }
        return myComments;
    }
}
