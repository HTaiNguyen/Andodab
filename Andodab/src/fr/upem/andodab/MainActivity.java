package fr.upem.andodab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DBManager dbManager;
	private DAOCommon daoCommon;
	private DBCommon[] objects;
	private Button addButtonObject;
	private ListView listViewObjects;
	private ListAdapter adapter;

	private class ListAdapter extends ArrayAdapter<String> {
		public ListAdapter(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();

			this.setNotifyOnChange(false);

			this.sort(new Comparator<String>() {
				@Override
				public int compare(String lhs, String rhs) {
					return lhs.compareTo(rhs);
				}
			});

			this.setNotifyOnChange(true);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deleteDatabase(DBManager.DB_NAME);

		dbManager = new DBManager();
		dbManager.onCreate();

		daoCommon = new DAOCommon(getContentResolver());

		List<DBCommon> results = daoCommon.findByAncestor(1L);

		objects = results.toArray(new DBCommon[results.size()]);

		addButtonObject = (Button) findViewById(R.id.addButtonObject);
		addButtonObject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				alert.setTitle(R.string.button_add_object_title);
				alert.setMessage(R.string.button_add_object_message);
				final EditText input = new EditText(MainActivity.this);
				alert.setView(input);
				alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = input.getText().toString();

						DBCommon common = new DBCommon(1L, name, false);
						daoCommon.create(common);

						List<DBCommon> results = daoCommon.findByAncestor(1L);

						objects = results.toArray(new DBCommon[results.size()]);

						adapter.add(name);
						adapter.notifyDataSetChanged();
					}
				});

				alert.setNegativeButton(R.string.button_edit_object_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}
		});

		listViewObjects = (ListView) findViewById(R.id.objectList);
		listViewObjects.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			}
		});

		ArrayList<String> objectsName = new ArrayList<>();

		for (int i = 0; i < objects.length; i++) {
			objectsName.add(objects[i].getName());
		}


		adapter = new ListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, objectsName);
		listViewObjects.setAdapter(adapter);
		registerForContextMenu(listViewObjects);
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
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.objectList) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(objects[info.position].getName());

			String[] menuItems = getResources().getStringArray(R.array.objectListMenu);

			for (int i = 0; i<menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		int menuItemIndex = item.getItemId();

		String listItemName = objects[info.position].getName();

		switch (menuItemIndex) {
		case 0:
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
			alert.setTitle(R.string.button_edit_object_message);
			alert.setMessage(R.string.button_edit_object_message);
			final EditText input = new EditText(MainActivity.this);
			input.setText(listItemName);
			alert.setView(input);
			alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					DBCommon common = objects[info.position];
					String name = input.getText().toString();

					common.setName(name);
					daoCommon.udpate(common);
				}
			});

			alert.setNegativeButton(R.string.button_edit_object_cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

			alert.show();

			break;
		case 1:
			DBCommon common = objects[info.position];

			daoCommon.delete(common);

			adapter.remove(common.getName());
			adapter.notifyDataSetChanged();

			break;
		default:
			break;
		}

		return true;
	}
}
