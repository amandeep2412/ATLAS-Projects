package com.amazon.buspassmanagement.db;

import java.util.List;

//having T will help us to use different types.. in diff methods
//interface created here..focuses on CRUD operations on a table
public interface DAO<T> {

	int insert(T object); //type T: means any datatype
	int update(T object);
	int delete(T object);
	List<T> retrieve();
	//to retrieve custom data..by passing select * query.....
	List<T> retrieve(String sql);
}
