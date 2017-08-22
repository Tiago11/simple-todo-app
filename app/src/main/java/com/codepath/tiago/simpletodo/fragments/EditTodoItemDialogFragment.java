package com.codepath.tiago.simpletodo.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.tiago.simpletodo.R;
import com.codepath.tiago.simpletodo.models.TodoItem;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tiago on 8/18/17.
 */

public class EditTodoItemDialogFragment extends DialogFragment {
    private EditText etEditTitle;
    private DatePicker dpEditDate;

    public EditTodoItemDialogFragment() {
        // Empty constructor is required for DialogFragment.
    }

    public static EditTodoItemDialogFragment newInstance(TodoItem todoItem, int pos) {
        EditTodoItemDialogFragment frag = new EditTodoItemDialogFragment();

        // Parse the items date into a Date object.
        Date pDate = todoItem.strToDate();
        Calendar c = Calendar.getInstance();
        c.setTime(pDate);
        int dateYear = c.get(Calendar.YEAR);
        int dateMonth = c.get(Calendar.MONTH);
        int dateDay = c.get(Calendar.DAY_OF_MONTH);

        // Create a Bundle with the information I want to pass to the dialogFragment.
        Bundle args = new Bundle();
        args.putString("todoTitle", todoItem.getTitle());
        args.putInt("dateYear", dateYear);
        args.putInt("dateMonth", dateMonth);
        args.putInt("dateDay", dateDay);
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
        etEditTitle = (EditText) v.findViewById(R.id.etEditTitle);

        // Find the reference of the view's datePicker.
        dpEditDate = (DatePicker) v.findViewById(R.id.dpEditDate);

        // Get the title of the item from the activity.
        String titleItem = getArguments().getString("todoTitle");

        // Append it to the editText field.
        etEditTitle.append(titleItem);

        // Get the date information.
        int dateYear = getArguments().getInt("dateYear");
        int dateMonth = getArguments().getInt("dateMonth");
        int dateDay = getArguments().getInt("dateDay");

        // Set the date picker to the item's date.
        dpEditDate.init(dateYear, dateMonth, dateDay, null);

        /*
            TODO: There is a problem here, what if the original date of the item is before today's date?
            TODO: One possible solution could be to check for that, and if that is the case, force the
            TODO: date picker to show today's date.
         */

        // Set the minimum date of the date picker to today, we don't want to do thing in the past.
        dpEditDate.setMinDate(System.currentTimeMillis() - 1000);

        // Set and create an alertDialog with our custom view.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.EditDialog);

        // Create custom title for the dialog.
        TextView dialogTitle = new TextView(getContext());
        dialogTitle.setText(R.string.edit_item_label);
        dialogTitle.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT < 23) {
            dialogTitle.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
        } else {
            dialogTitle.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        dialogTitle.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the custom title to the dialog.
        alertDialogBuilder.setCustomTitle(dialogTitle);

        alertDialogBuilder.setView(v);
        alertDialogBuilder.setCancelable(true);

        // Set a positive button and attach an onClick event listener to it.
        alertDialogBuilder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() { // Todo: change hardcoded string.
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Get the new title.
                String itemTitle = etEditTitle.getText().toString().trim();

                // Get the new date.
                Calendar c = Calendar.getInstance();
                c.set(dpEditDate.getYear(), dpEditDate.getMonth(), dpEditDate.getDayOfMonth());
                Date itemDate = c.getTime();

                // Create the item.
                TodoItem upTodoItem = new TodoItem(itemTitle, itemDate);
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
