package fr.upem.andodab.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DBManager extends ContentProvider {
	public static final String AUTHORITY = "fr.upem.test.DbManager";
	public static final String DB_NAME = "andodab.db";
	public static final int DB_VERSION = 1;

	public static final String SQL_CREATE_TABLE_OBJECT = 
			"CREATE TABLE IF NOT EXISTS " + DBObject.TABLE_NAME + " (" + 
					DBObject.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBObject.ANCESTOR_ID + " INTEGER, " +
					DBObject.NAME + " VARCHAR(255), " + 
					DBObject.TYPE + " VARCHAR(255), " + 
					DBObject.SEALED + " BOOLEAN" + 
					");";
	public static final String SQL_DROP_TABLE_OBJECT = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_DICTIONARY = 
			"CREATE TABLE IF NOT EXISTS " + DBDictionary.TABLE_NAME + " (" + 
					DBDictionary.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBDictionary.OBJECT_ID + " INTEGER, " +
					DBDictionary.KEY + " VARCHAR(255), " + 
					DBDictionary.VALUE_TYPE + " VARCHAR(255), " + 
					DBDictionary.VALUE_ID + " INTEGER" + 
					");";
	public static final String SQL_DROP_TABLE_DICTIONARY = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_FLOAT = 
			"CREATE TABLE IF NOT EXISTS " + DBFloat.TABLE_NAME + " (" + 
					DBFloat.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBFloat.ANCESTOR_ID + " INTEGER, " + 
					DBFloat.VALUE + " INTEGER" + 
					");";
	public static final String SQL_DROP_TABLE_FLOAT = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_INTEGER = 
			"CREATE TABLE IF NOT EXISTS " + DBInteger.TABLE_NAME + " (" + 
					DBInteger.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBInteger.ANCESTOR_ID + " INTEGER, " + 
					DBInteger.VALUE + " INTEGER" + 
					");";
	public static final String SQL_DROP_TABLE_INTEGER = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_STRING = 
			"CREATE TABLE IF NOT EXISTS " + DBString.TABLE_NAME + " (" + 
					DBString.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBString.ANCESTOR_ID + " INTEGER, " +
					DBString.VALUE + " INTEGER" + 
					");";
	public static final String SQL_DROP_TABLE_STRING = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	private Context context;
	private DbHelper dbHelper;

	private final class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_TABLE_OBJECT);
			db.execSQL(SQL_CREATE_TABLE_DICTIONARY);
			db.execSQL(SQL_CREATE_TABLE_FLOAT);
			db.execSQL(SQL_CREATE_TABLE_INTEGER);
			db.execSQL(SQL_CREATE_TABLE_STRING);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DROP_TABLE_OBJECT);
			db.execSQL(SQL_DROP_TABLE_DICTIONARY);
			db.execSQL(SQL_DROP_TABLE_FLOAT);
			db.execSQL(SQL_DROP_TABLE_INTEGER);
			db.execSQL(SQL_DROP_TABLE_STRING);

			onCreate(db);
		}
	}

	@Override
	public boolean onCreate() {
		this.context = getContext();

		dbHelper = new DbHelper(context);

		return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,	String[] selectionArgs, String sortOrder) {
		long id = getId(uri);

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (id < 0) {
			return db.query(DBObject.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
		} else {
			return db.query(DBObject.TABLE_NAME, projection, DBObject.ID + "=" + id, null, null, null, null);
		}
	}

	@Override
	public String getType(Uri uri) {
		return DBObject.MIME;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			long id = db.insertOrThrow(DBObject.TABLE_NAME, null, values);

			if (id == -1) {
				throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
			} else {
				return ContentUris.withAppendedId(uri, id);
			}
		} finally {
			db.close();
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		long id = getId(uri);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			if (id < 0) {
				return db.delete(DBObject.TABLE_NAME, selection, selectionArgs);
			} else {
				return db.delete(DBObject.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
			}
		} finally {
			db.close();
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		long id = getId(uri);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			if (id < 0) {
				return db.update(DBObject.TABLE_NAME, values, selection, selectionArgs);
			} else {
				return db.update(DBObject.TABLE_NAME, values, DBObject.ID + "=" + id, null);
			}
		} finally {
			db.close();
		}
	}

	private long getId(Uri uri) {
		String lastPathSegment = uri.getLastPathSegment();

		if (lastPathSegment != null) {
			try {
				return Long.parseLong(lastPathSegment);
			} catch (NumberFormatException e) {
				Log.e("DbManager", "Number Format Exception : " + e);
			}
		}

		return -1;
	}
}