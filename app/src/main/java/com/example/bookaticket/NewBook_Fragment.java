package com.example.bookaticket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookaticket.Model.BookInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewBook_Fragment extends Fragment {

    private RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;
    private TextView onlineTv;
    private  TextView myBooksTv;
    private int clickCounter = 0;
    private BookAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_book_, container, false);
        adapter = new BookAdapter(bookInfoArrayList, NewBook_Fragment.this.getContext());
        linearLayoutManager = new LinearLayoutManager(NewBook_Fragment.this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView  = (RecyclerView) view.findViewById(R.id.idRVBooks);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.idLoadingPB);
        searchEdt = view.findViewById(R.id.idEdtSearchBooks);
        searchBtn = view.findViewById(R.id.idBtnSearch);
        onlineTv = view.findViewById(R.id.onlineTv);
        myBooksTv = view.findViewById(R.id.myBooksTv);

        // adding my books here
        bookInfoArrayList.add(new BookInfo("Harry Potter 1", "res/drawable/harry_potter1.png", "J K Rolling", "something"));
        adapter.notifyDataSetChanged();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setVisibility(View.VISIBLE);
                onlineTv.setVisibility(View.VISIBLE);
                myBooksTv.setVisibility(View.INVISIBLE);
                bookInfoArrayList.clear();
                clickCounter++;
                adapter.notifyDataSetChanged();

                if (clickCounter>1)
                {
                    if (searchEdt.getText().toString().isEmpty() ) {
                        searchEdt.setError("Please enter search query");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    getBooksInfo(searchEdt.getText().toString(), view);

                }
            }
        });
        return view;
    }

        private void getBooksInfo(String query, View view) {

            bookInfoArrayList.clear();

            mRequestQueue = Volley.newRequestQueue(this.getContext());

            mRequestQueue.getCache().clear();

            String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;

            RequestQueue queue = Volley.newRequestQueue(this.getContext());

            JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    if (response != null) {
                        try {
                            JSONArray itemsArray = response.getJSONArray("items");
                            for (int i = 0; i < itemsArray.length(); i++) {
                                JSONObject itemsObj = itemsArray.getJSONObject(i);
                                JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                                String title = volumeObj.optString("title");
                                String subtitle = volumeObj.optString("subtitle");
                                JSONArray authorsArray = volumeObj.getJSONArray("authors");
                                String publisher = volumeObj.optString("publisher");
                                String publishedDate = volumeObj.optString("publishedDate");
                                String description = volumeObj.optString("description");
                                int pageCount = volumeObj.optInt("pageCount");
                                JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                                String thumbnail = imageLinks.optString("thumbnail");
                                String previewLink = volumeObj.optString("previewLink");
                                String infoLink = volumeObj.optString("infoLink");
                                JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                                String buyLink = saleInfoObj.optString("buyLink");
                                ArrayList<String> authorsArrayList = new ArrayList<>();
                                if (authorsArray.length() != 0) {
                                    for (int j = 0; j < authorsArray.length(); j++) {
                                        authorsArrayList.add(authorsArray.optString(i));
                                    }
                                }
                                BookInfo bookInfo = new BookInfo(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
                                bookInfoArrayList.add(bookInfo);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewBook_Fragment.this.getContext(), "No Data Found" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // also displaying error message in toast.
                    Toast.makeText(NewBook_Fragment.this.getContext(), "Error found is " + error, Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(booksObjrequest);
        }

}