package com.codepath.tiago.simpletodo;

import android.database.Cursor;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by tiago on 8/16/17.
 */

@Table(database = MyDatabase.class)
public class TodoItem extends BaseModel {

    @Column(name = "_id") // For the cursorAdapter to work, the cursor's id columns must be named |_id|.
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String title;

    public TodoItem() {
        super();
    }

    public TodoItem(String title) {
        super();
        this.title = title;
    }

    public TodoItem(Cursor c) {
        super();
        try {
            int idValue = c.getInt(c.getColumnIndexOrThrow("_id"));
            String titleValue = c.getString(c.getColumnIndexOrThrow("title"));

            this.id = idValue;
            this.title = titleValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
