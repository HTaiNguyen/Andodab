package fr.upem.andodab.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBObject;

public class DAOCommon implements DAO<DBCommon>{

	private ContentResolver contentResolver;
	
	public DAOCommon(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	
	@Override
	public void create(DBCommon dbCommon) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ANCESTOR_ID, dbCommon.getAncestorId());
		Uri  uri = contentResolver.insert(DBObject.CONTENT_URI, values);	

		dbCommon.setId(ContentUris.parseId(uri));
		values.clear();
		values.put(DBCommon.ID, dbCommon.getId());
		values.put(DBCommon.NAME, dbCommon.getName());
		values.put(DBCommon.SEALED, dbCommon.isSealed());
		
		contentResolver.insert(DBCommon.CONTENT_URI, values);	
	}

	@Override
	public DBCommon read(Object id) {
		Cursor cursor = contentResolver.query(DBObject.CONTENT_URI, new String[]{DBCommon.ANCESTOR_ID}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(DBObject.ANCESTOR_ID));
		cursor = contentResolver.query(DBCommon.CONTENT_URI, new String[]{DBCommon.NAME, DBCommon.SEALED}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex(DBCommon.NAME));
		boolean sealed = cursor.getInt(cursor.getColumnIndex(DBCommon.SEALED)) > 0;
		DBCommon dbCommon = new DBCommon(ancestorId, name, sealed);
		dbCommon.setId((Long) id);
		return dbCommon;
		
	}
	
	public List<DBCommon> findAll() {
		ArrayList<DBCommon> dbCommons = new ArrayList<DBCommon>();
		Cursor cursor = contentResolver.query(DBCommon.CONTENT_URI, new String[]{DBCommon.NAME, DBCommon.SEALED, DBCommon.ID}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Long id = cursor.getLong(cursor.getColumnIndex(DBCommon.ID));
				String name = cursor.getString(cursor.getColumnIndex(DBCommon.NAME));
				boolean sealed = cursor.getInt(cursor.getColumnIndex(DBCommon.SEALED)) > 0;
				Cursor cursor2 = contentResolver.query(DBObject.CONTENT_URI, new String[]{DBCommon.ANCESTOR_ID}, "WHERE " + DBCommon.ID + " = ?", new String[]{id.toString()}, null);
				cursor2.moveToFirst();
				long ancestorId = cursor2.getLong(cursor.getColumnIndex(DBObject.ANCESTOR_ID));
				
				DBCommon dbCommon = new DBCommon(ancestorId, name, sealed);
				dbCommon.setId(id);
				
				dbCommons.add(dbCommon);
			} while(cursor.moveToNext());
		}
		
		return dbCommons;
		
	}

	@Override
	public void udpate(DBCommon dbCommon) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ID, dbCommon.getId());
		values.put(DBCommon.ANCESTOR_ID, dbCommon.getAncestorId());
		values.put(DBCommon.NAME, dbCommon.getName());
		values.put(DBCommon.SEALED, dbCommon.isSealed());
		contentResolver.update(DBCommon.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBCommon dbCommon) {
		ContentValues values = new ContentValues();
		values.put(DBCommon.ID, dbCommon.getId());
		
		contentResolver.delete(DBCommon.CONTENT_URI, "WHERE " + DBCommon.ID + " = ?", new String[]{dbCommon.getId()+""});
	}

	
}
