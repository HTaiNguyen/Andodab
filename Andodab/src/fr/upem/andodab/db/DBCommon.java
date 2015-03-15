package fr.upem.andodab.db;

public class DBCommon extends DBObject {

	private String name;
	private boolean sealed;

	public DBCommon() {
	}

	public DBCommon(Long ancestorId, String name, boolean sealed) {
		super(ancestorId, "COMMON");

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
	

	@Override
	public String toString() {
		return name;
	}
}