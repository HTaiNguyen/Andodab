package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBInteger;
import fr.upem.andodab.table.TableFloat;
import fr.upem.andodab.table.TableInteger;
import fr.upem.andodab.table.TableObject;

public class DAOInteger implements DAO<DBInteger>{
	
	private ContentResolver contentResolver;
	
	public DAOInteger(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		Uri  uri = contentResolver.insert(TableObject.CONTENT_URI, values);	
		values.put(TableInteger.COL_TYPE, dbInteger.getType());
		dbInteger.setId(ContentUris.parseId(uri));
		values.clear();
		values.put(TableInteger.COL_ID, dbInteger.getId());
		values.put(TableInteger.COL_VALUE, dbInteger.getValue());
		values.put(TableInteger.COL_ANCESTOR_ID, dbInteger.getAncestorId());
		contentResolver.insert(TableInteger.CONTENT_URI, values);	
	}

	@Override
	public DBInteger read(Object id) {

		Cursor cursor = contentResolver.query(
				TableInteger.CONTENT_URI, new String[]{TableInteger.COL_VALUE, TableInteger.COL_ANCESTOR_ID},
				TableInteger.COL_ID + " = ?", new String[]{id.toString()}, 
				null);
		
		cursor.moveToFirst();
		
		long value = cursor.getLong(cursor.getColumnIndex(TableInteger.COL_VALUE));
		long ancestorId = cursor.getLong(cursor.getColumnIndex(TableInteger.COL_ANCESTOR_ID));
		
		DBInteger dbInteger = new DBInteger(ancestorId, value);
		dbInteger.setId((Long)id);
		return dbInteger;
	}

	@Override
	public void udpate(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		values.put(TableInteger.COL_ID, dbInteger.getId());
		values.put(TableInteger.COL_ANCESTOR_ID, dbInteger.getAncestorId());
		values.put(TableInteger.COL_VALUE, dbInteger.getValue());

		contentResolver.update(TableInteger.CONTENT_URI, values, TableInteger.COL_ID + " = ?", new String[] { dbInteger.getId().toString() });
	}

	@Override
	public void delete(DBInteger dbInteger) {
		ContentValues values = new ContentValues();
		values.put(TableInteger.COL_ID, dbInteger.getId());
		
		contentResolver.delete(TableInteger.CONTENT_URI, TableInteger.COL_ID + " = ?", new String[]{dbInteger.getId()+""});
	}

}
