package fr.upem.andodab.table;

import fr.upem.andodab.db.DBManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TableDictionary {
	public static final String TABLE_NAME = "dictionary";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String COL_ID = "id";
	public static final String COL_OBJECT_ID = "object_id";
	public static final String COL_KEY = "key";
	public static final String COL_VALUE_ID = "value_id";
	
	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + 
					COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					COL_OBJECT_ID + " INTEGER, " +
					COL_KEY + " VARCHAR(255), " + 
					COL_VALUE_ID + " INTEGER" +
					");";

	public static final String SQL_DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TableDictionary.TABLE_NAME;

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
		      int newVersion) {
		    Log.w(TABLE_NAME , "Upgrading database from version "
		        + oldVersion + " to " + newVersion
		        + ", which will destroy all old data");
		    database.execSQL(SQL_DROP_TABLE);
		    onCreate(database);
	}

}