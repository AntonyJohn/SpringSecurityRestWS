/**
 * 
 */
package com.antony.springrestws.Base.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author antony
 *
 */
public interface Dao<T> {

	/**
	 * This method is used to save domain object to the database. Persisted
	 * classes implement the DomainObject interface by extending the
	 * AbstractDomainObject class.
	 * 
	 * @param domainObject
	 *            - the object to be persisted
	 */
	void save(T domainObject);
	
	/**
	 * This method is different from save, update method usually try to merge
	 * with the existing referenced object.
	 * AbstractDomainObject class.
	 * 
	 * @param domainObject
	 *            - the object to be persisted
	 */
	void update(T domainObject);
	
	
	/**
	 * This method is used to save a collection of any domain objects to the
	 * database. Persisted classes implement the DomainObject interface by
	 * extending the AbstractDomainObject class.
	 * 
	 * @param domainObjects
	 *            - collection to be persisted
	 */
	void saveAll(Collection<T> domainObjects);

	/**
	 * This method retrieves a persisted domain object from the database.
	 * 
	 * @param domainClass
	 *            - the class type to be retrieved
	 * @param id
	 *            - the id of the object to be retrieved
	 * @return - the domain object identified by the id
	 */
	T get(Class<T> domainClass, int id);
	
	/**
	 * This method retrieves a persisted domain object from the database based on given object values (object query)
	 * It tries to mimic Hiberate Criteria API functionality but limited by primitive types i.e. only primitive attributes value
	 * should be populated and passed to this method.
	 * NOTE: ONLY PRIMITIVE TYPE ATTRIBUTES WILL WORK, COMPLEX TYPE ATTRIBUTES WON'T WORK!!!
	 * @param domainClass - class type to be retrieved
	 * @param domainObject - object with query values
	 * @return List of result objects
	 */
	List<T> get(Class<T> domainClass, T domainObject);

	/**
	 * This method returns a collection of all objects of the given class.
	 * 
	 * @param domainClass
	 *            - type of objects to retrieve
	 * @return - Collection of the domainClass objects
	 */
	Collection<T> getAll(Class<T> domainClass);

	/**
	 * This method deletes the persisted domain object from the database.
	 * 
	 * @param domainObject
	 *            - the domain object to be deleted
	 */
	void delete(T domainObject, Integer id);

	/**
	 * Delete all given persistent instances of domain objects from the
	 * database.
	 * 
	 * @param domainObjectList
	 *            - the list of domain object to be deleted.
	 */
	void deleteAll(Collection<T> domainObjectList);	
	
	/**
	 * Exceute stored procedure for the given stored procedure name in the database
	 * and return result for the fieldArray.
	 * 
	 * @param domainObject
	 *            
	 */
	@SuppressWarnings("rawtypes")
	List executeStoredProc(String storedProcName, String[] fieldArray,T domainObject);
	/**
	 * Exceute stored procedure for the given stored procedure name in the database
	 * and return result for the fieldArray. This is mainly for the requirement of 
	 * passing parameter from more than one type of domain object.
	 * 
	 * @param parameterMap
	 *            
	 */
	@SuppressWarnings("rawtypes")
	List executeStoredProc(String storedProcName, String[] fieldArray,HashMap parameterMap);

	@SuppressWarnings("rawtypes")
	List executeQuery(String queryName, String[] fieldArray,HashMap parameterMap);
        
        //T executeNativeQuery(String userName);
        T executeNativeQuery(Class<T> domainClass, String userName);
}
