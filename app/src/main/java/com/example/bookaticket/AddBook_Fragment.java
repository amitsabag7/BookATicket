package com.example.bookaticket;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AddBook_Fragment extends Fragment {

    Book book;
    String stationName;

    String stationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        ImageView cover = view.findViewById(R.id.addBook_coverIV);
        TextView name = view.findViewById(R.id.addBook_nameTV);
        TextView author = view.findViewById(R.id.addBook_authorTV);
        TextView year = view.findViewById(R.id.addBook_yearTV);
        TextView description = view.findViewById(R.id.addBook_descriptionTV);
        RatingBar rating = view.findViewById(R.id.addBook_ratingRB);
        EditText comment = view.findViewById(R.id.addBook_newCommentET);
        Button addBook = view.findViewById(R.id.addBook_addBtn);

        stationId = AddBook_FragmentArgs.fromBundle(getArguments()).getStationId();
        stationName = AddBook_FragmentArgs.fromBundle(getArguments()).getStationName();
        BookInfo bookInfo = AddBook_FragmentArgs.fromBundle(getArguments()).getBookInfo();
        name.setText(bookInfo.getTitle());
        author.setText(bookInfo.getAuthor());
        year.setText(bookInfo.getPublishedDate());
        description.setText(bookInfo.getDescription());
        addBook.setText("Add Book to " + stationName + " Station");

//        String newUrlString = bookInfo.getThumbnail().replaceFirst("^http", "https");
//        try{
//            Picasso.get().load(newUrlString).into(cover);
//        }catch (Exception e){
//            System.out.println(e);
//        }

        String newUrlString = bookInfo.getThumbnail().replaceFirst("^http", "https");
        Glide.with(this).load(newUrlString).into(cover);


         addBook.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view1) {

            String username = Model.instance().getUser().getUserName();
            String commentText = comment.getText().toString();
            int userAvatar = 1;
            int ratingValue = (int) rating.getRating();
//            Comment newComment = new Comment(username, ratingValue, commentText, userAvatar);
        //    book = new Book(bookInfo.getTitle(), bookInfo.getThumbnail(), bookInfo.authorsToString(), bookInfo.getPublishedDate(), bookInfo.getDescription(), newComment, true);


//            Model.instance().addBookToStation(book, stationId, newComment, new Model.AddBookToStationListener() {
//                @Override
//                public void onComplete(boolean b) {
//                    Navigation.findNavController(view).navigateUp();
//                }
//
//            });
        }


    });

    return view;
    }
}

