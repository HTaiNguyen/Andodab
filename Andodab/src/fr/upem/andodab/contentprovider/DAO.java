package fr.upem.andodab.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAO {
	protected final static int VERSION = 1;
	protected final static String DB_NAME = "andodab.db";
	
	protected SQLiteDatabase andodabDB = null;
	protected AndodabSQLiteHelper andodabSQLiteHelper = null;
	
	public DAO(Context context) {
		this.andodabSQLiteHelper = new AndodabSQLiteHelper(context, DB_NAME, null, VERSION);
	}
	
	public SQLiteDatabase open() {
		andodabDB = andodabSQLiteHelper.getWritableDatabase();
		return andodabDB;
	}
	
	public void close() {
		andodabDB.close();
	}
	
	public SQLiteDatabase getDB() {
		return andodabDB;
	}
}
