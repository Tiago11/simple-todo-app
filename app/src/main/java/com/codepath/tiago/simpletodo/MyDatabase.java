package com.codepath.tiago.simpletodo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by tiago on 8/16/17.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 3;
}
