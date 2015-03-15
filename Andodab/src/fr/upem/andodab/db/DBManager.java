package fr.upem.andodab.db;

import fr.upem.andodab.table.TableCommon;
import fr.upem.andodab.table.TableDictionary;
import fr.upem.andodab.table.TableFloat;
import fr.upem.andodab.table.TableInteger;
import fr.upem.andodab.table.TableObject;
import fr.upem.andodab.table.TableString;
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
	public static final String Table_NAME = "andodab.db";
	public static final int Table_VERSION = 1;

	private Context context;
	private DbHelper dbHelper;


	private static final int CODE_TABLE_OBJECT = 1;
	private static final int CODE_TABLE_OBJECT_COL_ID = 2;
	private static final int CODE_TABLE_DICTIONARY = 3;
	private static final int CODE_TABLE_DICTIONARY_COL_ID = 4;
	private static final int CODE_TABLE_COMMON = 5;
	private static final int CODE_TABLE_COMMON_COL_ID = 6;
	private static final int CODE_TABLE_INTEGER = 7;
	private static final int CODE_TABLE_INTEGER_COL_ID = 8;
	private static final int CODE_TABLE_STRING = 9;
	private static final int CODE_TABLE_STRING_COL_ID = 10;
	private static final int CODE_TABLE_FLOAT = 11;
	private static final int CODE_TABLE_FLOAT_COL_ID = 12;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, TableObject.TABLE_NAME, CODE_TABLE_OBJECT);
		uriMatcher.addURI(AUTHORITY, TableObject.TABLE_NAME + "/#", CODE_TABLE_OBJECT_COL_ID);
		uriMatcher.addURI(AUTHORITY, TableDictionary.TABLE_NAME, CODE_TABLE_DICTIONARY);
		uriMatcher.addURI(AUTHORITY, TableDictionary.TABLE_NAME + "/#", CODE_TABLE_DICTIONARY_COL_ID);
		uriMatcher.addURI(AUTHORITY, TableCommon.TABLE_NAME, CODE_TABLE_COMMON);
		uriMatcher.addURI(AUTHORITY, TableCommon.TABLE_NAME + "/#", CODE_TABLE_COMMON_COL_ID);
		uriMatcher.addURI(AUTHORITY, TableInteger.TABLE_NAME, CODE_TABLE_INTEGER);
		uriMatcher.addURI(AUTHORITY, TableInteger.TABLE_NAME, CODE_TABLE_INTEGER_COL_ID);
		uriMatcher.addURI(AUTHORITY, TableFloat.TABLE_NAME, CODE_TABLE_FLOAT);
		uriMatcher.addURI(AUTHORITY, TableFloat.TABLE_NAME + "/#", CODE_TABLE_FLOAT_COL_ID);
		uriMatcher.addURI(AUTHORITY, TableString.TABLE_NAME, CODE_TABLE_STRING);
		uriMatcher.addURI(AUTHORITY, TableString.TABLE_NAME + "/#", CODE_TABLE_STRING_COL_ID);
	}


	private final class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, Table_NAME, null, Table_VERSION);
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
		public void onCreate(SQLiteDatabase database) {
			TableObject.onCreate(database);
			TableCommon.onCreate(database);
			TableFloat.onCreate(database);
			TableInteger.onCreate(database);
			TableString.onCreate(database);
			TableDictionary.onCreate(database);
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			TableObject.onUpgrade(database, oldVersion, newVersion);
			TableCommon.onUpgrade(database, oldVersion, newVersion);
			TableFloat.onUpgrade(database, oldVersion, newVersion);
			TableInteger.onUpgrade(database, oldVersion, newVersion);
			TableString.onUpgrade(database, oldVersion, newVersion);
			TableDictionary.onUpgrade(database, oldVersion, newVersion);
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
					return db.query(TableObject.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableObject.TABLE_NAME, projection, TableObject.COL_ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_DICTIONARY:
				if (id < 0) {
					return db.query(TableDictionary.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableDictionary.TABLE_NAME, projection, TableDictionary.COL_ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_COMMON:
				if (id < 0) {
					return db.query(TableCommon.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableCommon.TABLE_NAME, projection, TableCommon.COL_ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_FLOAT:
				if (id < 0) {
					return db.query(TableFloat.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableFloat.TABLE_NAME, projection, TableFloat.COL_ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_INTEGER:
				if (id < 0) {
					return db.query(TableInteger.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableInteger.TABLE_NAME, projection, TableInteger.COL_ID + "=" + id, null, null, null, null);
				}
			case CODE_TABLE_STRING:
				if (id < 0) {
					return db.query(TableString.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				} else {
					return db.query(TableString.TABLE_NAME, projection, TableString.COL_ID + "=" + id, null, null, null, null);
				}
			default:
				throw new IllegalArgumentException("Unsupported URI : " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case CODE_TABLE_OBJECT:
				return TableObject.MIME;
			case CODE_TABLE_DICTIONARY:
				return TableDictionary.MIME;
			case CODE_TABLE_COMMON:
				return TableCommon.MIME;
			case CODE_TABLE_FLOAT:
				return TableFloat.MIME;
			case CODE_TABLE_STRING:
				return TableString.MIME;
			case CODE_TABLE_INTEGER:
				return TableInteger.MIME;
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
					id  = db.insertOrThrow(TableObject.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_DICTIONARY:
					id = db.insertOrThrow(TableDictionary.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_COMMON:
					id = db.insertOrThrow(TableCommon.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_FLOAT:
					id = db.insertOrThrow(TableFloat.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_INTEGER:
					id = db.insertOrThrow(TableInteger.TABLE_NAME, null, values);
					if (id == -1) {
						throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","DbManager", values, uri));
					} else {
						return ContentUris.withAppendedId(uri, id);
					}
				case CODE_TABLE_STRING:
					id = db.insertOrThrow(TableString.TABLE_NAME, null, values);
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
						return db.delete(TableObject.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableObject.TABLE_NAME, TableObject.COL_ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_DICTIONARY:
					if (id < 0) {
						return db.delete(TableDictionary.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableDictionary.TABLE_NAME, TableDictionary.COL_ID + "=" + id, selectionArgs);
					}				
				case CODE_TABLE_COMMON:
					if (id < 0) {
						return db.delete(TableCommon.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableCommon.TABLE_NAME, TableObject.COL_ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_FLOAT:
					if (id < 0) {
						return db.delete(TableFloat.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableFloat.TABLE_NAME, TableObject.COL_ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_INTEGER:
					if (id < 0) {
						return db.delete(TableInteger.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableInteger.TABLE_NAME, TableObject.COL_ID + "=" + id, selectionArgs);
					}
				case CODE_TABLE_STRING:
					if (id < 0) {
						return db.delete(TableString.TABLE_NAME, selection, selectionArgs);
					} else {
						return db.delete(TableString.TABLE_NAME, TableObject.COL_ID + "=" + id, selectionArgs);
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
						return db.update(TableObject.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableObject.TABLE_NAME, values, TableObject.COL_ID + "=" + id, null);
					}
				case CODE_TABLE_DICTIONARY: 
					if (id < 0) {
						return db.update(TableDictionary.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableDictionary.TABLE_NAME, values, TableDictionary.COL_ID + "=" + id, null);
					}
				case CODE_TABLE_COMMON: 
					if (id < 0) {
						return db.update(TableCommon.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableCommon.TABLE_NAME, values, TableObject.COL_ID + "=" + id, null);
					}
				case CODE_TABLE_FLOAT: 
					if (id < 0) {
						return db.update(TableFloat.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableFloat.TABLE_NAME, values, TableObject.COL_ID + "=" + id, null);
					}
				case CODE_TABLE_INTEGER: 
					if (id < 0) {
						return db.update(TableInteger.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableInteger.TABLE_NAME, values, TableObject.COL_ID + "=" + id, null);
					}
				case CODE_TABLE_STRING: 
					if (id < 0) {
						return db.update(TableString.TABLE_NAME, values, selection, selectionArgs);
					} else {
						return db.update(TableString.TABLE_NAME, values, TableObject.COL_ID + "=" + id, null);
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