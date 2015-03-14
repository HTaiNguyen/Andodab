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

	private Long id;
	private Long ownerId;
	private String key;
	private Long valueId;

	public DBDictionary() {
	}

	public DBDictionary(Long ownerId, String key, Long valueId) {
		this.ownerId  = ownerId;
		this.key = key;
		this.valueId = valueId;
	}

	@Override
	public String toString() {
		return key + " = ?";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwner(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getValueId() {
		return valueId;
	}

	public void setValue(Long valueId) {
		this.valueId = valueId;
	}
}