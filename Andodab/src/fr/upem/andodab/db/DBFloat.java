package fr.upem.andodab.db;


public class DBFloat extends DBObject{
	
	private Float value;
	
	public DBFloat() {
	}

	public DBFloat(Long ancestorId, Float value) {
		super(ancestorId, "FLOAT");
		this.value = value;
	}
	
	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}