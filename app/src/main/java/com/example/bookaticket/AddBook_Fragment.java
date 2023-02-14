package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;

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
        String bookInstanceId = AddBook_FragmentArgs.fromBundle(getArguments()).getBookInstanceId();
        name.setText(bookInfo.getTitle());
        author.setText(bookInfo.getAuthor());
        year.setText(bookInfo.getPublishedDate());
        description.setText(bookInfo.getDescription());
        addBook.setText("Add Book to " + stationName + " Station");


//        String newUrlString = bookInfo.getThumbnail().replaceFirst("^http:", "https:");
        Glide.with(this).load(bookInfo.getThumbnail()).into(cover);


         addBook.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view1) {

            String username = Model.instance().getUser().getUserName();
            String commentText = comment.getText().toString();
            String userEmail = Model.instance().getCurentUserEmail();
            int ratingValue = (int) rating.getRating();

            if (bookInstanceId != "") {
                Model.instance().returnBookInstanceToStation(bookInstanceId, stationId, new Model.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean bookReturned) {
                        if (bookReturned) {
                            Comment newComment = new Comment(bookInfo.getId(), userEmail, ratingValue, commentText);
                            Model.instance().addNewComment(newComment, new Model.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean commentAdded) {
                                    if (!commentAdded) {
                                        Toast.makeText(getContext(), "Could not save new comment", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Toast.makeText(getContext(), "Book returned to " + stationName + " station", Toast.LENGTH_SHORT).show();
                            AddBook_FragmentDirections.ActionAddBookFragmentToStationBookListFragment action =
                                    AddBook_FragmentDirections.actionAddBookFragmentToStationBookListFragment(stationId, stationName);
                            Navigation.findNavController(view).navigate(action);
                        } else {
                            Toast.makeText(getContext(), "Could not return book to " + stationName + " station", Toast.LENGTH_SHORT).show();
                        }
                    }


                });


            } else {
                Model.instance().addBookInfo(bookInfo, new Model.Listener<String>() {
                    @Override
                    public void onComplete(String bookInfoID) {
                        if (bookInfoID != null) {
                            Comment newComment = new Comment(bookInfoID, userEmail, ratingValue, commentText);
                            Model.instance().addNewComment(newComment, new Model.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean commentAdded) {
                                    if (!commentAdded) {
                                        Toast.makeText(getContext(), "Could not save new comment", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            BookInstance newBookInstance = new BookInstance(bookInfoID, stationId, "");
                            Model.instance().addBookInstanceToStation(newBookInstance, new Model.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean bookInstanceAdded) {
                                    if (!bookInstanceAdded) {
                                        Toast.makeText(getContext(), "Could not add this book to " + stationName, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Book was added to " + stationName, Toast.LENGTH_SHORT).show();
                                        AddBook_FragmentDirections.ActionAddBookFragmentToStationBookListFragment action =
                                                AddBook_FragmentDirections.actionAddBookFragmentToStationBookListFragment(stationId, stationName);
                                        Navigation.findNavController(view).navigate(action);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getContext(), "Could not add this book to " + stationName, Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Error saving new book info or retrieving existing one by info link");
                        }
                    }
                });
            }
        }
    });

    return view;
    }
}

