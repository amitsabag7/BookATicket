package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddBookFromCurrentBook_Fragment extends Fragment {

    private ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>();
    private ImageButton globalsearchBtn;
    private TextView myBooksTv;
    private BookAdapter adapter;
    private BookInstanceAdapter bookInstanceAdapter;
    List<BookInstance> bookInstances;

    private Map<String, BookInfo> bookInfoMap =  new HashMap<>();
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book_from_current_book_, container, false);
        String stationId =  AddBookFromCurrentBook_FragmentArgs.fromBundle(getArguments()).getStationId();
        String stationName =  AddBookFromCurrentBook_FragmentArgs.fromBundle(getArguments()).getStationName();
        Bundle bundle = new Bundle();
        bundle.putString("stationId", stationId);
        bundle.putString("stationName", stationName);
        bookInstanceAdapter = new BookInstanceAdapter(bookInstances, bookInfoMap, AddBookFromCurrentBook_Fragment.this.getContext(), bundle);
//        adapter = new BookAdapter(bookInstances, bookInfoMap, AddBookFromCurrentBook_Fragment.this.getContext(), bundle);
        linearLayoutManager = new LinearLayoutManager(AddBookFromCurrentBook_Fragment.this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView  = (RecyclerView) view.findViewById(R.id.idCurrentBooks);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(bookInstanceAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        if (Model.instance().isLogedIn())
        {
            userEmail = Model.instance().getCurentUserEmail();
            // get the users books
            Model.instance().getAllBookInstancesByUserEmail(userEmail, (bookInstancesList) -> {
                if (bookInstancesList != null) {
                    bookInstances = bookInstancesList;
                    bookInstanceAdapter.setBookInstances(bookInstances);
                    bookInstanceAdapter.notifyDataSetChanged();

                    for (BookInstance bookInstance : bookInstances) {
                        Model.instance().getBookInfoByID(bookInstance.getBookInfoID(), (bookInfo) -> {
                            if (bookInfo != null) {
                                bookInfoMap.put(bookInfo.getId(), bookInfo);
                                bookInstanceAdapter.setBookInfoMap(bookInfoMap);
                                bookInstanceAdapter.notifyDataSetChanged();
                                Log.d("TAG", "book info by book instance size: " + bookInfoMap.size());
                            }
                        });
                    }
                    Log.d("TAG", "book instances by user size: " + bookInstances.size());
                }
            });


        }
        else {
            Toast.makeText(getContext(), "No logged in user", Toast.LENGTH_SHORT).show();
        }
//
        globalsearchBtn = view.findViewById(R.id.idBtnGlobalSearch);

        globalsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stationId =  AddBookFromCurrentBook_FragmentArgs.fromBundle(getArguments()).getStationId();
                String stationName =  AddBookFromCurrentBook_FragmentArgs.fromBundle(getArguments()).getStationName();
                AddBookFromCurrentBook_FragmentDirections.ActionAddBookFromCurrentBookFragmentToNewBookFragment action =
                        AddBookFromCurrentBook_FragmentDirections.actionAddBookFromCurrentBookFragmentToNewBookFragment(stationId, stationName);
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }
}