package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBFloat;
import fr.upem.andodab.table.TableFloat;
import fr.upem.andodab.table.TableObject;

public class DAOFloat implements DAO<DBFloat>{
	
	private ContentResolver contentResolver;
	
	public DAOFloat(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}
	
	@Override
	public void create(DBFloat dbFloat) {
		ContentValues values = new ContentValues();
		values.put(TableFloat.COL_TYPE, dbFloat.getType());
		Uri  uri = contentResolver.insert(TableObject.CONTENT_URI, values);	
		dbFloat.setId(ContentUris.parseId(uri));
		values.clear();
		values.put(TableFloat.COL_ID, dbFloat.getId());
		values.put(TableFloat.COL_VALUE, dbFloat.getValue());
		values.put(TableFloat.COL_ANCESTOR_ID, dbFloat.getAncestorId());
		
		contentResolver.insert(TableFloat.CONTENT_URI, values);	
	}

	@Override
	public DBFloat read(Object id) {
		Cursor cursor = contentResolver.query(
				TableFloat.CONTENT_URI, new String[]{TableFloat.COL_VALUE, TableFloat.COL_ANCESTOR_ID}, 
				TableFloat.COL_ID + " = ?", new String[]{id.toString()}, 
				null);
		
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(TableFloat.COL_ANCESTOR_ID));
		Float value = cursor.getFloat(cursor.getColumnIndex(TableFloat.COL_VALUE));
		
		DBFloat dbFloat = new DBFloat(ancestorId, value);
		dbFloat.setId((Long)id);
		return dbFloat;
		
	}

	@Override
	public void udpate(DBFloat dbFloat) {
		ContentValues values = new ContentValues();
		values.put(TableFloat.COL_ID, dbFloat.getId());
		values.put(TableFloat.COL_ANCESTOR_ID, dbFloat.getAncestorId());
		values.put(TableFloat.COL_VALUE, dbFloat.getValue());

		contentResolver.update(TableFloat.CONTENT_URI, values, TableFloat.COL_ID + " = ?", new String[] { dbFloat.getId().toString() });
	}

	@Override
	public void delete(DBFloat dbFloat) {
		contentResolver.delete(TableFloat.CONTENT_URI, TableFloat.COL_ID + " = ?", new String[]{dbFloat.getId()+""});
	}

}
