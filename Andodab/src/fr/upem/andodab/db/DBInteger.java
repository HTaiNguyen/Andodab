package fr.upem.andodab.db;

import android.net.Uri;

public class DBInteger extends DBObject{
	public static final String TABLE_NAME = "integer";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String VALUE = "value";
	
	private Long value;
	
	public DBInteger() {
	}
	
	public DBInteger(Long ancestorId, Long value) {
		super(ancestorId);
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
}