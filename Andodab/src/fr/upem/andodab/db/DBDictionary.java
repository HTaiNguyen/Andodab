package fr.upem.andodab.db;

public class DBDictionary {

	private Long id;
	private Long ownerId;
	private String key;
	private Long valueId;
	private String valueName;

	public DBDictionary() {
	}

	public DBDictionary(Long ownerId, String key, Long valueId, String valueName) {
		this.ownerId  = ownerId;
		this.key = key;
		this.valueId = valueId;
		this.valueName = valueName;
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

	@Override
	public String toString() {
		return key + " = "+ valueName;
	}
}