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
	private long ownerId;
	private String key;
	private long valueId;
	
	public DBDictionary() {
	}
	
	public DBDictionary(long ownerId, String key, long valueId) {
		this.ownerId  = ownerId;
		this.key = key;
		this.valueId = valueId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwner(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getValueId() {
		return valueId;
	}

	public void setValue(long valueId) {
		this.valueId = valueId;
	}
	
	
}