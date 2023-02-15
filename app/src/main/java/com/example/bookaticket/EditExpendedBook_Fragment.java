package com.example.bookaticket;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditExpendedBook_Fragment extends Fragment {

    List<Comment> commentsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_expended_book, container, false);
        TextView name = view.findViewById(R.id.expandedBook_name_tv);
        ImageView cover = view.findViewById(R.id.expandedBook_cover_img);
        TextView writer = view.findViewById(R.id.expandedBook_writer_tv);
        TextView year = view.findViewById(R.id.expandedBook_year_tv);
        TextView description = view.findViewById(R.id.expandedBook_description_tv);

        RecyclerView comments = view.findViewById(R.id.expandedBook_comments_rl);

        comments.setHasFixedSize(true);
        comments.setLayoutManager(new LinearLayoutManager(view.getContext()));
        EditCommentRecyclerAdapter commentAdapter = new EditCommentRecyclerAdapter();
        comments.setAdapter(commentAdapter);

        BookInfo bookInfo = EditExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInfo();
        String bookInstanceId = EditExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInstanceId();
        String userEmail = Model.instance().getCurentUserEmail();

        name.setText(bookInfo.getTitle());
        Glide.with(this).load(bookInfo.getThumbnail()).into(cover);
        writer.setText(bookInfo.getAuthor());
        year.setText(bookInfo.getPublishedDate());
//        description.setText(bookInfo.getDescription());

        Model.instance().getBookInfoCommentsByUserEmail(bookInfo.getId(), userEmail, new Model.Listener<List<Comment>>() {
            @Override
            public void onComplete(List<Comment> data) {
                if (data != null && data.size() > 0) {
                    commentsList = data;
                    commentAdapter.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getContext(), "No comments", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    class EditCommentsViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        ImageView avatar;
        //            ImageView starRate;
        RatingBar startRate;
        EditText text;

        ImageButton saveBtn;

        public EditCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.comment_userAvatar_img);
            userName = itemView.findViewById(R.id.comment_userName_tv);
            startRate = itemView.findViewById(R.id.comment_row_rating);
            text = itemView.findViewById(R.id.editCommentText);
            saveBtn = itemView.findViewById(R.id.applyComment);
        }

        public void bind(Comment comment) {
            userName.setText(comment.userEmail);

            Model.instance().getUserByEmail(comment.userEmail, user -> {
                if (user.profileImg != ""){
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

    class EditCommentRecyclerAdapter extends RecyclerView.Adapter<EditCommentsViewHolder> {


        @NonNull
        @Override
        public EditCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.edit_comment_row, parent, false);
            return new EditCommentsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EditCommentsViewHolder holder, int position) {
            Comment comment = commentsList.get(position);
            //Log.d("tag", "comment: " + st.avatarURL);
            holder.bind(comment);

            holder.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment.text = holder.text.getText().toString();
                    comment.rate = (int) holder.startRate.getRating();
                    Model.instance().updateComment(comment, new Model.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            if (data) {
                                Toast.makeText(getContext(), "Comment updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Comment not updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            if (commentsList != null) {
                return commentsList.size();
            }return 0;
        }
    }
}
