package daoNoSQL;

import java.util.List;

/**
 * <h1> DAO-Interface</h1>	
 * 
 * @author Katarzyna Wadowska
 *
 */
public interface IGenericDAO<T> {

	/**
	 * save the object in DB
	 * @param object
	 */
	int create(T object);
	
	/**
	 * updates a desired object
	 * @param object
	 */
	void update(T onject, int id);
	
	/**
	 * delete a object
	 * @param object
	 */
	void delete(Class<?> clazz, int id);
	
	/**
	 * returns the desired object 
	 * @param clazz
	 * @param id
	 * @return GenericType
	 */
	T getOneById(Class<?> clazz, int id);
	
	/**
	 * returns all object as a list
	 * @return ArrayList
	 */
	List<T> getAll(Class<?> clazz);
	
}
