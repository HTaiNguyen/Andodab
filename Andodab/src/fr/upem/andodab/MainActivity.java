package fr.upem.andodab;

import java.util.Arrays;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBManager;
import android.app.Activity;
import android.app.AlertDialog;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//deleteDatabase(DBManager.DB_NAME);

		dbManager = new DBManager();
		dbManager.onCreate();

		daoCommon = new DAOCommon(getContentResolver());

		List<DBCommon> results = daoCommon.findByAncestor(1L);

		objects = results.toArray(new DBCommon[results.size()]);
		Arrays.sort(objects);

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
						Arrays.sort(objects);

						updateListView();
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



		updateListView();
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

	public void updateListView() {
		String[] objectsName = new String[objects.length];

		for (int i = 0; i < objects.length; i++) {
			objectsName[i] = objects[i].getName();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, objectsName);
		listViewObjects.setAdapter(adapter);

		registerForContextMenu(listViewObjects);
	}
}