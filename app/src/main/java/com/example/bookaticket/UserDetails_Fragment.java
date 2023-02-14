package com.example.bookaticket;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.User;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import java.util.List;

public class UserDetails_Fragment extends Fragment {

    List<Book> books;
//    public User user= new User();
    UserViewModel viewModel = new UserViewModel();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.main_menu_private);
                menu.findItem(R.id.main_menu_logout).setVisible(true).setOnMenuItemClickListener((view) ->{
                    Model.instance().logoutuser();
                    Navigation.findNavController(getView()).navigate(R.id.login_Fragment);
                    return true;
                });

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(getView()).navigate(R.id.homePage_Fragment);
                }
                return false;
            }
        },this, Lifecycle.State.RESUMED);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        TextView userName = view.findViewById(R.id.UserName);
        TextView hometown = view.findViewById(R.id.Hometown);
        TextView email = view.findViewById(R.id.Email);
        ImageView profileImg = view.findViewById(R.id.profileImg);
        ImageView editprofile=view.findViewById(R.id.editprofileimg);

        Model.instance().getUserByEmail(Model.instance().getCurentUserEmail(),(u)-> {
            viewModel.setUser(u);
            userName.setText(viewModel.getUser().userName);
            hometown.setText(viewModel.getUser().homeTown);
            email.setText(viewModel.getUser().email);
            if (viewModel.getUser().profileImg != "") {
                Picasso.get().load(viewModel.getUser().profileImg).placeholder(R.drawable.avatar).into(profileImg);
            }
            else {
                profileImg.setImageResource(R.drawable.avatar);
            }
        });


        books = Model.instance().getAllBooks();

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_userDetails_Fragment_to_editUserDetails_Fragment);
            }
        });

        RecyclerView books = view.findViewById(R.id.MyBooksrecyclerView);

        books.setHasFixedSize(true);
        books.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BookRecyclerAdapter bookAdapter = new BookRecyclerAdapter();
        books.setAdapter(bookAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookNameTV;
        TextView bookAuthorTV;
        ImageView bookCoverIV;
        ImageView bookDeliverCoverIV;
        TextView bookDeliverText;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNameTV = itemView.findViewById(R.id.myBookRow_bookNameTV);
            bookAuthorTV = itemView.findViewById(R.id.myBookRow_bookAuthorTV);
            bookCoverIV = itemView.findViewById(R.id.myBookRow_bookCoverIV);
            bookDeliverCoverIV = itemView.findViewById(R.id.myBookRow_bookCover_deliver);
            bookDeliverText = itemView.findViewById(R.id.myBookRow_deliver_text);
        }

        public void bind(Book book) {
            bookNameTV.setText(book.name);
            bookAuthorTV.setText(book.writer);
            bookCoverIV.setImageResource(R.drawable.harry_potter1);

            if (book.isReturned == false){
                bookDeliverCoverIV.setImageResource(R.drawable.not_delivered);
                bookDeliverText.setText("Still with me");
            } else {
                bookDeliverCoverIV.setImageResource(R.drawable.delivered);
                bookDeliverText.setText("Delivered");
            }
        }
    }

    public class BookRecyclerAdapter extends RecyclerView.Adapter<BookViewHolder> {

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.my_book_row, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = books.get(position);
            holder.bind(book);

        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }

}
