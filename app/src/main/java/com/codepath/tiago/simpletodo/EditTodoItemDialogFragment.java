package com.codepath.tiago.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tiago on 8/18/17.
 */

public class EditTodoItemDialogFragment extends DialogFragment {
    private EditText etEditItem;

    public EditTodoItemDialogFragment() {
        // Empty constructor is required for DialogFragment.
    }

    public static EditTodoItemDialogFragment newInstance(TodoItem todoItem, int pos) {
        EditTodoItemDialogFragment frag = new EditTodoItemDialogFragment();

        // Create a Bundle with the information I want to pass to the dialogFragment.
        Bundle args = new Bundle();
        args.putString("todoTitle", todoItem.getTitle());
        args.putInt("pos", pos);

        // Set the Bundle.
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the view we created for the dialogFragment.
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View v = inflater.inflate(R.layout.fragment_edit_item, null);

        // Find the reference of the view's editText field.
        etEditItem = (EditText) v.findViewById(R.id.etEditItem);

        // Get the title of the item from the activity.
        String titleItem = getArguments().getString("todoTitle");

        // Append it to the editText field.
        etEditItem.append(titleItem);

        // Set and create an alertDialog with our custom view.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Edit a Todo item"); // Todo: change hardcoded string.
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setCancelable(true);

        // Set a positive button and attach an onClick event listener to it.
        alertDialogBuilder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() { // Todo: change hardcoded string.
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TodoItem upTodoItem = new TodoItem(etEditItem.getText().toString().trim());
                int pos = getArguments().getInt("pos");

                // If the new title is empty, don't do anything and show a message.
                if (TextUtils.isEmpty(upTodoItem.getTitle())) {
                    Toast.makeText(getContext(), "Sorry! Cannot have empty items.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Get the listener implementation to send the data back to the activity.
                EditTodoItemDialogListener listener = (EditTodoItemDialogListener) getActivity();
                listener.onFinishEditDialog(upTodoItem, pos);
                dialog.dismiss();
            }
        });
        // Set a negative button and attach an onClick listener to it.
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Just close the dialog.
                dialog.dismiss();
            }
        });

        // Create the alertDialog.
        return alertDialogBuilder.create();
    }

    /*
     * Implementations of this interface will be able to receive data from the dialog.
     */
    public interface  EditTodoItemDialogListener {
        void onFinishEditDialog(TodoItem upTodoItem, int pos);
    }

}






















