package com.example.bookaticket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AddBookDialog extends DialogFragment {
    String bookTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();
        bookTitle = bundle.getString("title");

        builder.setMessage("Do you want to add : "
                        + bookTitle
                        + " to station "
                        + "station  name here"
                        + " ?")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // add this book to the station
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel and go back
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
