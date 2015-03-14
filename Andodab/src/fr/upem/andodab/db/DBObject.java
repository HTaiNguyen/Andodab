package fr.upem.andodab.db;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class DBObject implements BaseColumns {
	public static final String TABLE_NAME = "object";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String ID = "id";
	public static final String ANCESTOR_ID = "ancestor_id";
	
	private Long id;
	private Long ancestorId;
	
	public DBObject() {
	}
	
	public DBObject(Long ancestorId) {
		this.ancestorId = ancestorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAncestorId() {
		return ancestorId;
	}

	public void setAncestorId(Long ancestorId) {
		this.ancestorId = ancestorId;
	}
	
}