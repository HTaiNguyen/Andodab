package fr.upem.test;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DbManager extends ContentProvider {
	public static final String AUTHORITY = "fr.upem.test.DbManager";
	public static final String DB_NAME = "andodab.db";
	public static final int DB_VERSION = 1;

	public static final String SQL_CREATE_TABLE_OBJECT = 
			"CREATE TABLE IF NOT EXISTS " + ADObject.DBObject.TABLE_NAME + " (" + 
					ADObject.DBObject.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					ADObject.DBObject.NAME + " VARCHAR(255)" + 
					");";
	public static final String SQL_DROP_TABLE_OBJECT = 
			"DROP TABLE IF EXISTS " + ADObject.DBObject.TABLE_NAME;

	private Context context;
	private DbHelper dbHelper;

	private final class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_TABLE_OBJECT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DROP_TABLE_OBJECT);

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
			return db.query(ADObject.DBObject.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
		} else {
			return db.query(ADObject.DBObject.TABLE_NAME, projection, ADObject.DBObject.ID + "=" + id, null, null, null, null);
		}
	}

	@Override
	public String getType(Uri uri) {
		return "vnd.android.cursor.item/vnd.fr.upem.test.DbManager.object";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			long id = db.insertOrThrow(ADObject.DBObject.TABLE_NAME, null, values);

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
				return db.delete(ADObject.DBObject.TABLE_NAME, selection, selectionArgs);
			} else {
				return db.delete(ADObject.DBObject.TABLE_NAME, ADObject.DBObject.ID + "=" + id, selectionArgs);
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
				return db.update(ADObject.DBObject.TABLE_NAME, values, selection, selectionArgs);
			} else {
				return db.update(ADObject.DBObject.TABLE_NAME, values, ADObject.DBObject.ID + "=" + id, null);
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