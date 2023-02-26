package com.amazon.internalclassifieds.db;

import java.util.List;

public interface DAO<T> {

	int insert(T object);
	int update(T object);
	int delete(T object);
	List<T> retrieve();
	List<T> retrieve(String sql); //to retrieve data using custom query
	
}
