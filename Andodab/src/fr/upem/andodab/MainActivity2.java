package fr.upem.andodab;

import java.util.Comparator;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.dao.DAODictionary;
import fr.upem.andodab.dao.DAOFloat;
import fr.upem.andodab.dao.DAOInteger;
import fr.upem.andodab.dao.DAOString;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBDictionary;
import fr.upem.andodab.db.DBFloat;
import fr.upem.andodab.db.DBInteger;
import fr.upem.andodab.db.DBManager;
import fr.upem.andodab.db.DBObject;
import fr.upem.andodab.db.DBString;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity2 extends Activity {
	private long currentId;
	private String currentName;
	private DBManager dbManager;
	private DAOCommon daoCommon;
	private DAODictionary daoDictionary;
	private DAOFloat daoFloat;
	private DAOInteger daoInteger;
	private DAOString daoString;
	private TextView title;
	private Button addButtonKey;
	private Button addButtonObject;
	private ListView keyList;
	private ListAdapter2 adapter2;
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
					return c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
				}
			});

			this.setNotifyOnChange(true);
		}
	}

	private class ListAdapter2 extends ArrayAdapter<DBDictionary> {
		public ListAdapter2(Context context, int resource, List<DBDictionary> objects) {
			super(context, resource, objects);
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();

			this.setNotifyOnChange(false);

			this.sort(new Comparator<DBDictionary>() {
				@Override
				public int compare(DBDictionary c1, DBDictionary c2) {
					return c1.getKey().toLowerCase().compareTo(c2.getKey().toLowerCase());
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
		currentName = bundle.getString("name");

		dbManager = new DBManager();
		dbManager.onCreate();

		daoCommon = new DAOCommon(getContentResolver());
		daoDictionary = new DAODictionary(getContentResolver());

		daoFloat = new DAOFloat(getContentResolver());
		daoInteger = new DAOInteger(getContentResolver());
		daoString = new DAOString(getContentResolver());

		title = (TextView) findViewById(R.id.titleObjectList);
		title.setText(currentName);

		addButtonKey = (Button) findViewById(R.id.addButtonKey);
		addButtonKey.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				alert.setTitle(R.string.button_add_key_title);

				LinearLayout layout = new LinearLayout(MainActivity2.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView labelName = new TextView(MainActivity2.this);
				labelName.setText(R.string.button_add_key_name);
				layout.addView(labelName);

				final EditText inputName = new EditText(MainActivity2.this); 
				layout.addView(inputName);

				final TextView labelType = new TextView(MainActivity2.this);
				labelType.setText(R.string.button_add_key_type);
				layout.addView(labelType);

				final Spinner spinnerType = new Spinner(MainActivity2.this);
				String[] choices = { "Objet", "Float", "Integer", "String" };
				ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_spinner_item, choices);
				spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerType.setAdapter(spinnerAdapter);
				layout.addView(spinnerType);

				final TextView labelObjects = new TextView(MainActivity2.this);
				labelObjects.setText(R.string.button_add_key_objects);
				layout.addView(labelObjects);

				final Spinner spinnerObjects = new Spinner(MainActivity2.this);
				List<DBCommon> objectsList = daoCommon.findByAncestor(1L);
				ArrayAdapter<DBCommon> spinnerAdapter2 = new ArrayAdapter<DBCommon>(MainActivity2.this, android.R.layout.simple_spinner_item, objectsList);
				spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerObjects.setAdapter(spinnerAdapter2);
				layout.addView(spinnerObjects);

				final TextView labeValue = new TextView(MainActivity2.this);
				labeValue.setText(R.string.button_add_key_value);
				labeValue.setVisibility(View.GONE);
				layout.addView(labeValue);

				final EditText inputValue = new EditText(MainActivity2.this);
				inputValue.setVisibility(View.GONE);
				layout.addView(inputValue);

				spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
						switch (position) {
						case 0:
							labelObjects.setVisibility(View.VISIBLE);
							spinnerObjects.setVisibility(View.VISIBLE);
							labeValue.setVisibility(View.GONE);
							inputValue.setVisibility(View.GONE);

							break;
						default:
							labelObjects.setVisibility(View.GONE);
							spinnerObjects.setVisibility(View.GONE);
							labeValue.setVisibility(View.VISIBLE);
							inputValue.setVisibility(View.VISIBLE);

							break;
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

				alert.setView(layout);

				alert.setPositiveButton(R.string.button_edit_object_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = inputName.getText().toString();
						String type = spinnerType.getSelectedItem().toString();
						Object objectSelected = spinnerObjects.getSelectedItem();
						String value = inputValue.getText().toString();

						if (name.isEmpty() || (!type.equals("Objet") && value.isEmpty())) {
							Toast toast = Toast.makeText(getBaseContext(), R.string.error_message, Toast.LENGTH_SHORT);
							toast.show();

							return;
						}

						DBDictionary dictionary = null;
						DBObject object = null;

						if (type.equals("Objet")) {
							object = (DBObject) objectSelected;
						} else {
							if (type.equals("Float")) {
								DBFloat dbFloat = new DBFloat(currentId, Float.parseFloat(value));
								daoFloat.create(dbFloat);
								object = dbFloat;
							} else if (type.equals("Integer")) {
								DBInteger dbInteger = new DBInteger(currentId, Long.parseLong(value));
								daoInteger.create(dbInteger);
								object = dbInteger;
							} else {
								DBString dbString = new DBString(currentId, value);
								daoString.create(dbString);
								object = dbString;
							}
						}

						dictionary = new DBDictionary(currentId, name, object.getId(), object.toString());

						try {
							daoDictionary.create(dictionary);

							adapter2.add(dictionary);
							adapter2.notifyDataSetChanged();
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

		List<DBDictionary> attributesList = daoDictionary.findByObject(currentId);

		adapter2 = null;

		if (attributesList.size() > 0) {
			adapter2 = new ListAdapter2(MainActivity2.this, android.R.layout.simple_list_item_1, attributesList);
		}

		keyList = (ListView) findViewById(R.id.keyList);
		keyList.setBackgroundColor(Color.LTGRAY);
		keyList.setAdapter(adapter2);
		registerForContextMenu(keyList);

		addButtonObject = (Button) findViewById(R.id.addButtonObject);
		addButtonObject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				alert.setTitle(R.string.button_add_key_title);

				LinearLayout layout = new LinearLayout(MainActivity2.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView labelName = new TextView(MainActivity2.this);
				labelName.setText(R.string.button_add_object_name);
				layout.addView(labelName);

				final EditText inputName = new EditText(MainActivity2.this); 
				layout.addView(inputName);

				final TextView labelSealed = new TextView(MainActivity2.this);
				labelSealed.setText(R.string.button_add_object_sealed);
				layout.addView(labelSealed);

				final Spinner spinnerSealed = new Spinner(MainActivity2.this);
				String[] choices = { getResources().getString(R.string.no), getResources().getString(R.string.yes) };
				ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_spinner_item, choices);
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

						DBCommon common = new DBCommon(currentId, name, sealed);

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

		List<DBCommon> commonsList = daoCommon.findByAncestor(currentId);

		adapter = null;

		if (commonsList.size() > 0) {
			adapter = new ListAdapter(MainActivity2.this, android.R.layout.simple_list_item_1, commonsList);
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

		adapter = new ListAdapter(MainActivity2.this, android.R.layout.simple_list_item_1, commonsList);
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
		} else if (v.getId() == R.id.keyList) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(adapter.getItem(info.position).getName());

			String[] menuItems = getResources().getStringArray(R.array.keyListMenu);

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
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
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

			final Spinner spinnerSealed = new Spinner(MainActivity2.this);
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
}
