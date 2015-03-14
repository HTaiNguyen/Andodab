package fr.upem.andodab.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBDictionary;

public class DAODictionary implements DAO<DBDictionary>{

	private ContentResolver contentResolver;

	public DAODictionary(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();
		values.put(DBDictionary.OBJECT_ID, dbDictionary.getOwnerId());
		values.put(DBDictionary.VALUE_ID, dbDictionary.getValueId());
		values.put(DBDictionary.KEY, dbDictionary.getKey());

		Uri uri = contentResolver.insert(DBDictionary.CONTENT_URI, values);	
		dbDictionary.setId(ContentUris.parseId(uri));
	}

	@Override
	public DBDictionary read(Object id) {
		Cursor cursor = contentResolver.query(DBDictionary.CONTENT_URI, new String[]{DBDictionary.OBJECT_ID, DBDictionary.KEY, DBDictionary.VALUE_ID}, DBDictionary.ID + " = ?", new String[]{id.toString()}, null);
		cursor.moveToFirst();
		long ownerId = cursor.getLong(cursor.getColumnIndex(DBDictionary.OBJECT_ID));
		String key = cursor.getString(cursor.getColumnIndex(DBDictionary.KEY));
		long valueId = cursor.getLong(cursor.getColumnIndex(DBDictionary.VALUE_ID));

		DBDictionary dbDictionary = new DBDictionary(ownerId, key, valueId);
		dbDictionary.setId((Long)id);
		return dbDictionary;


	}

	@Override
	public void udpate(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();
		values.put(DBDictionary.ID, dbDictionary.getId());
		values.put(DBDictionary.OBJECT_ID, dbDictionary.getOwnerId());
		values.put(DBDictionary.KEY, dbDictionary.getKey());
		values.put(DBDictionary.VALUE_ID, dbDictionary.getValueId());
		contentResolver.update(DBDictionary.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();
		values.put(DBDictionary.ID, dbDictionary.getId());

		contentResolver.delete(DBDictionary.CONTENT_URI, DBDictionary.ID + " = ?", new String[]{dbDictionary.getId()+""});
	}

	public List<DBDictionary> findByObject(Long ownerId) {
		Cursor cursor = contentResolver.query(DBDictionary.CONTENT_URI, new String[]{DBDictionary.ID, DBDictionary.KEY, DBDictionary.VALUE_ID}, DBDictionary.OBJECT_ID + " = ?", new String[]{ownerId.toString()}, null);
		ArrayList<DBDictionary> dbDictionaries = new ArrayList<DBDictionary>();
		if(cursor.moveToFirst()){
			do {
				long id = cursor.getLong(cursor.getColumnIndex(DBDictionary.ID));
				String key = cursor.getString(cursor.getColumnIndex(DBDictionary.KEY));
				long valueId = cursor.getLong(cursor.getColumnIndex(DBDictionary.VALUE_ID));

				DBDictionary dbDictionary = new DBDictionary(ownerId, key, valueId);
				dbDictionary.setId(id);
				dbDictionaries.add(dbDictionary);
			} while(cursor.moveToNext());
		}
		return dbDictionaries;
	}
}
