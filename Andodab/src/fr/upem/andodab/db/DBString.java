package fr.upem.andodab.db;

import android.net.Uri;

public class DBString extends DBObject{
	public static final String TABLE_NAME = "string";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String VALUE = "value";
	
	private String value;

	public DBString(long ancestorId, String value) {
		super(ancestorId);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}