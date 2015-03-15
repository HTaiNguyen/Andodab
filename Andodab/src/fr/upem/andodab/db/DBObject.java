package fr.upem.andodab.db;

public abstract class DBObject{

	private Long id;
	private Long ancestorId;
	private String type;

	public DBObject() {
	}

	public DBObject(Long ancestorId, String type) {
		this.ancestorId = ancestorId;
		this.type = type;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}