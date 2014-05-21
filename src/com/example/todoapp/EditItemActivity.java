package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private EditText etEditItem;
	private int positionOfSelectedItem;
	private Item selectedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    selectedItem = (Item) intent.getSerializableExtra("selected_item");
	    positionOfSelectedItem = intent.getIntExtra("position", -1);
	    
	    // Set the edit text field with the name of the selected item
	    etEditItem = (EditText)findViewById(R.id.etEditItem);
	    etEditItem.setText(selectedItem.task);
	    etEditItem.setSelection(selectedItem.task.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onSaveItem(View v) {
		// Prepare data intent 
		Intent data = new Intent();
		
		// Pass relevant data back as a result
	//	data.putExtra("edited_item", etEditItem.getText().toString());
		selectedItem.task = etEditItem.getText().toString();
		data.putExtra("edited_item", (Item) selectedItem);
		data.putExtra("item_position", positionOfSelectedItem);
		
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		
		finish(); // closes the activity, pass data to parent
	}
}
