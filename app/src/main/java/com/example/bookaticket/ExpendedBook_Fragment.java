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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.Comment;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ExpendedBook_Fragment extends Fragment {
    private LinkedList<Comment> comments = new LinkedList<>();


    // change everything to book info and delete book!
//    List<Book> books;
//    static Book book1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expended_book, container, false);
        BookInfo bookInfo = ExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInfo();
        String bookInstanceID = ExpendedBook_FragmentArgs.fromBundle(getArguments()).getBookInstanceId();

//        books = Model.instance().getAllBooks();
//        book1 = new Book();
//        Comment c1 = new Comment("String bookInfoID", "String id", "user1", 4, "very good");
//        Comment c2 = new Comment("String bookInfoID", "String id", "user2", 5, "great");
//        comments.add(c1);
//        comments.add(c2);

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
                    Model.instance().takeBookFromStation(bookInstanceID, currentUserEmail);
                    Toast.makeText(getContext(), "Book taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "you mast be logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecyclerView comments = view.findViewById(R.id.expandedBook_comments_rl);

        comments.setHasFixedSize(true);
        comments.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CommentRecyclerAdapter commentAdapter = new CommentRecyclerAdapter();
        comments.setAdapter(commentAdapter);

        name.setText(bookInfo.getTitle());
        //cover.setImageURI(Uri.parse("res/drawable/harry_potter1.png"));
        Picasso.get().load(bookInfo.getThumbnail()).into(cover);
//        cover.setImageResource(R.drawable.harry_potter1);
        writer.setText(bookInfo.getAuthor());
        publishedDate.setText(bookInfo.getPublishedDate());
        description.setText(bookInfo.getDescription());

        return view;

    }


    class CommentsViewHolder extends RecyclerView.ViewHolder{
            TextView userName;
            ImageView avatar;
            ImageView starRate;
            TextView text;

            public CommentsViewHolder(@NonNull View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.comment_userAvatar_img);
                userName = itemView.findViewById(R.id.comment_userName_tv);
                starRate = itemView.findViewById(R.id.comment_stars);
                text = itemView.findViewById(R.id.comment_text_tv);
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

            public void bind(Comment comment) {
                userName.setText(comment.userEmail);
         //       avatar.setImageResource(comment.userAvatarPath);
                text.setText(comment.text);
                starRate.setImageResource(starImage(comment.rate));
            }
        }

//        interface OnItemClickListener {
//            void onItemClick(int pos) ;
//        }

        class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

            //OnItemClickListener listener;
//            void setOnItemClickListener(OnItemClickListener listener) {
//                this.listener = listener;
//            }


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
                return comments.size();
            }
        }
    }