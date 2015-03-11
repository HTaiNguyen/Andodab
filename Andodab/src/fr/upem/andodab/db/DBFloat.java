package fr.upem.andodab.db;

import android.net.Uri;

public class DBFloat {
	public static final String TABLE_NAME = "float";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String ANCESTOR_ID = "ancestor_id";
	public static final String VALUE = "value";
}