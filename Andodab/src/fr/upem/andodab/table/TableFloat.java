package fr.upem.andodab.table;

import fr.upem.andodab.db.DBManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TableFloat extends TableObject{
	public static final String TABLE_NAME = "float";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;
	
	public static final String COL_VALUE = "value";
	
	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + 
					TableFloat.COL_ID + " INTEGER, " + 
					TableFloat.COL_ANCESTOR_ID + " INTEGER, " +
					TableFloat.COL_VALUE + " REAL, " + 
					"PRIMARY KEY (" + COL_ID + ")" +
					");";
	public static final String SQL_DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	
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