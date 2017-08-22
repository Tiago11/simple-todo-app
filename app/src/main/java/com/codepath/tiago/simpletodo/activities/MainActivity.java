package com.codepath.tiago.simpletodo.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.codepath.tiago.simpletodo.adapters.TodoCursorAdapter;
import com.codepath.tiago.simpletodo.fragments.EditTodoItemDialogFragment;
import com.codepath.tiago.simpletodo.R;
import com.codepath.tiago.simpletodo.models.TodoItem;
import com.codepath.tiago.simpletodo.models.TodoItem_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements EditTodoItemDialogFragment.EditTodoItemDialogListener {

    // Tag for logging.
    private static final String TAG = MainActivity.class.toString();

    // Request code for the |AddTodoItemActivity| intent.
    private final int ADD_NEW_ITEM_REQUEST_CODE = 1;

    private ListView lvItems;

    private SimpleCursorAdapter simpleCursorAdapter;

    private TodoCursorAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Show app-icon on the toolbar.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_bow);

            // find reference to lvItems view.
            lvItems = (ListView) findViewById(R.id.lvItems);

            Cursor cursorItems = SQLite.select().from(TodoItem.class).queryResults().cursor();

            //String[] fromColumns = new String[] {TodoItem_Table.title.toString().replaceAll("`", ""),
            //        TodoItem_Table.date.toString().replaceAll("`", "")};
            //int[] toViews = new int[] {android.R.id.text1, android.R.id.text2}; // The textViews in simple_list_item_2.

            //simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
            //        cursorItems, fromColumns, toViews, 0);

            //lvItems.setAdapter(simpleCursorAdapter);

            /**************************************************************************************/
            // Start of new adapter

            todoAdapter = new TodoCursorAdapter(this, cursorItems);

            lvItems.setAdapter(todoAdapter);

            // End of new adapter
            /**************************************************************************************/

            // Attach event listeners to the ListView.
            setupListViewListener();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddNewItem:
                Intent i = new Intent(MainActivity.this, AddTodoItemActivity.class);
                startActivityForResult(i, ADD_NEW_ITEM_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_NEW_ITEM_REQUEST_CODE) {
                TodoItem todoItem = (TodoItem) Parcels.unwrap(data.getParcelableExtra("todoItem"));
                todoItem.save();

                // Update the cursor adapter.
                updateAllItemsInCursorAdapter();

            }
        }
    }

    /*
     * Attaches event listeners to the ListView.
     */
    private void setupListViewListener() {
        // Event listener to remove item on long click.
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item that was long-clicked.
                        TodoItem rmTodoItem = getTodoItemFromCursorAdapter(pos);
                        rmTodoItem.delete();

                        // Update the cursor adapter.
                        updateAllItemsInCursorAdapter();

                        return true;
                    }
                }
        );

        // Event listener to edit an item on click.
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {

                        // Get the title of the item clicked.
                        TodoItem todoItem = getTodoItemFromCursorAdapter(pos);

                        // Show the edit dialog.
                        showEditTodoItemDialog(todoItem, pos);
                    }
                }
        );
    }

    /*
     * Fetches all the items from the database and connects them to the adapter through a cursor.
     */
    private void updateAllItemsInCursorAdapter() {
        Cursor nc = SQLite.select().from(TodoItem.class).queryResults().cursor();

        // This method closes the old cursor releasing its resources.
        // It also tells the `ListView` to redraw its elements, so we don't have
        // to manually notify the adapter that the underlying data has changed.
        //simpleCursorAdapter.changeCursor(nc);
        todoAdapter.changeCursor(nc);
    }

    /*
     * Returns the title of the item at position |pos| of the cursor.
     */
    private String getTitleFromCursorAdapter(int pos) {
        //Cursor c = simpleCursorAdapter.getCursor();
        Cursor c = todoAdapter.getCursor();
        c.moveToPosition(pos);
        return c.getString(c.getColumnIndex(TodoItem_Table.title.toString().replaceAll("`", "")));
    }

    /*
    * Returns the item at position |pos| of the cursor.
     */
    private TodoItem getTodoItemFromCursorAdapter(int pos) {
        //Cursor c = simpleCursorAdapter.getCursor();
        Cursor c = todoAdapter.getCursor();
        c.moveToPosition(pos);
        return new TodoItem(c);
    }

    private void showEditTodoItemDialog(TodoItem todoItem, int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoItemDialogFragment editTodoItemDialogFragment = EditTodoItemDialogFragment.newInstance(todoItem, pos);
        editTodoItemDialogFragment.show(fm, "fragment_alert");
    }

    @Override
    public void onFinishEditDialog(TodoItem todoItem, int pos) {

        // Get the original item from the database.
        TodoItem item = getTodoItemFromCursorAdapter(pos);

        // Update the fields.
        item.setTitle(todoItem.getTitle());
        item.setDate(todoItem.strToDate());

        // Update the item into the database.
        item.update();

        // Update the cursor adapter.
        updateAllItemsInCursorAdapter();
    }
}
