package fr.upem.andodab;

import java.util.Comparator;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity2 extends Activity {
	private long currentId;
	private DBManager dbManager;
	private DAOCommon daoCommon;
	private Button addButtonObject;
	private ListView listViewObjects;
	private ListAdapter adapter;

	private class ListAdapter extends ArrayAdapter<DBCommon> {
		public ListAdapter(Context context, int resource, List<DBCommon> objects) {
			super(context, resource, objects);
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();

			this.setNotifyOnChange(false);

			this.sort(new Comparator<DBCommon>() {
				@Override
				public int compare(DBCommon c1, DBCommon c2) {
					return c1.getName().compareTo(c2.getName());
				}
			});

			this.setNotifyOnChange(true);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		Bundle bundle = getIntent().getExtras();
		currentId = bundle.getLong("id");

		dbManager = new DBManager();
		dbManager.onCreate();

		daoCommon = new DAOCommon(getContentResolver());

		addButtonObject = (Button) findViewById(R.id.addButtonObject);
		addButtonObject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				alert.setTitle(R.string.button_add_object_title);
				alert.setMessage(R.string.button_add_object_message);
				final EditText input = new EditText(MainActivity2.this);
				alert.setView(input);
				alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = input.getText().toString();

						DBCommon common = new DBCommon(currentId, name, false);
						daoCommon.create(common);

						adapter.add(common);
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
				long id = adapter.getItem(position).getId();

				Intent intent = new Intent(getBaseContext(), MainActivity2.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});

		adapter = new ListAdapter(MainActivity2.this, android.R.layout.simple_list_item_1, daoCommon.findByAncestor(currentId));
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
			menu.setHeaderTitle(adapter.getItem(info.position).getName());

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

		final String listItemName = adapter.getItem(info.position).getName();

		switch (menuItemIndex) {
		case 0:
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
			alert.setTitle(R.string.button_edit_object_message);
			alert.setMessage(R.string.button_edit_object_message);
			final EditText input = new EditText(MainActivity2.this);
			input.setText(listItemName);
			alert.setView(input);
			alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					DBCommon common = adapter.getItem(info.position);
					String name = input.getText().toString();

					common.setName(name);
					daoCommon.udpate(common);

					adapter.getItem(info.position).setName(name);
					adapter.notifyDataSetChanged();
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
			DBCommon common = adapter.getItem(info.position);

			daoCommon.delete(common);

			adapter.remove(common);
			adapter.notifyDataSetChanged();

			break;
		default:
			break;
		}

		return true;
	}
}
