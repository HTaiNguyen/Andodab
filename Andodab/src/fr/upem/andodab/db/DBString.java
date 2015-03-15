package fr.upem.andodab.db;

public class DBString extends DBObject{

	private String value;

	public DBString(Long ancestorId, String value) {
		super(ancestorId, "STRING");
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}