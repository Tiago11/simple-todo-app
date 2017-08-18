package com.codepath.tiago.simpletodo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

public class MainActivity extends AppCompatActivity {

    // Request code for EditItemActivity.
    private static final int EDIT_ITEM_REQUEST_CODE = 1;

    // Tag for logging.
    private static final String TAG = MainActivity.class.toString();

    private ListView lvItems;

    private SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find reference to lvItems view.
        lvItems = (ListView) findViewById(R.id.lvItems);

        Cursor cursorItems = SQLite.select().from(TodoItem.class).queryResults().cursor();

        String[] fromColumns = new String[] {"title"}; // TODO: The column name can't be hardcoded.
        int[] toViews = new int[] {android.R.id.text1}; // The textView in simple_list_item_1.

        try {

            simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursorItems, fromColumns, toViews, 0);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        lvItems.setAdapter(simpleCursorAdapter);

        // Attach event listeners to the ListView.
        setupListViewListener();
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
                        //Cursor c = simpleCursorAdapter.getCursor();
                        //c.moveToPosition(pos);
                        //TodoItem rmTodoItem = new TodoItem(c);
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
                        // Create a Intent to communicate across Activities.
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                        // Get the title of the item clicked.
                        String titleItem = getTitleFromCursorAdapter(pos);

                        // Store the title and position of the item in the Intent.
                        i.putExtra("title", titleItem);
                        i.putExtra("pos", pos);

                        // Start the |EditItemActivity| expecting a result from it.
                        startActivityForResult(i, EDIT_ITEM_REQUEST_CODE);
                    }
                }
        );
    }

    /*
     * Fires when "Add Item" button is pressed on this Activity.
     * Adds an item to the list, unless the item description is empty.
     */
    public void onAddItem(View view) {
        // Find reference to etNewItem view.
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        // Get the title of the new item.
        String titleItem = etNewItem.getText().toString();

        // Check if the text field is empty, we don't want to add empty items.
        if (TextUtils.isEmpty(titleItem)) {

            // Show a Toast message to the user and do nothing.
            Toast.makeText(this, "Sorry! Cannot add an empty item.", Toast.LENGTH_LONG).show();
            return;
        }

        // Add the new item to the adapter.
        TodoItem newTodoItem = new TodoItem(titleItem);
        newTodoItem.save();

        // Update the cursor adapter.
        updateAllItemsInCursorAdapter();

        // Clear the text field.
        etNewItem.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            // The result is from |EditItemActivity|.
            if (requestCode == EDIT_ITEM_REQUEST_CODE) {

                // Get the data from the Intent.
                int pos = data.getExtras().getInt("pos");
                String newTitleItem = data.getExtras().getString("newTitle");

                // Get the original title of the item.
                String oldTitleItem = getTitleFromCursorAdapter(pos);

                // If the title of the item wasn't changed, we don't do anything.
                if (oldTitleItem.equals(newTitleItem)) {
                    return;
                }
                try {
                    // Set the item new title, notify the adapter and write changes to a file.
                    TodoItem upTodoItem = SQLite.select()
                            .from(TodoItem.class)
                            .where(TodoItem_Table.title.eq(oldTitleItem))
                            .querySingle();
                    upTodoItem.setTitle(newTitleItem);
                    upTodoItem.update();

                    // Update the cursor adapter.
                    updateAllItemsInCursorAdapter();
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    /*
     * Fetches all the items from the database and connects them to the adapter through a cursor.
     */
    private void updateAllItemsInCursorAdapter() {
        Cursor nc = SQLite.select().from(TodoItem.class).queryResults().cursor();

        // This method closes the old cursor releasing its resources.
        // It also tells the `ListView` to redraw its elements, so we don't have
        // to manually notify the adapter that the underlying data has changed.
        simpleCursorAdapter.changeCursor(nc);
    }

    /*
     * Returns the title of the item at position |pos| of the cursor.
     */
    private String getTitleFromCursorAdapter(int pos) {
        Cursor c = simpleCursorAdapter.getCursor();
        c.moveToPosition(pos);
        return c.getString(c.getColumnIndex("title")); // TODO: Column name can't be hardcoded.
    }

    /*
    * Returns the item at position |pos| of the cursor.
     */
    private TodoItem getTodoItemFromCursorAdapter(int pos) {
        Cursor c = simpleCursorAdapter.getCursor();
        c.moveToPosition(pos);
        return new TodoItem(c);
    }
}
