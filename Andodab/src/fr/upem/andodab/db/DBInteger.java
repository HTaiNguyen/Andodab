package fr.upem.andodab.db;

public class DBInteger extends DBObject{
	
	private Long value;
	
	public DBInteger() {
	}

	public DBInteger(Long ancestorId, Long value) {
		super(ancestorId, "INT");
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
}