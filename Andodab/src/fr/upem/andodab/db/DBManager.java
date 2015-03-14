package fr.upem.andodab.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
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
					"FOREIGN KEY (" + DBObject.ANCESTOR_ID + ") REFERENCES " + DBObject.TABLE_NAME + "(" + DBObject.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_OBJECT = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;
	
	public static final String SQL_CREATE_TABLE_COMMON = 
			"CREATE TABLE IF NOT EXISTS " + DBCommon.TABLE_NAME + " (" + 
					DBCommon.ID + " INTEGER, " +
					DBCommon.NAME + " VARCHAR(255) NOT NULL UNIQUE COLLATE NOCASE, " + 
					DBCommon.SEALED + " BOOLEAN," + 
					"PRIMARY KEY (" + DBCommon.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_COMMON = 
			"DROP TABLE IF EXISTS " + DBCommon.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_DICTIONARY = 
			"CREATE TABLE IF NOT EXISTS " + DBDictionary.TABLE_NAME + " (" + 
					DBDictionary.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					DBDictionary.OBJECT_ID + " INTEGER, " +
					DBDictionary.KEY + " VARCHAR(255), " + 
					DBDictionary.VALUE_ID + " INTEGER, " + 
					"FOREIGN KEY (" + DBDictionary.OBJECT_ID + ") REFERENCES " + DBObject.TABLE_NAME + "(" + DBObject.ID + "), " +
					"FOREIGN KEY (" + DBDictionary.VALUE_ID + ") REFERENCES " + DBObject.TABLE_NAME + "(" + DBObject.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_DICTIONARY = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_FLOAT = 
			"CREATE TABLE IF NOT EXISTS " + DBFloat.TABLE_NAME + " (" + 
					DBFloat.ID + " INTEGER, " + 
					DBFloat.VALUE + " REAL, " + 
					"PRIMARY KEY (" + DBFloat.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_FLOAT = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_INTEGER = 
			"CREATE TABLE IF NOT EXISTS " + DBInteger.TABLE_NAME + " (" + 
					DBInteger.ID + " INTEGER, " + 
					DBInteger.VALUE + " INTEGER, " + 
					"PRIMARY KEY (" + DBInteger.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_INTEGER = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	public static final String SQL_CREATE_TABLE_STRING = 
			"CREATE TABLE IF NOT EXISTS " + DBString.TABLE_NAME + " (" + 
					DBString.ID + " INTEGER, " + 
					DBString.VALUE + " TEXT, " + 
					"PRIMARY KEY (" + DBString.ID + ")" +
					");";
	public static final String SQL_DROP_TABLE_STRING = 
			"DROP TABLE IF EXISTS " + DBObject.TABLE_NAME;

	private Context context;
	private DbHelper dbHelper;


	private static final int CODE_TABLE_OBJECT = 1;
	private static final int CODE_TABLE_OBJECT_ID = 2;
	private static final int CODE_TABLE_DICTIONARY = 3;
	private static final int CODE_TABLE_DICTIONARY_ID = 4;
	private static final int CODE_TABLE_COMMON = 5;
	private static final int CODE_TABLE_COMMON_ID = 6;
	private static final int CODE_TABLE_INTEGER = 7;
	private static final int CODE_TABLE_INTEGER_ID = 8;
	private static final int CODE_TABLE_STRING = 9;
	private static final int CODE_TABLE_STRING_ID = 10;
	private static final int CODE_TABLE_FLOAT = 11;
	private static final int CODE_TABLE_FLOAT_ID = 12;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, DBObject.TABLE_NAME, CODE_TABLE_OBJECT);
		uriMatcher.addURI(AUTHORITY, DBObject.TABLE_NAME + "/#", CODE_TABLE_OBJECT_ID);
		uriMatcher.addURI(AUTHORITY, DBDictionary.TABLE_NAME, CODE_TABLE_DICTIONARY);
		uriMatcher.addURI(AUTHORITY, DBDictionary.TABLE_NAME + "/#", CODE_TABLE_DICTIONARY_ID);
		uriMatcher.addURI(AUTHORITY, DBCommon.TABLE_NAME, CODE_TABLE_COMMON);
		uriMatcher.addURI(AUTHORITY, DBCommon.TABLE_NAME + "/#", CODE_TABLE_COMMON_ID);
		uriMatcher.addURI(AUTHORITY, DBInteger.TABLE_NAME, CODE_TABLE_INTEGER);
		uriMatcher.addURI(AUTHORITY, DBInteger.TABLE_NAME, CODE_TABLE_INTEGER_ID);
		uriMatcher.addURI(AUTHORITY, DBFloat.TABLE_NAME, CODE_TABLE_FLOAT);
		uriMatcher.addURI(AUTHORITY, DBFloat.TABLE_NAME + "/#", CODE_TABLE_FLOAT_ID);
		uriMatcher.addURI(AUTHORITY, DBString.TABLE_NAME, CODE_TABLE_STRING);
		uriMatcher.addURI(AUTHORITY, DBString.TABLE_NAME + "/#", CODE_TABLE_STRING_ID);
	}


	private final class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}
		
		@Override
		public void onOpen(SQLiteDatabase db) {
		    super.onOpen(db);
		    if (!db.isReadOnly()) {
		        // Enable foreign key constraints
		        db.execSQL("PRAGMA foreign_keys=ON;");
		    }
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_TABLE_OBJECT);
			db.execSQL(SQL_CREATE_TABLE_DICTIONARY);
			db.execSQL(SQL_CREATE_TABLE_COMMON);
			db.execSQL(SQL_CREATE_TABLE_FLOAT);
			db.execSQL(SQL_CREATE_TABLE_INTEGER);
			db.execSQL(SQL_CREATE_TABLE_STRING);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DROP_TABLE_OBJECT);
			db.execSQL(SQL_DROP_TABLE_DICTIONARY);
			db.execSQL(SQL_DROP_TABLE_COMMON);
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
		switch (uriMatcher.match(uri)) {
			case CODE_TABLE_OBJECT:
				if (id < 0) {
					return db.query(DBObject.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBObject.TABLE_NAME, projection, DBObject.ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_DICTIONARY:
				if (id < 0) {
					return db.query(DBDictionary.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBDictionary.TABLE_NAME, projection, DBDictionary.ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_COMMON:
				if (id < 0) {
					return db.query(DBCommon.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBCommon.TABLE_NAME, projection, DBCommon.ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_FLOAT:
				if (id < 0) {
					return db.query(DBFloat.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBFloat.TABLE_NAME, projection, DBFloat.ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_INTEGER:
				if (id < 0) {
					return db.query(DBInteger.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBInteger.TABLE_NAME, projection, DBInteger.ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_STRING:
				if (id < 0) {
					return db.query(DBString.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(DBString.TABLE_NAME, projection, DBString.ID + "=" + id, null, null, null, null);
				}
			default:
				throw new IllegalArgumentException("Unsupported URI : " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case CODE_TABLE_OBJECT:
				return DBObject.MIME;
			case CODE_TABLE_DICTIONARY:
				return DBDictionary.MIME;
			case CODE_TABLE_COMMON:
				return DBCommon.MIME;
			case CODE_TABLE_FLOAT:
				return DBFloat.MIME;
			case CODE_TABLE_STRING:
				return DBString.MIME;
			case CODE_TABLE_INTEGER:
				return DBInteger.MIME;
			default:
				throw new IllegalArgumentException("Unsupported URI : " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			long id = 0;
			switch (uriMatcher.match(uri)) {
				case CODE_TABLE_OBJECT:
					id  = db.insertOrThrow(DBObject.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_DICTIONARY:
					id = db.insertOrThrow(DBDictionary.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_COMMON:
					id = db.insertOrThrow(DBCommon.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_FLOAT:
					id = db.insertOrThrow(DBFloat.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_INTEGER:
					id = db.insertOrThrow(DBInteger.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_STRING:
					id = db.insertOrThrow(DBString.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				default:
					throw new SQLException("Failed to insert row into " + uri);
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
			switch (uriMatcher.match(uri)) {
				case CODE_TABLE_OBJECT:
					if (id < 0) {
						return db.delete(DBObject.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBObject.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_DICTIONARY:
					if (id < 0) {
						return db.delete(DBDictionary.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBDictionary.TABLE_NAME, DBDictionary.ID + "=" + id, selectionArgs);
					}				
				case CODE_TABLE_COMMON:
					if (id < 0) {
						return db.delete(DBCommon.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBCommon.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_FLOAT:
					if (id < 0) {
						return db.delete(DBFloat.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBFloat.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_INTEGER:
					if (id < 0) {
						return db.delete(DBInteger.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBInteger.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_STRING:
					if (id < 0) {
						return db.delete(DBString.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(DBString.TABLE_NAME, DBObject.ID + "=" + id, selectionArgs);
					}
				default:
					throw new IllegalArgumentException("Unsupported URI: " + uri);
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
			switch (uriMatcher.match(uri)) {
				case CODE_TABLE_OBJECT: 
					if (id < 0) {
						return db.update(DBObject.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBObject.TABLE_NAME, values, DBObject.ID + "=" + id, null);
					}
				case CODE_TABLE_DICTIONARY: 
					if (id < 0) {
						return db.update(DBDictionary.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBDictionary.TABLE_NAME, values, DBDictionary.ID + "=" + id, null);
					}
				case CODE_TABLE_COMMON: 
					if (id < 0) {
						return db.update(DBCommon.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBCommon.TABLE_NAME, values, DBObject.ID + "=" + id, null);
					}
				case CODE_TABLE_FLOAT: 
					if (id < 0) {
						return db.update(DBFloat.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBFloat.TABLE_NAME, values, DBObject.ID + "=" + id, null);
					}
				case CODE_TABLE_INTEGER: 
					if (id < 0) {
						return db.update(DBInteger.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBInteger.TABLE_NAME, values, DBObject.ID + "=" + id, null);
					}
				case CODE_TABLE_STRING: 
					if (id < 0) {
						return db.update(DBString.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(DBString.TABLE_NAME, values, DBObject.ID + "=" + id, null);
					}
				default:
					throw new IllegalArgumentException("Unsupported URI: " + uri);
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