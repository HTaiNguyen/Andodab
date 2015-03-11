package fr.upem.old.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class AndodabContentProvider extends ContentProvider {
	private SQLiteDatabase andodabDB = null;
	private static UriMatcher uriMatcher;
	
	private static final String AUTHORITY = "fr.upem.andodab"; 
	private static final String PATH = "andodabObject";
	
	private AndodabContentProvider(UriMatcher uriMatcher) {
		this.uriMatcher = uriMatcher;
	}
	
	public static AndodabContentProvider AndodabContentProviderFactory() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH, 0);
		uriMatcher.addURI(AUTHORITY, PATH + "/float/#", 1);
		uriMatcher.addURI(AUTHORITY, PATH + "/integer/#", 2);
		uriMatcher.addURI(AUTHORITY, PATH + "/string/#", 3);
		
		return new AndodabContentProvider(uriMatcher);
	}
	
	@Override
	public boolean onCreate() {
		Context context = getContext();
		AndodabSQLiteHelper dbHelper = new AndodabSQLiteHelper(context);
		andodabDB = dbHelper.getWritableDatabase();
		if(andodabDB == null) {
			return false;
		}
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlQueryBuilder = new SQLiteQueryBuilder();
		
		// TODO récupérer le path de l'URI
		sqlQueryBuilder.setTables("");
		sqlQueryBuilder.appendWhere("");
		
		return sqlQueryBuilder.query(andodabDB, projection, selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
