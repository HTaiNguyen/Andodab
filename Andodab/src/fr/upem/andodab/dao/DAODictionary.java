package fr.upem.andodab.dao;

import android.content.ContentResolver;
import fr.upem.andodab.db.DBDictionary;

public class DAODictionary implements DAO<DBDictionary>{

	private ContentResolver contentResolver;
	
	public DAODictionary(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}
	
	@Override
	public void create(DBDictionary dbDictionary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DBDictionary read(Object id) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void udpate(DBDictionary dbDictionary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(DBDictionary dbDictionary) {
		// TODO Auto-generated method stub
		
	}

}
