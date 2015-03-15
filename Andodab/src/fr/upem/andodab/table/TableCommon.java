package fr.upem.andodab.table;

import fr.upem.andodab.db.DBManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TableCommon extends TableObject {
	public static final String TABLE_NAME = "common";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;
	
	
	public static final String COL_NAME = "name";
	public static final String COL_SEALED = "sealed";
	
	public static final String ROOT_NAME = "Root";
	public static final String ROOT_ID = "1";
	
	public static final String INT_NAME = "Int";
	public static final String INT_ID = "2";
	
	public static final String FLOAT_NAME = "Float";
	public static final String FLOAT_ID = "3";
	
	public static final String STRING_NAME = "String" ;
	public static final String STRING_ID = "4";

	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + 
					TableCommon.COL_ID + " INTEGER, " +
					TableCommon.COL_ANCESTOR_ID + " INTEGER, " +
					TableCommon.COL_NAME + " VARCHAR(255) NOT NULL UNIQUE COLLATE NOCASE, " + 
					TableCommon.COL_SEALED + " BOOLEAN," + 
					"PRIMARY KEY (" + COL_ID + ")" +
					");";
	
	public static final String SQL_INIT_TABLE = 
				"INSERT OR ABORT INTO" + TABLE_NAME + "("
					+ COL_ID + ", "
					+ COL_ANCESTOR_ID + ", "
					+ COL_NAME + ", "
					+ COL_SEALED + ", "
					+ "VALUES("+ ROOT_ID + ", " + "NULL" + ", " + ROOT_NAME + ", "+ "1" + ");"
				+ "INSERT OR ABORT INTO" + TABLE_NAME + "("
					+ COL_ID + ", "
					+ COL_ANCESTOR_ID + ", "
					+ COL_NAME + ", "
					+ COL_SEALED + ", "
					+ "VALUES("+ INT_ID + ", " + ROOT_ID + ", " + INT_NAME + ", "+ "1" + ");"
				+ "INSERT OR ABORD INTO" + TABLE_NAME + "("
					+ COL_ID + ", "
					+ COL_ANCESTOR_ID + ", "
					+ COL_NAME + ", "
					+ COL_SEALED + ", "
					+ "VALUES("+ FLOAT_ID + ", " + ROOT_ID + ", " + FLOAT_NAME + ", "+ "1" + ");"
				+ "INSERT OR ABORD INTO" + TABLE_NAME + "("
					+ COL_ID + ", "
					+ COL_ANCESTOR_ID + ", "
					+ COL_NAME + ", "
					+ COL_SEALED + ", "
					+ "VALUES("+ STRING_ID + ", " + ROOT_ID + ", " + STRING_NAME + ", "+ "1" + ");";
	
	public static final String SQL_DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE);
		database.execSQL(SQL_INIT_TABLE);
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