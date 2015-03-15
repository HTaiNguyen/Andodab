package fr.upem.andodab.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.table.TableCommon;
import fr.upem.andodab.table.TableObject;

public class DAOCommon implements DAO<DBCommon>{

	private ContentResolver contentResolver;

	public DAOCommon(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void create(DBCommon dbCommon) {
		ContentValues values = new ContentValues();
		Uri uri = contentResolver.insert(TableObject.CONTENT_URI, values);	
		
		dbCommon.setId(ContentUris.parseId(uri));

		values.put(TableCommon.COL_ID, dbCommon.getId());
		values.put(TableCommon.COL_ANCESTOR_ID, dbCommon.getAncestorId());
		values.put(TableCommon.COL_NAME, dbCommon.getName());
		values.put(TableCommon.COL_SEALED, dbCommon.isSealed());

		contentResolver.insert(TableCommon.CONTENT_URI, values);
	}

	@Override
	public DBCommon read(Object id) {
		Cursor cursor = contentResolver.query(
				TableCommon.CONTENT_URI, new String[] { TableCommon.COL_ANCESTOR_ID, TableCommon.COL_NAME, TableCommon.COL_SEALED },
				TableCommon.COL_ID + " = ?", new String[] { id.toString() },
				null);
		
		cursor.moveToFirst();
		long ancestorId = cursor.getLong(cursor.getColumnIndex(TableCommon.COL_ANCESTOR_ID));
		String name = cursor.getString(cursor.getColumnIndex(TableCommon.COL_NAME));
		boolean sealed = cursor.getInt(cursor.getColumnIndex(TableCommon.COL_SEALED)) > 0;

		DBCommon dbCommon = new DBCommon(ancestorId, name, sealed);
		dbCommon.setId((Long) id);

		return dbCommon;
	}

	public List<DBCommon> findAll() {
		ArrayList<DBCommon> dbCommons = new ArrayList<>();
		Cursor cursor = contentResolver.query(
				TableCommon.CONTENT_URI, new String[] {TableCommon.COL_ID, TableCommon.COL_ANCESTOR_ID, TableCommon.COL_NAME, TableCommon.COL_SEALED},
				null, null, 
				TableCommon.COL_NAME);

		if (cursor.moveToFirst()) {
			do {
				Long id = cursor.getLong(cursor.getColumnIndex(TableCommon.COL_ID));
				String name = cursor.getString(cursor.getColumnIndex(TableCommon.COL_NAME));
				boolean sealed = cursor.getInt(cursor.getColumnIndex(TableCommon.COL_SEALED)) > 0;
				long ancestorId = cursor.getLong(cursor.getColumnIndex(TableCommon.COL_ANCESTOR_ID));

				DBCommon dbCommon = new DBCommon(ancestorId, name, sealed);
				dbCommon.setId(id);

				dbCommons.add(dbCommon);
			} while (cursor.moveToNext());
		}

		return dbCommons;

	}

	public List<DBCommon> findByAncestor(Long ancestorId) {
		ArrayList<DBCommon> dbCommons = new ArrayList<DBCommon>();
		Cursor cursor = contentResolver.query(
				TableCommon.CONTENT_URI, new String[] { TableCommon.COL_NAME, TableCommon.COL_SEALED, TableCommon.COL_ID }, 
				TableCommon.COL_ANCESTOR_ID + " = ?", new String[] { ancestorId.toString() }, 
				TableCommon.COL_NAME);

		if (cursor.moveToFirst()) {
			do {
				Long id = cursor.getLong(cursor.getColumnIndex(TableCommon.COL_ID));
				String name = cursor.getString(cursor.getColumnIndex(TableCommon.COL_NAME));
				boolean sealed = cursor.getInt(cursor.getColumnIndex(TableCommon.COL_SEALED)) > 0;
				DBCommon dbCommon = new DBCommon(ancestorId, name, sealed);
				dbCommon.setId(id);
				dbCommons.add(dbCommon);
			} while (cursor.moveToNext());
		}

		return dbCommons;
	}

	@Override
	public void udpate(DBCommon dbCommon) {
		ContentValues values = new ContentValues();
		values.put(TableCommon.COL_ANCESTOR_ID, dbCommon.getAncestorId());
		values.put(TableCommon.COL_NAME, dbCommon.getName());
		values.put(TableCommon.COL_SEALED, dbCommon.isSealed());

		contentResolver.update(TableCommon.CONTENT_URI, values, TableCommon.COL_ID + " = ?", new String[] { dbCommon.getId().toString() });
	}

	@Override
	public void delete(DBCommon dbCommon) {
		contentResolver.delete(TableCommon.CONTENT_URI, TableCommon.COL_ID + " = ?", new String[] { dbCommon.getId().toString() });
	}
}
