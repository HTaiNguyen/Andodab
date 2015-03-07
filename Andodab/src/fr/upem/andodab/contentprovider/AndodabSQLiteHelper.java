package fr.upem.andodab.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AndodabSQLiteHelper extends SQLiteOpenHelper {

	public static final String OBJECT_TABLE_NAME = "object";
	public static final String OBJECT_KEY = "id";
	public static final String OBJECT_NAME = "name";
	public static final String OBJECT_VALUE = "value";
	public static final String OBJECT_TYPE = "type";
	public static final String OBJECT_PARENT_ID = "parent_id";
	
	private static final String OBJECT_DROP_TABLE = "DROP TABLE IF EXISTS " + OBJECT_TABLE_NAME;
	public static final String OBJECT_CREATE_TABLE = 
			"CREATE TABLE " + OBJECT_TABLE_NAME + " (" + 
					OBJECT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					OBJECT_NAME + " TEXT, " +
					OBJECT_VALUE + " TEXT, " + 
					OBJECT_PARENT_ID + "INTEGER FOREIGN KEY);";
	
	private final static String DB_NAME = "andodab.db";
	private final static int DB_VERSION = 1;
	
	public AndodabSQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public AndodabSQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(OBJECT_CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(OBJECT_DROP_TABLE);
		onCreate(db);
	}
}
