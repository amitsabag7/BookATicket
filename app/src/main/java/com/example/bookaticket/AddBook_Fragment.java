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

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;

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

        stationId = StationBookList_FragmentArgs.fromBundle(getArguments()).getId();
        stationName = AddBook_FragmentArgs.fromBundle(getArguments()).getStationName();
        book = AddBook_FragmentArgs.fromBundle(getArguments()).getBook();

        try {
            Drawable coverImg = Drawable.createFromStream((InputStream) new URL(book.imgPath).getContent(), "src");
            cover.setImageDrawable(coverImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        name.setText(book.name);
        author.setText(book.writer);
        year.setText(String.valueOf(book.year));
        description.setText(book.description);
        addBook.setText("Add Book to" + stationName);
        /**
         addBook.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view1) {
        Comment newComment = new Comment("cAdar", comment.getText().toString(), rating.getRating());


        Model.instance().addBookToStation(book, stationId, newComment, new Model.AddBookToStationListener() {
        @Override public void onComplete(boolean b) {
        Navigation.findNavController(view).navigateUp();
        }
        });
        }
        });
         */
        return view;
    }
}
