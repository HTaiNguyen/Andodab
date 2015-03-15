package fr.upem.andodab.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBDictionary;
import fr.upem.andodab.db.DBObject;
import fr.upem.andodab.table.TableDictionary;

public class DAODictionary implements DAO<DBDictionary>{

	private ContentResolver contentResolver;
	private DAOObject daoObject;

	public DAODictionary(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
		this.daoObject = new DAOObject(contentResolver);
	}

	@Override
	public void create(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();

		values.put(TableDictionary.COL_OBJECT_ID, dbDictionary.getOwnerId());
		values.put(TableDictionary.COL_VALUE_ID, dbDictionary.getValueId());
		values.put(TableDictionary.COL_KEY, dbDictionary.getKey());

		Uri uri = contentResolver.insert(TableDictionary.CONTENT_URI, values);	

		dbDictionary.setId(ContentUris.parseId(uri));
	}

	@Override
	public DBDictionary read(Object id) {
		Cursor cursor = contentResolver.query(
				TableDictionary.CONTENT_URI, new String[] { TableDictionary.COL_OBJECT_ID, TableDictionary.COL_KEY, TableDictionary.COL_VALUE_ID }, 
				TableDictionary.COL_ID + " = ?", new String[] { id.toString() }, 
				null);
		
		if(!cursor.moveToFirst()) {
			return null;
		}
		
		long ownerId = cursor.getLong(cursor.getColumnIndex(TableDictionary.COL_OBJECT_ID));
		String key = cursor.getString(cursor.getColumnIndex(TableDictionary.COL_KEY));
		long valueId = cursor.getLong(cursor.getColumnIndex(TableDictionary.COL_VALUE_ID));
		
		DBObject obj = daoObject.read(valueId);

		DBDictionary dbDictionary = new DBDictionary(ownerId, key, valueId, obj);
		dbDictionary.setId((Long) id);

		return dbDictionary;
	}
	

	public List<DBDictionary> findByObject(Long ownerId) {
		Cursor cursor = contentResolver.query(
				TableDictionary.CONTENT_URI, new String[]{TableDictionary.COL_ID, TableDictionary.COL_KEY, TableDictionary.COL_VALUE_ID}, 
				TableDictionary.COL_OBJECT_ID + " = ?", new String[] { ownerId.toString() },
				null);
		
		ArrayList<DBDictionary> dbDictionaries = new ArrayList<DBDictionary>();

		if (cursor.moveToFirst()) {
			do {
				long id = cursor.getLong(cursor.getColumnIndex(TableDictionary.COL_ID));
				String key = cursor.getString(cursor.getColumnIndex(TableDictionary.COL_KEY));
				long valueId = cursor.getLong(cursor.getColumnIndex(TableDictionary.COL_VALUE_ID));

				DBObject obj = daoObject.read(valueId);

				DBDictionary dbDictionary = new DBDictionary(ownerId, key, valueId, obj);
				if(dbDictionaries != null) {
					dbDictionary.setId(id);
					dbDictionaries.add(dbDictionary);
				}
			} while (cursor.moveToNext());
		}

		return dbDictionaries;
	}

	@Override
	public void udpate(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();

		values.put(TableDictionary.COL_ID, dbDictionary.getId());
		values.put(TableDictionary.COL_OBJECT_ID, dbDictionary.getOwnerId());
		values.put(TableDictionary.COL_KEY, dbDictionary.getKey());
		values.put(TableDictionary.COL_VALUE_ID, dbDictionary.getValueId());

		contentResolver.update(TableDictionary.CONTENT_URI, values, null, null);
	}

	@Override
	public void delete(DBDictionary dbDictionary) {
		ContentValues values = new ContentValues();

		values.put(TableDictionary.COL_ID, dbDictionary.getId());

		contentResolver.delete(TableDictionary.CONTENT_URI, TableDictionary.COL_ID + " = ?", new String[] { dbDictionary.getId() + "" });
	}

}