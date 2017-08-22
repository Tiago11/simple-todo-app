package com.codepath.tiago.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.tiago.simpletodo.R;
import com.codepath.tiago.simpletodo.models.TodoItem;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tiago on 8/20/17.
 */

public class AddTodoItemActivity extends AppCompatActivity {

    // Tag for logging.
    private static final String TAG = AddTodoItemActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_item);

            Toolbar toolbar = (Toolbar) findViewById(R.id.tbEmpty);
            setSupportActionBar(toolbar);

            // Get a support ActionBar corresponding to this toolbar.
            ActionBar ab = getSupportActionBar();

            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);

            // Show app-icon on the toolbar.
            ab.setDisplayShowHomeEnabled(true);
            ab.setIcon(R.drawable.ic_bow);

            // set today's date on the date picker.
            setCurrentDateOnView();
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

    }

    /*
     * Creates the items on the menu (toolbar).
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    /*
     * Fires when "Save Changes" button is pressed on this Activity.
     * Fetches all the info from the fields and sends it to the |MainActivity|.
     */
    public void onSubmit(View view) {
        DatePicker dpDate = (DatePicker) findViewById(R.id.dpDate);
        Calendar c = Calendar.getInstance();
        c.set(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth());
        Date date = c.getTime();

        EditText etTitleItem = (EditText) findViewById(R.id.etTitleItem);
        String title = etTitleItem.getText().toString().trim();


        // Check if the text field is empty, we don't want to add empty items.
        if (TextUtils.isEmpty(title)) {

            // Show a Toast message to the user and do nothing.
            Toast.makeText(this, "Sorry! Cannot add an empty item.", Toast.LENGTH_LONG).show();
            return;
        }

        // Prepare data intent to send back to the main activity.
        Intent data = new Intent();
        TodoItem ti = new TodoItem(title, date);
        data.putExtra("todoItem", Parcels.wrap(ti));
        setResult(RESULT_OK, data);

        this.finish();
    }

    /*
     *
     */
    private void setCurrentDateOnView() {

        DatePicker dpDate = (DatePicker) findViewById(R.id.dpDate);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dpDate.init(year, month, day, null);

        // Set the minimum date of the date picker to today, we don't want to do thing in the past.
        dpDate.setMinDate(System.currentTimeMillis() - 1000);
    }
}
