package com.example.bookaticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ExpendedBook_Fragment extends Fragment {
    private List<Comment> comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expended_book, container, false);
        BookInfo bookInfo = ExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInfo();
        String bookInstanceID = ExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInstanceId();
        TextView name = view.findViewById(R.id.expandedBook_name_tv);
        ImageView cover = view.findViewById(R.id.expandedBook_cover_img);
        TextView writer = view.findViewById(R.id.expandedBook_writer_tv);
        TextView publishedDate = view.findViewById(R.id.expandedBook_year_tv);
        TextView description = view.findViewById(R.id.expandedBook_description_tv);
        Button takeBook = view.findViewById(R.id.expandedBook_take_btn);

        takeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUserEmail = Model.instance().getCurentUserEmail();
                if (currentUserEmail != "") {
                    Model.instance().takeBookFromStation(bookInstanceID, currentUserEmail, new Model.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean bookTaken) {
                            if (bookTaken) {
                                Toast.makeText(getContext(), "Book Taken", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(v).navigateUp();
                            } else {
                                Toast.makeText(getContext(), "Book Not Taken", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "you mast be logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecyclerView commentsView = view.findViewById(R.id.expandedBook_comments_rl);

        commentsView.setHasFixedSize(true);
        commentsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CommentRecyclerAdapter commentAdapter = new CommentRecyclerAdapter();
        commentsView.setAdapter(commentAdapter);

        Model.instance().getAllCommentsByBookInfoID(bookInfo.getId(), (list) -> {
            if (list != null) {
                comments = list;
                commentAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
            }
        });

        name.setText(bookInfo.getTitle());
        Picasso.get().load(bookInfo.getThumbnail()).into(cover);
        writer.setText(bookInfo.getAuthor());
        publishedDate.setText(bookInfo.getPublishedDate());
        description.setText(bookInfo.getDescription());

        return view;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{
            TextView userName;
            ImageView avatar;
//            ImageView starRate;
            RatingBar startRate;
            TextView text;

            public CommentsViewHolder(@NonNull View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.comment_userAvatar_img);
                userName = itemView.findViewById(R.id.comment_userName_tv);
                startRate = itemView.findViewById(R.id.comment_row_rating);
                text = itemView.findViewById(R.id.comment_text_tv);
            }

            public void bind(Comment comment) {
                userName.setText(comment.userEmail);
                Model.instance().getUserByEmail(comment.userEmail, user -> {
                    if(user.profileImg != "") {
                        Picasso.get().load(user.profileImg).placeholder(R.drawable.avatar).into(avatar);
                    } else {
                        avatar.setImageResource(R.drawable.avatar);
                    }

                });

                text.setText(comment.text);
                //starRate.setImageResource(starImage(comment.rate));
                startRate.setRating(comment.rate);
            }
        }

        class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentsViewHolder> {


            @NonNull
            @Override
            public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.comment_row, parent, false);
                return new CommentsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
                Comment comment = comments.get(position);
                //Log.d("tag", "comment: " + st.avatarURL);
                holder.bind(comment);
            }

            @Override
            public int getItemCount() {
                if (comments != null) {
                    return comments.size();
                }return 0;
            }
        }
    }