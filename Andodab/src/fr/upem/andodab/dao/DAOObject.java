package fr.upem.andodab.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import fr.upem.andodab.db.DBObject;
import fr.upem.andodab.table.TableObject;

public class DAOObject implements DAO<DBObject> {
	
	private ContentResolver contentResolver;
	private DAOCommon daoCommon;
	private DAOFloat daoFloat;
	private DAOInteger daoInteger;
	private DAOString daoString;
	
	public DAOObject(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
		daoCommon = new DAOCommon(contentResolver);
		daoFloat = new DAOFloat(contentResolver);
		daoInteger = new DAOInteger(contentResolver);
		daoString = new DAOString(contentResolver);
	}
	
	@Override
	public void create(DBObject obj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DBObject read(Object id) {
		Cursor cursor = contentResolver.query(
				TableObject.CONTENT_URI, new String[] { TableObject.COL_TYPE },
				TableObject.COL_ID + " = ?", new String[] { id.toString() },
				null);
		
		if(!cursor.moveToFirst()) {
			return null;
		}
		
		String type = cursor.getString(cursor.getColumnIndex(TableObject.COL_TYPE ));

		switch(type) {
			case "COMMON":
				return daoCommon.read(id);
			case "FLOAT":
				return daoFloat.read(id);
			case "INT":
				return daoInteger.read(id);
			case "STRING":
				return daoString.read(id);
			default:
				return null;
		}
	}

	@Override
	public void udpate(DBObject obj) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void delete(DBObject obj) {
		throw new UnsupportedOperationException();
		
	}

}
