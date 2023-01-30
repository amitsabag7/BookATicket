package com.example.bookaticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.User;
import java.util.List;

public class EditUserDetails_Fragment extends Fragment {

    private List<Book> books;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_details, container, false);
        user = Model.instance().getUser();
        books = Model.instance().getAllBooks();

        EditText userName = view.findViewById(R.id.editTextTextPersonName);
        EditText hometown = view.findViewById(R.id.editTextTextHometown);
        TextView email = view.findViewById(R.id.Email);
        ImageView profileImg = view.findViewById(R.id.profileImg);
        ImageView saveEditProfile = view.findViewById(R.id.editprofileimg);

        userName.setText(user.userName);
        hometown.setText(user.homeTown);
        email.setText(user.email);
        profileImg.setImageResource(R.drawable.avatar);
        RecyclerView books = view.findViewById(R.id.MyBooksrecyclerView);

        books.setHasFixedSize(true);
        books.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BookRecyclerAdapter bookAdapter = new BookRecyclerAdapter();
        books.setAdapter(bookAdapter);

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.userName = String.valueOf(userName.getText());
                user.homeTown = String.valueOf(hometown.getText());
                Model.instance().setUser(user);
                userName.setText(user.userName);
                hometown.setText(user.homeTown);
                Navigation.findNavController(view).navigate(R.id.action_editUserDetails_Fragment_to_userDetails_Fragment);
            }
        });

        return view;
    }

    // For RecyclerView listener
    interface  OnItemClickListener {
        void onClick(int position);
    }

    public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {

        private OnItemClickListener mListener;

        void setOnItemClickListener(OnItemClickListener listener){
            mListener = listener;
        }

        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView bookNameTV;
            TextView bookAuthorTV;
            ImageView bookCoverIV;

            public BookViewHolder(@NonNull View itemView,OnItemClickListener listener) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null){
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION){
                                listener.onClick(position);
                            }
                        }
                    }
                });

                bookNameTV = itemView.findViewById(R.id.myBookRow_bookNameTV);
                bookAuthorTV = itemView.findViewById(R.id.myBookRow_bookAuthorTV);
                bookCoverIV = itemView.findViewById(R.id.myBookRow_bookCoverIV);
            }

            public void bind(Book book) {
                bookNameTV.setText(book.name);
                bookAuthorTV.setText(book.writer);
                bookCoverIV.setImageResource(R.drawable.harry_potter1);
            }
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.edit_my_book_row, parent, false);
            return new BookViewHolder(view,mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull EditUserDetails_Fragment.BookRecyclerAdapter.BookViewHolder holder,
                                     int position) {
            Book book = books.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}
