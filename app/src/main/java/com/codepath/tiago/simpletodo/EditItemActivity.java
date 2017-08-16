package com.codepath.tiago.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Find reference to etEditItem view.
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        // Get the title of the item from the Intent.
        String titleItem = getIntent().getStringExtra("title");

        // Append the title to the text field.
        etEditItem.append(titleItem);
    }

    /*
     * Fires when "Save Changes" button is pressed on this Activity.
     * Sends the new title of the item and its position to the |MainActivity|.
     */
    public void onSubmit(View view) {
        // Find reference to etEditItem view.
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        // Get the position of the item from the Intent.
        int posEditItem = getIntent().getIntExtra("pos", 0);

        // Get the new title of the item.
        String newTitle = etEditItem.getText().toString();

        // Check that the new title is not empty, if it is, Toast a message and do nothing.
        if (TextUtils.isEmpty(newTitle)) {
            Toast.makeText(this, "Sorry! Cannot have empty items.", Toast.LENGTH_LONG).show();
            return;
        }

        // Create and set the Intent to return.
        Intent data = new Intent();
        data.putExtra("newTitle", newTitle);
        data.putExtra("pos", posEditItem);
        setResult(RESULT_OK, data);

        // Return to the MainActivity.
        finish();
    }
}
