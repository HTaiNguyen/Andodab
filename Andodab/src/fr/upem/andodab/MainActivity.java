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
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
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
		setContentView(R.layout.activity_main);

		deleteDatabase(DBManager.DB_NAME);

		dbManager = new DBManager();
		dbManager.onCreate();

		daoCommon = new DAOCommon(getContentResolver());


			DBInit();


		addButtonObject = (Button) findViewById(R.id.addButtonObject);
		addButtonObject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				alert.setTitle(R.string.button_add_object_title);

				LinearLayout layout = new LinearLayout(MainActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView labelName = new TextView(MainActivity.this);
				labelName.setText(R.string.button_add_object_name);
				layout.addView(labelName);

				final EditText inputName = new EditText(MainActivity.this); 
				layout.addView(inputName);

				final TextView labelSealed = new TextView(MainActivity.this);
				labelSealed.setText(R.string.button_add_object_sealed);
				layout.addView(labelSealed);

				final Spinner spinnerSealed = new Spinner(MainActivity.this);
				String[] choices = { getResources().getString(R.string.no), getResources().getString(R.string.yes) };
				ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, choices);
				spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerSealed.setAdapter(spinnerAdapter);
				layout.addView(spinnerSealed);

				alert.setView(layout);

				alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = inputName.getText().toString();
						boolean sealed = spinnerSealed.getSelectedItem().equals(getResources().getString(R.string.yes)) ? true : false;

						if (name.isEmpty()) {
							Toast toast = Toast.makeText(getBaseContext(), R.string.error_message, Toast.LENGTH_SHORT);
							toast.show();

							return;
						}

						DBCommon common = new DBCommon(1L, name, sealed);

						try {
							daoCommon.create(common);

							adapter.add(common);
							adapter.notifyDataSetChanged();
						} catch (Exception e) {
							Toast toast = Toast.makeText(getBaseContext(), R.string.error_message, Toast.LENGTH_SHORT);
							toast.show();
						}
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

		List<DBCommon> commonsList = daoCommon.findByAncestor(1L);

		adapter = null;

		if (commonsList.size() > 0) {
			adapter = new ListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, commonsList);
		}

		listViewObjects = (ListView) findViewById(R.id.objectList);
		listViewObjects.setBackgroundColor(Color.LTGRAY);
		listViewObjects.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				long id = adapter.getItem(position).getId();
				String name = adapter.getItem(position).getName();

				Intent intent = new Intent(getBaseContext(), MainActivity2.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				startActivity(intent);
			}
		});

		adapter = new ListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, commonsList);
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
		final DBCommon common = adapter.getItem(info.position);

		switch (item.getItemId()) {
		case 0:
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
			alert.setTitle(R.string.button_edit_object_message);

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);

			final TextView labelName = new TextView(this);
			labelName.setText(R.string.button_add_object_name);
			layout.addView(labelName);

			final EditText inputName = new EditText(this); 
			inputName.setText(common.getName());
			layout.addView(inputName);

			final TextView labelSealed = new TextView(this);
			labelSealed.setText(R.string.button_add_object_sealed);
			layout.addView(labelSealed);

			final Spinner spinnerSealed = new Spinner(MainActivity.this);
			String[] choices = { getResources().getString(R.string.no), getResources().getString(R.string.yes) };
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choices); 
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			spinnerSealed.setAdapter(spinnerAdapter);
			String sealed = common.isSealed() ? getResources().getString(R.string.yes) : getResources().getString(R.string.no);
			int spinnerPosition = spinnerAdapter.getPosition(sealed);
			spinnerSealed.setSelection(spinnerPosition);
			layout.addView(spinnerSealed);

			alert.setView(layout);

			alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String name = inputName.getText().toString();

					common.setName(name);

					if (spinnerSealed.getSelectedItem().equals(getResources().getString(R.string.yes))) {
						common.setSealed(true);
					} else {
						common.setSealed(false);
					}

					try {
						daoCommon.udpate(common);

						adapter.getItem(info.position).setName(name);
						adapter.notifyDataSetChanged();
					} catch (Exception e) {
						Toast toast = Toast.makeText(getBaseContext(), R.string.error_message, Toast.LENGTH_SHORT);
						toast.show();
					}
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
			try {
				daoCommon.delete(common);

				adapter.remove(common);
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				Toast toast = Toast.makeText(getBaseContext(), R.string.error_message, Toast.LENGTH_SHORT);
				toast.show();
			}

			break;
		default:
			break;
		}

		return true;
	}

	public boolean DBInit() {

			daoCommon.create(new DBCommon(null, "Root", false));
			daoCommon.create(new DBCommon(1L, "Float", false));
			daoCommon.create(new DBCommon(1L, "Int", false));
			daoCommon.create(new DBCommon(1L, "String", false));


		return true;
	}
}
