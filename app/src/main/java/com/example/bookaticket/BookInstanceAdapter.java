package com.example.bookaticket;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookInstanceAdapter extends RecyclerView.Adapter<BookInstanceAdapter.BookViewHolder> {

    private List<BookInstance> bookInstanceList;
    private List<BookInfo> bookInfos;
    private Context mcontext;
    private FragmentManager fm;

    public BookInstanceAdapter(List<BookInstance> bookInstanceList, Context mcontext) {
        this.bookInstanceList = bookInstanceList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (bookInstanceList != null) {
            BookInstance bookInstance = bookInstanceList.get(position);
//            BookInfo bi = new BookInfo();
            String bookInfoID = bookInstance.getBookInfoID();

            Model.instance().getBookInfoByID(bookInfoID, (callback) -> {
                BookInfo bookInfo = new BookInfo();
                if (callback != null) {
                    bookInfo = callback;
                    holder.bind(bookInfo);
                    BookInfo finalBookInfo = bookInfo;
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Bundle args = new Bundle();
//                            args.putString("id", finalBookInfo.getId());
//                            args.putString("title", finalBookInfo.getTitle());
//                            args.putString("author", finalBookInfo.getAuthor());
//                            args.putString("publishedDate", finalBookInfo.getPublishedDate());
//                            args.putString("thumbnail", finalBookInfo.getThumbnail());
//                            args.putString("description", finalBookInfo.getDescription());

                            StationBookList_FragmentDirections.ActionStationBookListFragmentToExpendedBookFragment action =
                                    StationBookList_FragmentDirections.actionStationBookListFragmentToExpendedBookFragment(finalBookInfo);
                            Navigation.findNavController(view).navigate(action);
//                            StationBookList_FragmentArgs.
//                            Navigation.findNavController(view).navigate(R.id.expendedBook_Fragment, args);
                        }
                    });
                }
                else
                {
                    Toast.makeText(this.mcontext, "No book info found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookInstanceList != null) {
            return bookInstanceList.size();
        } else {
            return 0;
        }
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
            Picasso.get().load(bookInfo.getThumbnail()).into(bookCoverIV);
        }
    }
}



