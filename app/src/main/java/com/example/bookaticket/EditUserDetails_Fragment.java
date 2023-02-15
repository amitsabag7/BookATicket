package com.example.bookaticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.User;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditUserDetails_Fragment extends Fragment {

    private List<Book> books;
    private User user;
    ImageView profileImg;
    boolean isAvatarSelected = false;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    String userEmail;

    List<BookInstance> bookInstances;

    Map<String, BookInfo> bookInfoMap = new HashMap<>();

    EditUserBookAdapter editUserBookAdapter;

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
                    Navigation.findNavController(getView()).navigate(R.id.userDetails_Fragment);
                }
                return false;
            }
        }, this, Lifecycle.State.RESUMED);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if (result != null) {
                            profileImg.setImageBitmap(result);
                            isAvatarSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    profileImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_details, container, false);

        books = Model.instance().getAllBooks();

        EditText userName = view.findViewById(R.id.editTextTextPersonName);
        EditText hometown = view.findViewById(R.id.editTextTextHometown);
        TextView email = view.findViewById(R.id.Email);
        profileImg = view.findViewById(R.id.profileImg);
        ImageView saveEditProfile = view.findViewById(R.id.editprofileimg);
        ImageButton camera = view.findViewById(R.id.cameraButton);
        ImageButton gallery = view.findViewById(R.id.galleryButton);

        Model.instance().getUserByEmail(Model.instance().getCurentUserEmail(), (u) -> {
            user = u;
            userName.setText(user.userName);
            hometown.setText(user.homeTown);
            email.setText(user.email);
            if (user.profileImg != "") {
                Picasso.get().load(user.profileImg).placeholder(R.drawable.avatar).into(profileImg);
            } else {
                profileImg.setImageResource(R.drawable.avatar);
            }
        });

        if (Model.instance().isLogedIn()) {
            userEmail = Model.instance().getCurentUserEmail();
            // get the users books
            Model.instance().getAllBookInstancesByUserEmail(userEmail, (bookInstancesList) -> {
                if (bookInstancesList != null) {
                    bookInstances = bookInstancesList;
                    editUserBookAdapter.notifyDataSetChanged();

                    for (BookInstance bookInstance : bookInstances) {
                        Model.instance().getBookInfoByID(bookInstance.getBookInfoID(), (bookInfo) -> {
                            if (bookInfo != null) {
                                bookInfoMap.put(bookInfo.getId(), bookInfo);
                                editUserBookAdapter.notifyDataSetChanged();
                                Log.d("TAG", "book info by book instance size: " + bookInfoMap.size());
                            }
                        });
                    }
                    Log.d("TAG", "book instances by user size: " + bookInstances.size());
                }
            });
        }

        RecyclerView books = view.findViewById(R.id.MyBooksrecyclerView);

        books.setHasFixedSize(true);
        books.setLayoutManager(new LinearLayoutManager(view.getContext()));
        editUserBookAdapter = new EditUserBookAdapter();
        books.setAdapter(editUserBookAdapter);

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.userName = String.valueOf(userName.getText());
                user.homeTown = String.valueOf(hometown.getText());

                if (isAvatarSelected) {
                    profileImg.setDrawingCacheEnabled(true);
                    profileImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) profileImg.getDrawable()).getBitmap();
                    Model.instance().uploadImage(user.email, bitmap, url -> {
                        if (url != null) {
                            Log.d("profileImg", "***" + url);
                            user.profileImg = url;
                        }
                    });
                }
                Model.instance().updateUserDetails(user);
                Navigation.findNavController(view).navigate(R.id.action_editUserDetails_Fragment_to_userDetails_Fragment);
            }
        });

        camera.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        gallery.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }

    // For RecyclerView listener


    public class EditUserBookAdapter extends RecyclerView.Adapter<EditUserBookAdapter.EditUserBookViewHolder> {

        public EditUserBookAdapter() {

        }

        @NonNull
        @Override
        public EditUserBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
            return new EditUserBookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EditUserBookViewHolder holder, int position) {
            if (bookInstances != null) {
                BookInstance bookInstance = bookInstances.get(position);
                String bookInfoID = bookInstance.getBookInfoID();
                BookInfo bookInfo = bookInfoMap.get(bookInfoID);
                if (bookInfo != null) {
                    holder.bind(bookInfo);
                } else {
                    Toast.makeText(getContext(), "No book info found", Toast.LENGTH_SHORT).show();
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditUserDetails_FragmentDirections.ActionEditUserDetailsFragmentToEditExpendedBookFragment action =
                                EditUserDetails_FragmentDirections.actionEditUserDetailsFragmentToEditExpendedBookFragment(bookInfo, bookInstance.getId());

                        Navigation.findNavController(v).navigate(action);
//                        FragmentActivity activity = (FragmentActivity) mcontext;
//
//                        if (bundle != null) {
//                            bundle.putSerializable("bookInfo", bookInfo);
//                            bundle.putString("bookInstanceId", bookInstance.getId());
//                            Navigation.findNavController(v).navigate(R.id.addBook_Fragment, bundle);
//                        } else {
//                            Toast.makeText(mcontext, "bundle is empty", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });

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

        public class EditUserBookViewHolder extends RecyclerView.ViewHolder {

            TextView bookNameTV;
            TextView bookAuthorTV;
            ImageView bookCoverIV;

            public EditUserBookViewHolder(@NonNull View itemView) {
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

        interface OnItemClickListener {
            void onClick(int position);
        }
}

