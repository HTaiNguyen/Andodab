package fr.upem.andodab.dao;

public interface DAO<T> {
	public void create(T t);
	public T read(Object primaryKey);
	public void udpate(T t);
	public void delete(T t);
}
