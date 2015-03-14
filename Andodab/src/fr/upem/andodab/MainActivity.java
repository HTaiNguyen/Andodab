package fr.upem.andodab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DBManager dbManager;
	private DBCommon[] objects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deleteDatabase(DBManager.DB_NAME);

		dbManager = new DBManager();
		dbManager.onCreate();

		final DAOCommon daoCommon = new DAOCommon(getContentResolver());
		daoCommon.create(new DBCommon(null, "Root", false));
		daoCommon.create(new DBCommon(1L, "Float", false));
		daoCommon.create(new DBCommon(1L, "Int", false));
		daoCommon.create(new DBCommon(1L, "String", false));
		daoCommon.create(new DBCommon(1L, "Food", false));
		daoCommon.create(new DBCommon(1L, "Animal", false));

		List<DBCommon> results = daoCommon.findByAncestor(1L);

		objects = results.toArray(new DBCommon[results.size()]);
		Arrays.sort(objects);

		final Button addButtonObject = (Button) findViewById(R.id.addButtonObject);
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
						long id = 0;
						String name = input.getText().toString();

						DBCommon common = new DBCommon(1L, name, false);
						id = common.getId();

						daoCommon.create(common);

						DBCommon[] newObjects = Arrays.copyOf(objects, objects.length + 1);
						newObjects[objects.length] = common;
						objects = newObjects;
						Arrays.sort(objects);
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

		final ListView listview = (ListView) findViewById(R.id.objectList);

		String[] objectsName = new String[objects.length];
		
		for (int i = 0; i < objects.length; i++) {
			objectsName[i] = objects[i].getName();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, objectsName);
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
			menu.setHeaderTitle(objects[info.position].getName());

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
		String listItemName = objects[info.position].getName();

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