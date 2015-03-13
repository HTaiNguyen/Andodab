package fr.upem.andodab.db;

import android.net.Uri;

public class DBDictionary {
	public static final String TABLE_NAME = "dictionary";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String OBJECT_ID = "object_id";
	public static final String KEY = "key";
	public static final String VALUE_ID = "value_id";
	
	private long id;
	private DBObject owner;
	private String key;
	private DBObject value;
	
	public DBDictionary() {
	}
	
	public DBDictionary(DBObject owner, String key, DBObject value) {
		this.owner  = owner;
		this.key = key;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DBObject getOwner() {
		return owner;
	}

	public void setOwner(DBObject owner) {
		this.owner = owner;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DBObject getValue() {
		return value;
	}

	public void setValue(DBObject value) {
		this.value = value;
	}
	
	
}