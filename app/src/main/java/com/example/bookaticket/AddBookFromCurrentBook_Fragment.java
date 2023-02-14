package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;

import java.util.List;


public class AddBookFromCurrentBook_Fragment extends Fragment {

    private ImageButton globalsearchBtn;
    private BookInstanceAdapter bookInstanceAdapter;
    List<BookInstance> bookInstances;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book_from_current_book_, container, false);
        bookInstanceAdapter = new BookInstanceAdapter(bookInstances, AddBookFromCurrentBook_Fragment.this.getContext());
        linearLayoutManager = new LinearLayoutManager(AddBookFromCurrentBook_Fragment.this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView  = (RecyclerView) view.findViewById(R.id.idCurrentBooks);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(bookInstanceAdapter);


        if (Model.instance().isLogedIn())
        {
            userEmail = Model.instance().getCurentUserEmail();
            Model.instance().getAllBookInstancesByUserEmail(userEmail, (list) -> {
                if (list != null) {
                    bookInstances = list;
                    bookInstanceAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getContext(), "No logged in user", Toast.LENGTH_SHORT).show();
        }

        globalsearchBtn = view.findViewById(R.id.idBtnGlobalSearch);

        globalsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.newBook_Fragment);
            }
        });
        return view;
    }
}