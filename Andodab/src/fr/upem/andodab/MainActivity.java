package fr.upem.andodab;

import fr.upem.test.AndodabContentProvider;
import fr.upem.test.Object;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	private void displayContentProvider() {
		String columns[] = new String[] { Object.OBJECT_ID, Object.OBJECT_NAME };
		Uri mContacts = AndodabContentProvider.CONTENT_URI;
		Cursor cur = managedQuery(mContacts, columns, null, null, null);
		Toast.makeText(MainActivity.this, cur.getCount() + "", Toast.LENGTH_LONG).show();

		if (cur.moveToFirst()) {
			String name = null;
			do {
				name = cur.getString(cur.getColumnIndex(Object.OBJECT_ID)) + " " + cur.getString(cur.getColumnIndex(Object.OBJECT_NAME));
				Toast.makeText(this, name + " ", Toast.LENGTH_LONG).show();
			} while (cur.moveToNext());
		}
	}

	private void insertRecords() {
		ContentValues contact = new ContentValues();
		contact.put(Object.OBJECT_NAME, "Android");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI, contact);

		contact.clear();
		contact.put(Object.OBJECT_NAME, "Java");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI, contact);

		contact.clear();
		contact.put(Object.OBJECT_NAME, "C++");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI, contact);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		insertRecords();
		displayContentProvider();
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