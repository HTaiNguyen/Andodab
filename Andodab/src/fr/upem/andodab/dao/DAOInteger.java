package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBInteger;
import fr.upem.andodab.db.DBObject;

public class DAOInteger implements DAO<DBInteger>{
	
	private ContentResolver contentResolver;
	
	public DAOInteger(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		values.put(DBInteger.ANCESTOR_ID, dbInteger.getAncestorId());
		Uri  uri = contentResolver.insert(DBObject.CONTENT_URI, values);	
		
		dbInteger.setId(ContentUris.parseId(uri));
		
		values.put(DBInteger.ID, dbInteger.getId());
		values.put(DBInteger.VALUE, dbInteger.getValue());
		
		contentResolver.insert(DBCommon.CONTENT_URI, values);	
	}

	@Override
	public DBInteger read(Object id) {
		Cursor cursor = contentResolver.query(DBObject.CONTENT_URI, new String[]{DBInteger.ANCESTOR_ID}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(DBInteger.ANCESTOR_ID));
		cursor = contentResolver.query(DBInteger.CONTENT_URI, new String[]{DBInteger.VALUE}, "WHERE " + DBInteger.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long value = cursor.getLong(cursor.getColumnIndex(DBInteger.VALUE));
		
		DBInteger dbInteger = new DBInteger(ancestorId, value);
		dbInteger.setId((Long)id);
		return dbInteger;
	}

	@Override
	public void udpate(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		values.put(DBInteger.ID, dbInteger.getId());
		values.put(DBInteger.ANCESTOR_ID, dbInteger.getAncestorId());
		values.put(DBInteger.VALUE, dbInteger.getValue());
		contentResolver.update(DBInteger.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ID, dbInteger.getId());
		
		contentResolver.delete(DBInteger.CONTENT_URI, "WHERE " + DBInteger.ID + " = ?", new String[]{dbInteger.getId()+""});
	}

}
