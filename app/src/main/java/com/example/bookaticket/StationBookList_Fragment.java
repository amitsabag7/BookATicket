package com.example.bookaticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Model;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StationBookList_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationBookList_Fragment extends Fragment {
    TextView stationNameTV;
    String stationName;
    String stationId;
    List<Book> books;
    BookRecyclerAdapter adapter;

    public static StationBookList_Fragment newInstance(String stationName) {
        StationBookList_Fragment fragment = new StationBookList_Fragment();
        Bundle args = new Bundle();
        args.putString("stationName", stationName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            stationName = args.getString("stationName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_station_book_list, container, false);
        stationId = StationBookList_FragmentArgs.fromBundle(getArguments()).getId();
        stationName = StationBookList_FragmentArgs.fromBundle(getArguments()).getName();

        books = Model.instance().getAllBooks();
        TextView stationNameTV = view.findViewById(R.id.stationBookList_stationNameTV);
        RecyclerView stationBookList = view.findViewById(R.id.stationBookListRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        stationBookList.setHasFixedSize(true);
        stationBookList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(stationBookList.getContext(),
                layoutManager.getOrientation());
        stationBookList.addItemDecoration(dividerItemDecoration);
        adapter = new BookRecyclerAdapter();
        stationBookList.setAdapter(adapter);
        ImageButton addBookBtn = view.findViewById(R.id.stationBookList_addBookBtn);
        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.addBookFromCurrentBook_Fragment);
            }
        });

        if (stationName != null) {
            stationNameTV.setText(stationName);
        }

        return view;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookNameTV;
        TextView bookAuthorTV;
        ImageView bookCoverIV;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNameTV = itemView.findViewById(R.id.bookRow_bookNameTV);
            bookAuthorTV = itemView.findViewById(R.id.bookRow_bookAuthorTV);
            bookCoverIV = itemView.findViewById(R.id.bookRow_bookCoverIV);
        }

        public void bind(Book book) {
            bookNameTV.setText(book.name);
            bookAuthorTV.setText(book.writer);
            bookCoverIV.setImageResource(R.drawable.harry_potter1);
        }
    }

    public class BookRecyclerAdapter extends RecyclerView.Adapter<BookViewHolder> {

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.book_row, parent, false);
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