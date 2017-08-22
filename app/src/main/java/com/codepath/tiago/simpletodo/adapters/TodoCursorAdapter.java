package com.codepath.tiago.simpletodo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.codepath.tiago.simpletodo.R;

/**
 * Created by tiago on 8/22/17.
 */

public class TodoCursorAdapter extends CursorAdapter {

    // Tag for logging.
    private final static String TAG = TodoCursorAdapter.class.toString();

    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /*
     * The newView method is used to inflate a new view and return it,
     * you don't bind any data to the view at this point.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    /*
     * The bindView method is used to bind all data to a given view
     * such as setting the text on a TextView.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            // Find fields to populate in inflated template.
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);

            // Extract properties from cursor.
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

            // Populate fields with extracted values.
            tvTitle.setText(title);
            tvDate.setText(date);

        } catch (IllegalArgumentException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

    }
}

































