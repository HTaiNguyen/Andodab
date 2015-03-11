package fr.upem.andodab.provider;

import java.util.HashMap;

public class ADObject {
	private long id;
	private String name;
	private ADObject ancestor;
	private HashMap<String, ADObject> dictionary;
	private boolean sealed;

	public ADObject(long id, String name, ADObject ancestor, boolean sealed) {
		this.id = id;
		this.name = name; 
		this.ancestor = ancestor; 
		this.dictionary = new HashMap<String, ADObject>();
		this.sealed = sealed;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ADObject getAncestor() {
		return ancestor;
	}

	public HashMap<String, ADObject> getDictionary() {
		return dictionary;
	}

	public boolean isSealed() {
		return sealed;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAncestor(ADObject ancestor) {
		this.ancestor = ancestor;
	}

	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}

	public void addKeyValue(String key, ADObject value) {
		dictionary.put(key, value);
	}

	@Override
	public String toString() {
		return name;
	}
}