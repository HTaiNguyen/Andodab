package fr.upem.andodab.db;


public class DBFloat extends DBObject{
	
	private float value;
	
	public DBFloat() {
	}

	public DBFloat(Long ancestorId, float value) {
		super(ancestorId, "FLOAT");
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	
}