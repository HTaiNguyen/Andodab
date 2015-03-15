package fr.upem.andodab.table;

import fr.upem.andodab.db.DBManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TableInteger extends TableObject{
	public static final String TABLE_NAME = "integer";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;
	
	public static final String COL_ID = "id";
	public static final String COL_VALUE = "value";
	
	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TableInteger.TABLE_NAME + " (" + 
					TableInteger.COL_ID + " INTEGER, " + 
					TableInteger.COL_VALUE + " INTEGER, " + 
					"PRIMARY KEY (" + TableInteger.COL_ID + "), " +
					"FOREIGN KEY (" + TableInteger.COL_ID + ") REFERENCES " + TABLE_NAME + "(" + COL_ID + ")" +
					");";
	public static final String SQL_DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;

	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE);
	}

}