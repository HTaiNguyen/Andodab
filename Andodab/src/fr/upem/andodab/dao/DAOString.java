package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBString;
import fr.upem.andodab.db.DBObject;

public class DAOString implements DAO<DBString> {
	
	private ContentResolver contentResolver;
	
	public DAOString(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(DBString.ANCESTOR_ID, dbString.getAncestorId());
		Uri  uri = contentResolver.insert(DBObject.CONTENT_URI, values);	
		
		dbString.setId(ContentUris.parseId(uri));
		
		values.put(DBString.ID, dbString.getId());
		values.put(DBString.VALUE, dbString.getValue());
		
		contentResolver.insert(DBCommon.CONTENT_URI, values);	
	}

	@Override
	public DBString read(Object id) {
		Cursor cursor = contentResolver.query(DBObject.CONTENT_URI, new String[]{DBString.ANCESTOR_ID}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(DBString.ANCESTOR_ID));
		cursor = contentResolver.query(DBString.CONTENT_URI, new String[]{DBString.VALUE}, "WHERE " + DBString.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		String value = cursor.getString(cursor.getColumnIndex(DBString.VALUE));
		
		DBString dbString = new DBString(ancestorId, value);
		dbString.setId((Long)id);
		return dbString;
	}

	@Override
	public void udpate(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(DBString.ID, dbString.getId());
		values.put(DBString.ANCESTOR_ID, dbString.getAncestorId());
		values.put(DBString.VALUE, dbString.getValue());
		contentResolver.update(DBString.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ID, dbString.getId());
		
		contentResolver.delete(DBString.CONTENT_URI, "WHERE " + DBString.ID + " = ?", new String[]{dbString.getId()+""});
		
	}

}
