package fr.upem.andodab.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DBObject implements BaseColumns {
	public static final String TABLE_NAME = "object";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String ANCESTOR_ID = "ancestor_id";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String SEALED = "sealed";
}