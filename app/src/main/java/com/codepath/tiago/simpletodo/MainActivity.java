package com.codepath.tiago.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Request code for EditItemActivity.
    static final int EDIT_ITEM_REQUEST_CODE = 1;

    private ListView lvItems;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find reference to lvItems view.
        lvItems = (ListView) findViewById(R.id.lvItems);

        // Read items from a file and set them to the collection |items|.
        readItems();

        // Connect the collection with the adapter.
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

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
                        items.remove(pos);

                        // Notify the adapter to show the changes on the activity.
                        itemsAdapter.notifyDataSetChanged();

                        // Write the changes to a file.
                        writeItems();

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
                        String titleItem = items.get(pos);

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
        itemsAdapter.add(titleItem);

        // Clear the text field.
        etNewItem.setText("");

        // Write the items to a file.
        writeItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            // The result is from |EditItemActivity|.
            if (requestCode == EDIT_ITEM_REQUEST_CODE) {

                // Get the data from the Intent.
                int pos = data.getExtras().getInt("pos");
                String newTitleItem = data.getExtras().getString("newTitle");

                String oldTitleItem = items.get(pos);

                // If the title of the item wasn't changed, we don't do anything.
                if (oldTitleItem.equals(newTitleItem)) {
                    return;
                }

                // Set the item new title, notify the adapter and write changes to a file.
                items.set(pos, newTitleItem);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    /*
     * Populates the |items| collection by reading from a file.
     */
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    /*
     * Writes the contents of the |items| collection into a file.
     * TODO: writeItems currently writes all the items of the collection in a file.
     * TODO: We could implement a smarter version that only adds the newest items.
     */
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
