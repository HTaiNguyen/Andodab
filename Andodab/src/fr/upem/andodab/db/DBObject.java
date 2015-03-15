package fr.upem.andodab.db;

public abstract class DBObject{

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