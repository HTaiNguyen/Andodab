package fr.upem.andodab.object;

import java.util.HashMap;

public class AndodabObject {
	private Long id;
	private String name;
	private String value;
	private AndodabObjectType type;
	private Long parentId;
	private HashMap<String, AndodabObject> dictionary;
	
	public AndodabObject(Long id) {
		this.id = id;
	}
	
	public AndodabObject(Long id, String name, String value, AndodabObjectType type, Long parentId) {
		this.id = id;
		this.name = name; 
		this.value = value;
		this.type = type;
		this.parentId = parentId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public AndodabObjectType getType() {
		return type;
	}
	
	public void setType(AndodabObjectType type) {
		this.type = type;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
