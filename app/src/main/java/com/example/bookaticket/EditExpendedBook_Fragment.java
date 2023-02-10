package com.example.bookaticket;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.User;
import java.util.List;

public class EditExpendedBook_Fragment extends Fragment {

    List<Book> books;
    static Book book1;
    static User user;
    CommentsListFagmentViewModel viewModel = new CommentsListFagmentViewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_expended_book, container, false);
        books = Model.instance().getAllBooks();
        book1 = books.get(0);
        user = Model.instance().getUser();

        TextView name = view.findViewById(R.id.expandedBook_name_tv);
        ImageView cover = view.findViewById(R.id.expandedBook_cover_img);
        TextView writer = view.findViewById(R.id.expandedBook_writer_tv);
        TextView year = view.findViewById(R.id.expandedBook_year_tv);
        TextView description = view.findViewById(R.id.expandedBook_description_tv);

        RecyclerView comments = view.findViewById(R.id.expandedBook_comments_rl);

        comments.setHasFixedSize(true);
        comments.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CommentRecyclerAdapter commentAdapter = new CommentRecyclerAdapter(viewModel.getData(user.userName));
        comments.setAdapter(commentAdapter);

        commentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {}
        });

        name.setText(book1.name);
        cover.setImageResource(R.drawable.harry_potter1);
        writer.setText(book1.writer);
        year.setText(String.valueOf(book1.year));
        description.setText(book1.description);

        return view;
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        ImageView avatar;
        ImageView starRate;
        TextView text;
        EditText editComment;
        ImageButton applyComment;
        List<Comment> data;

        public CommentsViewHolder(@NonNull View itemView,OnItemClickListener listener,List<Comment> data) {
            super(itemView);
            this.data = data;
            avatar = itemView.findViewById(R.id.comment_userAvatar_img);
            userName = itemView.findViewById(R.id.comment_userName_tv);
            starRate = itemView.findViewById(R.id.comment_stars);
            text = itemView.findViewById(R.id.comment_text_tv);
            editComment = itemView.findViewById(R.id.editCommentText);
            applyComment = itemView.findViewById(R.id.applyComment);

            applyComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Comment comment = data.get(getAdapterPosition());
                    String commetText = String.valueOf(editComment.getText());
                    book1.updateComment(comment,commetText);

                    if (text.getText().length() > 31){
                       text.getLayoutParams().width = 0;
                       text.requestLayout();
                    }

                    text.setText(data.get(getAdapterPosition()).text);
                    text.setVisibility(View.VISIBLE);
                    editComment.setVisibility(View.INVISIBLE);
                    applyComment.setVisibility(View.INVISIBLE);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                    text.setVisibility(View.INVISIBLE);
                    editComment.setText(text.getText());
                    editComment.setVisibility(View.VISIBLE);
                    applyComment.setVisibility(View.VISIBLE);
                }
            });
        }

        public int starImage(int stars) {
            switch (stars) {
                case 0:
                    return R.drawable.stars0;
                case 1:
                    return R.drawable.star1;
                case 2:
                    return R.drawable.stars2;
                case 3:
                    return R.drawable.stars3;
                case 4:
                    return R.drawable.stars4;
                case 5:
                    return R.drawable.stars5;
            }
            return R.drawable.stars0;
        }

        public void bind(Comment comment,int position) {
                userName.setText(comment.userEmail);
               // avatar.setImageResource("drawable/avatar.png");
                text.setText(comment.text);
                starRate.setImageResource(starImage(comment.rate));
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    class CommentRecyclerAdapter extends RecyclerView.Adapter<EditExpendedBook_Fragment.CommentsViewHolder> {

        OnItemClickListener mListener;

        List<Comment> data;

        public CommentRecyclerAdapter(List<Comment> data){
            this.data = data;
        }

        void setOnItemClickListener(OnItemClickListener listener){
            mListener = listener;
        }

        @NonNull
        @Override
        public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.edit_comment_row, parent, false);

            return new CommentsViewHolder(view, mListener,data);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
            Comment comment = data.get(position);
            holder.bind(comment, position);
        }

        @Override
        public int getItemCount() {
            int count = 0;
            List<Comment> comment = book1.comments;

            for (Comment cm:comment) {
                if (cm.userEmail == user.userName){
                    count++;
                }
            }
            return count;
        }
    }
}
