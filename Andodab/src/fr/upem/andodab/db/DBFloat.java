package fr.upem.andodab.db;

import android.net.Uri;

public class DBFloat extends DBObject{
	public static final String TABLE_NAME = "float";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String VALUE = "value";
	
	private float value;
	
	public DBFloat() {
	}
	
	public DBFloat(long ancestorId, float value) {
		super(ancestorId);
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	
}