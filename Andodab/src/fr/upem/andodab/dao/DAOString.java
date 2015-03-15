package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBString;
import fr.upem.andodab.table.TableInteger;
import fr.upem.andodab.table.TableObject;
import fr.upem.andodab.table.TableString;

public class DAOString implements DAO<DBString> {
	
	private ContentResolver contentResolver;
	
	public DAOString(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(TableString.COL_TYPE, dbString.getType());
		Uri  uri = contentResolver.insert(TableObject.CONTENT_URI, values);	
		dbString.setId(ContentUris.parseId(uri));
		values.clear();
		values.put(TableString.COL_ID, dbString.getId());
		values.put(TableString.COL_VALUE, dbString.getValue());
		values.put(TableString.COL_ANCESTOR_ID, dbString.getAncestorId());

		
		contentResolver.insert(TableString.CONTENT_URI, values);	
	}

	@Override
	public DBString read(Object id) {
		
		Cursor cursor = contentResolver.query(
				TableString.CONTENT_URI, new String[]{TableString.COL_VALUE, TableString.COL_ANCESTOR_ID}, 
				TableString.COL_ID + " = ?", new String[]{id.toString()}, 
				null); 
		
		cursor.moveToFirst();
		
		String value = cursor.getString(cursor.getColumnIndex(TableString.COL_VALUE));
		long ancestorId = cursor.getLong(cursor.getColumnIndex(TableString.COL_ANCESTOR_ID));
		
		DBString dbString = new DBString(ancestorId, value);
		dbString.setId((Long)id);
		return dbString;
	}

	@Override
	public void udpate(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(TableString.COL_ID, dbString.getId());
		values.put(TableString.COL_ANCESTOR_ID, dbString.getAncestorId());
		values.put(TableString.COL_VALUE, dbString.getValue());
		
		contentResolver.update(TableString.CONTENT_URI, values, TableString.COL_ID + " = ?", new String[] { dbString.getId().toString() });
	}

	@Override
	public void delete(DBString dbString) {
		ContentValues values = new ContentValues();
		values.put(TableString.COL_ID, dbString.getId());
		
		contentResolver.delete(TableString.CONTENT_URI, TableString.COL_ID + " = ?", new String[]{dbString.getId()+""});
		
	}

}
