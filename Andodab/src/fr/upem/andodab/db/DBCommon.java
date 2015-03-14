package fr.upem.andodab.db;

import android.net.Uri;

public class DBCommon extends DBObject{
	public static final String TABLE_NAME = "common";
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBManager.AUTHORITY + "/" + TABLE_NAME);
	public static final String MIME = "vnd.android.cursor.item/vnd." + DBManager.AUTHORITY + "." + TABLE_NAME;

	public static final String NAME = "name";
	public static final String SEALED = "sealed";

	private String name;
	private boolean sealed;

	public DBCommon() {
	}

	public DBCommon(Long ancestorId, String name, boolean sealed) {
		super(ancestorId);

		this.name = name;
		this.sealed = sealed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSealed() {
		return sealed;
	}

	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}	
}