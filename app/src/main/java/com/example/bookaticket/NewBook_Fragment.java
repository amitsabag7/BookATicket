package com.example.bookaticket;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookaticket.Model.Book;
import com.squareup.picasso.Picasso;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;


public class NewBook_Fragment extends Fragment {

    private RequestQueue mRequestQueue;
    private List<Book> bookInfoList;
    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_book, container, false);

        progressBar = view.findViewById(R.id.idLoadingPB);
        searchEdt = view.findViewById(R.id.idEdtSearchBooks);
        searchBtn = view.findViewById(R.id.idBtnSearch);

        return  view;
    }


    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

        // creating variables for arraylist and context.
        private List<Book> bookInfoList;
        private Context mcontext;

        // creating constructor for array list and context.
        public BookAdapter(List<Book> bookInfoList, Context mcontext) {
            this.bookInfoList = bookInfoList;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // inflating our layout for item of recycler view item.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_book_row, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

            // inside on bind view holder method we are
            // setting our data to each UI component.
            Book book = bookInfoList.get(position);
            holder.nameTV.setText(book.getName());
            holder.publisherTV.setText(book.getWriter());

            // below line is use to set image from URL in our image view.
            Picasso.get().load(book.getImgPath()).into(holder.bookIV);

            // below line is use to add on click listener for our item of recycler view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    // inside on click listener method we are calling a new activity
//                    // and passing all the data of that item in next intent.
//                    Intent i = new Intent(mcontext, BookDetails.class);
//                    i.putExtra("title", bookInfo.getTitle());
//                    i.putExtra("authors", bookInfo.getAuthors());
//                    i.putExtra("publisher", bookInfo.getPublisher());
//                    i.putExtra("description", bookInfo.getDescription());
//
//                    // after passing that data we are
//                    // starting our new  intent.
//                    mcontext.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            // inside get item count method we
            // are returning the size of our array list.
            return bookInfoList.size();
        }

        public class BookViewHolder extends RecyclerView.ViewHolder {
            // below line is use to initialize
            // our text view and image views.
            TextView nameTV, publisherTV, pageCountTV, dateTV;
            ImageView bookIV;

            public BookViewHolder(View itemView) {
                super(itemView);
                nameTV = itemView.findViewById(R.id.idTVBookTitle);
                publisherTV = itemView.findViewById(R.id.idTVpublisher);
                dateTV = itemView.findViewById(R.id.idTVDate);
                bookIV = itemView.findViewById(R.id.idIVbook);
            }
        }
    }
}