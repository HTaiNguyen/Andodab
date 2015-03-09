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
	public static final Uri CONTENT_URI = Uri.parse("content://fr.upem.test.DbManager");
	public static final String DB_NAME = "andodab.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "object";
	public static final String MIME = "vnd.android.cursor.item/vnd.fr.upem.test.DbManager.object";

	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
					Object.OBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					Object.OBJECT_NAME + " VARCHAR(255)" + 
					");";
	public static final String SQL_DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;

	private Context context;
	private DbHelper dbHelper;

	private final class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DROP_TABLE);
			onCreate(db);
		}
	}

	/*public DbManager(Context context) {
		this.context = context;
	}*/

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,	String[] selectionArgs, String sortOrder) {
		long id = getId(uri);

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (id < 0) {
			return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
		} else {
			return db.query(TABLE_NAME, projection, Object.OBJECT_ID + "=" + id, null, null, null, null);
		}
	}

	@Override
	public String getType(Uri uri) {
		return MIME;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			long id = db.insertOrThrow(TABLE_NAME, null, values);

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
				return db.delete(TABLE_NAME, selection, selectionArgs);
			} else {
				return db.delete(TABLE_NAME, Object.OBJECT_ID + "=" + id, selectionArgs);
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
				return db.update(TABLE_NAME, values, selection, selectionArgs);
			} else {
				return db.update(TABLE_NAME, values, Object.OBJECT_ID + "=" + id, null);
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