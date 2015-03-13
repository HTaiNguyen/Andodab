package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBFloat;
import fr.upem.andodab.db.DBObject;

public class DAOFloat implements DAO<DBFloat>{
	
	private ContentResolver contentResolver;
	
	public DAOFloat(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}
	
	@Override
	public void create(DBFloat dbFloat) {
		ContentValues values = new ContentValues();
		values.put(DBFloat.ANCESTOR_ID, dbFloat.getAncestorId());
		Uri  uri = contentResolver.insert(DBObject.CONTENT_URI, values);	
		
		dbFloat.setId(ContentUris.parseId(uri));
		
		values.put(DBFloat.ID, dbFloat.getId());
		values.put(DBFloat.VALUE, dbFloat.getValue());
		
		contentResolver.insert(DBCommon.CONTENT_URI, values);	
	}

	@Override
	public DBFloat read(Object id) {
		Cursor cursor = contentResolver.query(DBObject.CONTENT_URI, new String[]{DBFloat.ANCESTOR_ID}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(DBFloat.ANCESTOR_ID));
		cursor = contentResolver.query(DBFloat.CONTENT_URI, new String[]{DBFloat.VALUE}, "WHERE " + DBFloat.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		Float value = cursor.getFloat(cursor.getColumnIndex(DBFloat.VALUE));
		
		DBFloat dbFloat = new DBFloat(ancestorId, value);
		dbFloat.setId((Long)id);
		return dbFloat;
		
	}

	@Override
	public void udpate(DBFloat dbFloat) {
		ContentValues values = new ContentValues();
		values.put(DBFloat.ID, dbFloat.getId());
		values.put(DBFloat.ANCESTOR_ID, dbFloat.getAncestorId());
		values.put(DBFloat.VALUE, dbFloat.getValue());
		contentResolver.update(DBFloat.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBFloat dbFloat) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ID, dbFloat.getId());
		
		contentResolver.delete(DBFloat.CONTENT_URI, "WHERE " + DBFloat.ID + " = ?", new String[]{dbFloat.getId()+""});
	}

}
