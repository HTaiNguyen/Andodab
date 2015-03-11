package fr.upem.andodab;

import java.util.ArrayList;
import java.util.Arrays;

import fr.upem.test.ADObject;
import fr.upem.test.DbManager;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DbManager dbManager;
	private String[] objects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deleteDatabase(DbManager.DB_NAME);

		dbManager = new DbManager();
		dbManager.onCreate();

		ContentValues values = new ContentValues();
		values.put(ADObject.DBObject.NAME, "Objet1");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);
		values.put(ADObject.DBObject.NAME, "Objet2");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);
		values.put(ADObject.DBObject.NAME, "Objet3");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);

		ArrayList<String> result = new ArrayList<String>();
		String columns[] = new String[] { ADObject.DBObject.ID, ADObject.DBObject.NAME };
		Cursor cursor = getContentResolver().query(ADObject.DBObject.CONTENT_URI, columns, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex(ADObject.DBObject.NAME));
				result.add(name);
			} while (cursor.moveToNext());

			cursor.close();
		}

		objects = result.toArray(new String[result.size()]);
		Arrays.sort(objects);

		final ListView listview = (ListView) findViewById(R.id.objectList);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				return false;
			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, objects);
		listview.setAdapter(adapter);
		registerForContextMenu(listview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.objectList) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(objects[info.position]);

			String[] menuItems = getResources().getStringArray(R.array.objectListMenu);

			for (int i = 0; i<menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		int menuItemIndex = item.getItemId();

		String[] menuItems = getResources().getStringArray(R.array.objectListMenu);
		String menuItemName = menuItems[menuItemIndex];
		String listItemName = objects[info.position];

		System.out.println(menuItemIndex);
		System.out.println("Selected " + menuItemName + " for item " + listItemName);

		switch (menuItemIndex) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				break;
		}

		return true;
	}
}