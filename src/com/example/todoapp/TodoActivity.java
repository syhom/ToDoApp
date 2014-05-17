package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		etNewItem = (EditText)findViewById(R.id.etNewItem);
		lvItems = (ListView)findViewById(R.id.lvItems);
		readItems();
		todoAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
	    lvItems.setAdapter(todoAdapter);
	    setupListViewListener();
	}

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				String selectedItem = todoItems.get(pos);
				Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
				intent.putExtra("selected_item", selectedItem);
				intent.putExtra("position", pos);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	// This gets called when the Add button is pressed
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		if (itemText.length() != 0) {
			todoAdapter.add(itemText);	
			etNewItem.setText("");
			writeItems();
		}
	}
	
	// Handle the result of the sub-activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

			// Extract item and position from result extras
			String newItem = data.getExtras().getString("edited_item");			
			int position = data.getExtras().getInt("item_position");
			
			// Update todoItems ArrayList
			todoItems.set(position, newItem);
			
			// Update view
			todoAdapter.notifyDataSetChanged();
			
			// Save change to file
			writeItems();
		}
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
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

	// Read the list of items in the todo.txt and store in the todoItems ArrayList
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	// Save changes to the todo.txt file
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
