package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.bookaticket.Model.BookInfo;

import java.util.ArrayList;


public class AddBookFromCurrentBook_Fragment extends Fragment {

    private ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>();
    private ImageButton globalsearchBtn;
    private TextView myBooksTv;
    private BookAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_book_from_current_book_, container, false);;
        adapter = new BookAdapter(bookInfoArrayList, AddBookFromCurrentBook_Fragment.this.getContext());
        linearLayoutManager = new LinearLayoutManager(AddBookFromCurrentBook_Fragment.this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView  = (RecyclerView) view.findViewById(R.id.idCurrentBooks);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        // adding my books here
        bookInfoArrayList.add(new BookInfo("Harry Potter 1", "res/drawable/harry_potter1.png", "J K Rolling", "something"));
        adapter.notifyDataSetChanged();

        //myBooksTv = view.findViewById(R.id.myBooksTv);
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