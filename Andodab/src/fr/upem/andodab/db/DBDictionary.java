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
	public static final String VALUE_TYPE = "value_type";
}