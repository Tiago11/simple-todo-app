package com.codepath.tiago.simpletodo;

import android.database.Cursor;
import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tiago on 8/16/17.
 */

@Parcel(analyze = {TodoItem.class})
@Table(database = MyDatabase.class)
public class TodoItem extends BaseModel {

    @Column(name = "_id") // For the cursorAdapter to work, the cursor's id columns must be named |_id|.
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String title;

    @Column
    String date;

    // Format in which the date should be saved to the database and displayed to users.
    private final static String DATE_FORMAT = "MMM dd, yyyy";

    // Tag for logging.
    private final static String TAG = TodoItem.class.toString();

    public TodoItem() {
        super();
    }

    public TodoItem(String title) {
        super();
        this.title = title;
    }

    public TodoItem(String title, Date date) {
        super();
        this.title = title;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        this.date = formatter.format(date);
    }

    public TodoItem(Cursor c) {
        super();
        try {
            int idValue = c.getInt(c.getColumnIndexOrThrow("_id"));
            String titleValue = c.getString(c.getColumnIndexOrThrow("title"));
            String dateValue = c.getString(c.getColumnIndexOrThrow("date"));

            this.id = idValue;
            this.title = titleValue;
            this.date = dateValue;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        this.date = formatter.format(date);
    }

    public Date strToDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date fDate;
        try {
            // Parse the string.
            fDate = formatter.parse(this.date);
        } catch (ParseException e) {
            // Log the error.
            Log.e(TAG, Log.getStackTraceString(e));

            // Assign today's date.
            fDate = new Date();
        }

        return fDate;
    }

    @Override
    public String toString() {
        return this.title.toString();
    }
}
