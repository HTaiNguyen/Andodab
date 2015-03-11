package fr.upem.test;

import java.util.HashMap;

import android.net.Uri;
import android.provider.BaseColumns;

public class ADObject {
	private long id;
	private String name;
	private ADObject ancestor;
	private HashMap<String, ADObject> dictionary;
	private boolean sealed;

	public static final class DBObject implements BaseColumns {
		public static final String TABLE_NAME = "object";
		public static final Uri CONTENT_URI = Uri.parse("content://" + DbManager.AUTHORITY + "/" + TABLE_NAME);
		public static final String MIME = "vnd.android.cursor.item/vnd." + DbManager.AUTHORITY + "." + TABLE_NAME;

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ANCESTOR_ID = "ancestor_id";
		public static final String SEALED = "sealed";

		public static final String SQL_CREATE_TABLE = 
				"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + 
						ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						NAME + " VARCHAR(255)" + 
						ANCESTOR_ID + "INTEGER" +
						SEALED + "BOOLEAN" +
						");";
		public static final String SQL_DROP_TABLE = 
				"DROP TABLE IF EXISTS " + TABLE_NAME;
	}

	public ADObject(long id, String name, ADObject ancestor, boolean sealed) {
		this.id = id;
		this.name = name; 
		this.ancestor = ancestor; 
		this.dictionary = new HashMap<String, ADObject>();
		this.sealed = sealed;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ADObject getAncestor() {
		return ancestor;
	}

	public HashMap<String, ADObject> getDictionary() {
		return dictionary;
	}

	public boolean isSealed() {
		return sealed;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAncestor(ADObject ancestor) {
		this.ancestor = ancestor;
	}

	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}

	public void addKeyValue(String key, ADObject value) {
		dictionary.put(key, value);
	}

	@Override
	public String toString() {
		return name;
	}
}