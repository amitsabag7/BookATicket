package com.example.bookaticket;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetails_Fragment extends Fragment {

    List<Book> books;
    //    public User user= new User();
    UserViewModel viewModel = new UserViewModel();

    String userEmail;

    List<BookInstance> bookInstances;

    Map<String, BookInfo> bookInfoMap = new HashMap<>();

    UserBookAdapter userBookAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.main_menu_private);
                menu.findItem(R.id.main_menu_logout).setVisible(true).setOnMenuItemClickListener((view) -> {
                    Model.instance().logoutuser();
                    Navigation.findNavController(getView()).navigate(R.id.login_Fragment);
                    return true;
                });

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(getView()).navigate(R.id.homePage_Fragment);
                }
                return false;
            }
        }, this, Lifecycle.State.RESUMED);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        Bundle bundle = new Bundle();

        userBookAdapter = new UserBookAdapter();
        TextView userName = view.findViewById(R.id.UserName);
        TextView hometown = view.findViewById(R.id.Hometown);
        TextView email = view.findViewById(R.id.Email);
        ImageView profileImg = view.findViewById(R.id.profileImg);
        ImageView editprofile = view.findViewById(R.id.editprofileimg);

        Model.instance().getUserByEmail(Model.instance().getCurentUserEmail(), (u) -> {
            viewModel.setUser(u);
            userName.setText(viewModel.getUser().userName);
            hometown.setText(viewModel.getUser().homeTown);
            email.setText(viewModel.getUser().email);
            if (viewModel.getUser().profileImg != "") {
                Picasso.get().load(viewModel.getUser().profileImg).placeholder(R.drawable.avatar).into(profileImg);
            } else {
                profileImg.setImageResource(R.drawable.avatar);
            }
        });


//        books = Model.instance().getAllBooks();

        if (Model.instance().isLogedIn()) {
            userEmail = Model.instance().getCurentUserEmail();
            // get the users books
            Model.instance().getAllBookInstancesByUserEmail(userEmail, (bookInstancesList) -> {
                if (bookInstancesList != null) {
                    bookInstances = bookInstancesList;
                    userBookAdapter.notifyDataSetChanged();

                    for (BookInstance bookInstance : bookInstances) {
                        Model.instance().getBookInfoByID(bookInstance.getBookInfoID(), (bookInfo) -> {
                            if (bookInfo != null) {
                                bookInfoMap.put(bookInfo.getId(), bookInfo);
                                userBookAdapter.notifyDataSetChanged();
                                Log.d("TAG", "book info by book instance size: " + bookInfoMap.size());
                            }
                        });
                    }
                    Log.d("TAG", "book instances by user size: " + bookInstances.size());
                }
            });

            editprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_userDetails_Fragment_to_editUserDetails_Fragment);
                }
            });

            RecyclerView books = view.findViewById(R.id.MyBooksrecyclerView);
            books.setHasFixedSize(true);
            books.setLayoutManager(new LinearLayoutManager(view.getContext()));
            books.setAdapter(userBookAdapter);

        }

        return view;
    }

    public class UserBookAdapter extends RecyclerView.Adapter<UserBookAdapter.UserBookViewHolder> {

        public UserBookAdapter() {

        }

        @NonNull
        @Override
        public UserBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
            return new UserBookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserBookViewHolder holder, int position) {
            if (bookInstances != null) {
                BookInstance bookInstance = bookInstances.get(position);
                String bookInfoID = bookInstance.getBookInfoID();
                BookInfo bookInfo = bookInfoMap.get(bookInfoID);
                if (bookInfo != null) {
                    holder.bind(bookInfo);
                } else {
                    //Toast.makeText(getContext(), "No book info found", Toast.LENGTH_SHORT).show();
                }

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        FragmentActivity activity = (FragmentActivity) mcontext;
//
//                        if (bundle != null) {
//                            bundle.putSerializable("bookInfo", bookInfo);
//                            bundle.putString("bookInstanceId", bookInstance.getId());
//                            Navigation.findNavController(v).navigate(R.id.addBook_Fragment, bundle);
//                        } else {
//                            Toast.makeText(mcontext, "bundle is empty", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

            }
        }

        @Override
        public int getItemCount() {
            if (bookInstances != null) {
                return bookInstances.size();
            } else {
                return 0;
            }
        }

        public class UserBookViewHolder extends RecyclerView.ViewHolder {

            TextView bookNameTV;
            TextView bookAuthorTV;
            ImageView bookCoverIV;

            public UserBookViewHolder(@NonNull View itemView) {
                super(itemView);
                bookNameTV = itemView.findViewById(R.id.bookRow_bookNameTV);
                bookAuthorTV = itemView.findViewById(R.id.bookRow_bookAuthorTV);
                bookCoverIV = itemView.findViewById(R.id.bookRow_bookCoverIV);
            }

            public void bind(BookInfo bookInfo) {
                bookNameTV.setText(bookInfo.getTitle());
                bookAuthorTV.setText(bookInfo.getAuthor());
                Glide.with(bookCoverIV.getContext()).load(bookInfo.getThumbnail()).into(bookCoverIV);
                //Picasso.get().load(bookInfo.getThumbnail()).into(bookCoverIV);
            }
        }
    }
}