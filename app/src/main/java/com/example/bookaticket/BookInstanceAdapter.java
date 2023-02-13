package com.example.bookaticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaticket.Model.BookInstance;

import java.util.List;

public class BookInstanceAdapter extends RecyclerView.Adapter<BookInstanceAdapter.BookViewHolder> {

    private List<BookInstance> bookInstanceList;
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
            // here pull book info from firebase and update the info thats presented
            holder.bind(bookInstance);
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

        public void bind(BookInstance bookInstance) {
            bookNameTV.setText("bookInstance.name");
            bookAuthorTV.setText("bookInstance.writer");
            bookCoverIV.setImageResource(R.drawable.harry_potter1);
        }
    }
}



