package fr.upem.andodab;

import java.util.ArrayList;

import fr.upem.test.ADObject;
import fr.upem.test.DbManager;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DbManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbManager = new DbManager();
		dbManager.onCreate();

		ContentValues values = new ContentValues();
		values.put(ADObject.DBObject.NAME, "Objet1");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);
		values.put(ADObject.DBObject.NAME, "Objet2");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);
		values.put(ADObject.DBObject.NAME, "Objet3");
		getContentResolver().insert(ADObject.DBObject.CONTENT_URI, values);

		String columns[] = new String[] { ADObject.DBObject.ID, ADObject.DBObject.NAME };
		Cursor cursor = getContentResolver().query(ADObject.DBObject.CONTENT_URI, columns, null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		if (cursor.moveToFirst()) {
			String name = null;

			do {
				name = cursor.getString(cursor.getColumnIndex(ADObject.DBObject.ID)) + " " + cursor.getString(cursor.getColumnIndex(ADObject.DBObject.ID));
				result.add(name);
			} while (cursor.moveToNext());
		}

		final ListView listview = (ListView) findViewById(R.id.listview);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, result);
		listview.setAdapter(adapter);
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
}