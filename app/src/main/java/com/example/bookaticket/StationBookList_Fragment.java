package com.example.bookaticket;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
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
    List<BookInstance> bookInstances;
    List<BookInfo> bookInfoList;
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_station_book_list, container, false);
        stationId = StationBookList_FragmentArgs.fromBundle(getArguments()).getId();
        stationName = StationBookList_FragmentArgs.fromBundle(getArguments()).getName();
//        adapter = new BookInstanceAdapter(bookInstances, StationBookList_Fragment.this.getContext());
        TextView stationNameTV = view.findViewById(R.id.stationBookList_stationNameTV);
            RecyclerView stationBookList = view.findViewById(R.id.stationBookListRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        stationBookList.setHasFixedSize(true);
        stationBookList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(stationBookList.getContext(),
                layoutManager.getOrientation());
        stationBookList.addItemDecoration(dividerItemDecoration);

        adapter = new BookRecyclerAdapter();


        Model.instance().getAllBookInfosByStationID(stationId, (list) -> {
            if (list != null) {
                bookInfoList = list;
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
            }
        });

        Model.instance().getAllBookInstancesByStationID(stationId, (list) -> {
            if (list != null) {
                bookInstances = list;
                adapter.notifyDataSetChanged();
            } else {
            }
        });

        stationBookList.setAdapter(adapter);
        ImageButton addBookBtn = view.findViewById(R.id.stationBookList_addBookBtn);
        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StationBookList_FragmentDirections.ActionStationBookListFragmentToAddBookFromCurrentBookFragment action =
                        StationBookList_FragmentDirections.actionStationBookListFragmentToAddBookFromCurrentBookFragment(stationId, stationName);
                Navigation.findNavController(v).navigate(action);
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

        public void bind(BookInfo bookInfo) {
            bookNameTV.setText(bookInfo.getTitle());
            bookAuthorTV.setText(bookInfo.getAuthor());
//            String newUrlString = bookInfo.getThumbnail().replaceFirst("^http:", "https:");
            Glide.with(bookCoverIV.getContext()).load(bookInfo.getThumbnail()).into(bookCoverIV);
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
            int p = position;
            if (bookInfoList != null) {
                BookInfo bookInfo = bookInfoList.get(position);
                holder.bind(bookInfo);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bookInstances != null) {
                            StationBookList_FragmentDirections.ActionStationBookListFragmentToExpendedBookFragment action =
                                    StationBookList_FragmentDirections.actionStationBookListFragmentToExpendedBookFragment(bookInfo,bookInstances.get(p).getId());
                            Navigation.findNavController(v).navigate(action);
                        }

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if (bookInfoList != null) {
                return bookInfoList.size();
            } else {
                return 0;
            }
        }
    }
}