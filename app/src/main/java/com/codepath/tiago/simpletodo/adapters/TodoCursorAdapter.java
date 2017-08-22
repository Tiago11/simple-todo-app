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
import com.codepath.tiago.simpletodo.models.TodoItem;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tiago on 8/22/17.
 */

public class TodoCursorAdapter extends CursorAdapter {

    // Tag for logging.
    private final static String TAG = TodoCursorAdapter.class.toString();

    // We keep a reference to the context.
    private final Context context;

    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
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

            String dateToDisplay = getDateToDisplay(date);

            // Populate fields with extracted values.
            tvTitle.setText(title);
            tvDate.setText(dateToDisplay);

        } catch (IllegalArgumentException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

    }

    /*
     * Returns "Today" if |date| is today's date, "Tomorrow" if |date| is tomorrow's date or
     * |date| otherwise.
     */
    private String getDateToDisplay(String strDate) {

        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        String strToday = TodoItem.dateToString(today);

        if (strDate.equals(strToday)) {
            return this.context.getResources().getString(R.string.today);
        }

        c.add(Calendar.DATE, 1);
        Date tomorrow = c.getTime();
        String strTomorrow = TodoItem.dateToString(tomorrow);

        if (strDate.equals(strTomorrow)) {
            return this.context.getResources().getString(R.string.tomorrow);
        }

        return strDate;
    }
}

































