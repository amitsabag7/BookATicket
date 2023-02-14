package com.example.bookaticket;

import android.app.AlertDialog;
import android.app.BackgroundServiceStartNotAllowedException;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaticket.Model.BookInfo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<BookInfo> bookInfoArrayList;
    private Context mcontext;
    private FragmentManager fm;
    private Bundle bundle;
    // creating constructor for array list and context.
    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context mcontext, Bundle bundle) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting our data to each UI component.
        BookInfo bookInfo = bookInfoArrayList.get(position);
        holder.nameTV.setText(bookInfo.getTitle());
        holder.publisherTV.setText(bookInfo.getPublisher());
        holder.pageCountTV.setText("No of Pages : " + bookInfo.getPageCount());
        holder.dateTV.setText(bookInfo.getPublishedDate());

        // below line is use to set image from URL in our image view.
//        String newUrlString = bookInfo.getThumbnail().replaceFirst("^http", "https");
        try{
            Glide.with(mcontext).load(bookInfo.getThumbnail()).into(holder.bookIV);
//            Picasso.get().load(newUrlString).into(holder.bookIV);
        }catch (Exception e){
            System.out.println(e);
        }

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 inside on click listener method we are calling a new activity
//                 and passing all the data of that item in next intent.

                FragmentActivity activity = (FragmentActivity) mcontext;

                if (bundle != null) {
                    bundle.putSerializable("bookInfo", bookInfo);
                    bundle.putString("bookInstanceId", "");
                    Navigation.findNavController(v).navigate(R.id.addBook_Fragment, bundle);
                } else {
                    Toast.makeText(mcontext, "bundle is empty", Toast.LENGTH_SHORT).show();
                }


//                AddBookDialog newAddDialog = new AddBookDialog();
//

//                // need to send the station as well
//                newAddDialog.setArguments(args);

//                newAddDialog.putExtra("title", bookInfo.getTitle());
//                i.putExtra("subtitle", bookInfo.getSubtitle());
//                i.putExtra("authors", bookInfo.getAuthors());
//                i.putExtra("publisher", bookInfo.getPublisher());
//                i.putExtra("publishedDate", bookInfo.getPublishedDate());
//                i.putExtra("description", bookInfo.getDescription());
//                i.putExtra("pageCount", bookInfo.getPageCount());
//                i.putExtra("thumbnail", bookInfo.getThumbnail());
//                i.putExtra("previewLink", bookInfo.getPreviewLink());
//                i.putExtra("infoLink", bookInfo.getInfoLink());
//                i.putExtra("buyLink", bookInfo.getBuyLink());

//                FragmentActivity activity = (FragmentActivity) mcontext;
//                newAddDialog.show(activity.getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return bookInfoArrayList.size();
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
            pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
        }
    }
}