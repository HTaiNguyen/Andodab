package fr.upem.andodab.db;

public class DBDictionary {

	private Long id;
	private Long ownerId;
	private String key;
	private Long valueId;
	private DBObject value;

	public DBDictionary() {
	}

	public DBDictionary(Long ownerId, String key, Long valueId, DBObject value) {
		this.ownerId  = ownerId;
		this.key = key;
		this.valueId = valueId;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwner(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getValueId() {
		return valueId;
	}

	public void setValue(Long valueId) {
		this.valueId = valueId;
	}
	
	public DBObject getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return key + " = "+ value;
	}
}