package fr.upem.andodab;

import fr.upem.test.DbManager;
import fr.upem.test.Object;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DbManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbManager = new DbManager();
		dbManager.onCreate();
		
		ContentValues values = new ContentValues();
		values.put(Object.OBJECT_NAME, "Objet1");
		getContentResolver().insert(DbManager.CONTENT_URI, values);
		values.put(Object.OBJECT_NAME, "Objet2");
		getContentResolver().insert(DbManager.CONTENT_URI, values);
		values.put(Object.OBJECT_NAME, "Objet3");
		getContentResolver().insert(DbManager.CONTENT_URI, values);
		
		String columns[] = new String[] { Object.OBJECT_ID, Object.OBJECT_NAME };
		Cursor cursor = getContentResolver().query(DbManager.CONTENT_URI, columns, null, null, null);
		
		if (cursor.moveToFirst()) {
			String name = null;
			
			do {
				name = cursor.getString(cursor.getColumnIndex(Object.OBJECT_ID)) + " " + cursor.getString(cursor.getColumnIndex(Object.OBJECT_NAME));
				Toast.makeText(this, name + " ", Toast.LENGTH_LONG).show();
			} while (cursor.moveToNext());
		}
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