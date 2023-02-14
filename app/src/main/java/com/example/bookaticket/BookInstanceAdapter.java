package com.example.bookaticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.BookInfo;
import com.example.bookaticket.Model.BookInstance;
import com.example.bookaticket.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookInstanceAdapter extends RecyclerView.Adapter<BookInstanceAdapter.BookViewHolder> {

    private List<BookInstance> bookInstanceList;
//    private List<BookInfo> bookInfos;

     private Map<String, BookInfo> bookInfosMap;
    private Context mcontext;
    private FragmentManager fm;

    public BookInstanceAdapter(List<BookInstance> bookInstanceList, Map<String, BookInfo> bookInfosMap, Context mcontext) {
        this.bookInstanceList = bookInstanceList;
        this.bookInfosMap = bookInfosMap;
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

            String bookInfoID = bookInstance.getBookInfoID();

            BookInfo bookInfo = bookInfosMap.get(bookInfoID);
            if (bookInfo != null){
                holder.bind(bookInfo);
            } else {
                Toast.makeText(this.mcontext, "No book info found", Toast.LENGTH_SHORT).show();
            }

//            Model.instance().getBookInfoByID(bookInfoID, (callback) -> {
//                BookInfo bookInfo = new BookInfo();
//                if (callback != null) {
//                    bookInfo = callback;
//                    holder.bind(bookInfo);
//                }
//                else
//                {
//                    Toast.makeText(this.mcontext, "No book info found", Toast.LENGTH_SHORT).show();
//                }
//            });

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

    public void setBookInstances(List<BookInstance> bookInstances) {
        this.bookInstanceList = bookInstances;
        notifyDataSetChanged();
    }

    public void setBookInfoMap(Map<String, BookInfo> bookInfoMap) {
        this.bookInfosMap = bookInfoMap;
        notifyDataSetChanged();
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
            Glide.with(bookCoverIV.getContext()).load(bookInfo.getThumbnail()).into(bookCoverIV);
            //Picasso.get().load(bookInfo.getThumbnail()).into(bookCoverIV);
        }
    }
}



