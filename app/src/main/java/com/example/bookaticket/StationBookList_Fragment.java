package com.example.bookaticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookaticket.Model.Book;
import com.example.bookaticket.Model.Model;
import com.example.bookaticket.Model.Station;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class StationBookList_Fragment extends Fragment {

    Station station;
    List<Book> stationBooks;
    BookRecyclerAdapter adapter;

    public static StationBookList_Fragment newInstance(String stationId) {
        StationBookList_Fragment fragment = new StationBookList_Fragment();
        Bundle args = new Bundle();
        args.putString("stationId", stationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            station = new Station(args.getString("stationId"));
            fetchStation();
        } else {
            station = new Station("07cJ3RsJZMRYyYgsLGcO", new GeoPoint(32.0853, 34.7818), "Herzliya");
            stationBooks = Model.instance().getAllBooks();
            fetchStation();
        }
    }

    private void fetchStation() {
        Model.instance().getStationById(station.getId(), (result) -> {
            if (station == null) {
                Toast.makeText(getContext(), "Error fetching station details", Toast.LENGTH_SHORT).show();
            } else {
                this.station = result;
                fetchBooksByStationId();
            }
        });
    }

    private void fetchBooksByStationId() {
        Model.instance().getBooksByStationId(station.getId(), (books) -> {
            if (books.isEmpty()) {
                Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
            } else {
                stationBooks.addAll(books);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_book_list, container, false);
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

        if (station.getName() != null) {
            stationNameTV.setText(station.getName());
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
            Book book = stationBooks.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            return stationBooks.size();
        }
    }
}