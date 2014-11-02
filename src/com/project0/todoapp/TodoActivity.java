package com.project0.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	
	private ArrayList<String> items;
	private ArrayAdapter<String> itemAdapter;
	
	// UI
	ListView lvItems;
	EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		// Find View for UI components
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		
		readItem();
		itemAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemAdapter);
		
		// setup listener for long click on list view
		setupListViewListener();
	}

	public void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				items.remove(pos);
				itemAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	public void populateArrayItems() {
		items = new ArrayList<String>();
		items.add("item1");
		items.add("item2");
	}
	
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		itemAdapter.add(itemText);
		etNewItem.setText("");
		saveItems();
	}
	
	private void readItem() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try{
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e) {
			items = new ArrayList<String>();
		}
		
	}
	
	private void saveItems() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, items);
		} catch(IOException e) {
			e.printStackTrace();
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
}
