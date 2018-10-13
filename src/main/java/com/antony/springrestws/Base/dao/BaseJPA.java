/**
 * 
 */
package com.antony.springrestws.Base.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.transaction.annotation.Transactional;

import com.antony.springrestws.Base.exception.DataAccessException;

/**
 * @author antony
 *
 */
public abstract class BaseJPA<T>  implements Dao<T> {

	protected EntityManager entityManager;
	
	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#save(com.defiance.ums.base.dataobject.BaseDO)
	 */
	
	@Transactional
	public void save(T domainObject) {
		try {	
			System.out.println("Base JPA");
			entityManager.persist(domainObject);
		} catch (Exception e) {
			throw new DataAccessException("Exception saving "
					+ domainObject.getClass().getName() +" "+e.getMessage(), e);
		}
		
	}  	
	
	
	/* (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#update(com.defiance.ums.base.dataobject.BaseDO, java.lang.Integer)
	 */
	@Transactional
	public void update(T domainObject) {
		try {
					
		   entityManager.merge(domainObject);
			
		} catch (Exception e) {
			throw new DataAccessException("Exception in updating "
					+ domainObject.getClass().getName() +" "+e.getMessage(), e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#save(java.util.Collection)
	 */
	@Transactional
	public void saveAll(Collection<T> domainObjects) {
		try {
			@SuppressWarnings("rawtypes")
			Iterator iterator = domainObjects.iterator();
			while(iterator.hasNext()){			
			entityManager.persist(iterator.next());
			}
		} catch (Exception e) {
			throw new DataAccessException("Exception deleting "
					+  e);
		}
	}

	/* (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#get(java.lang.Class, long)
	 */
	@Transactional
	public T get(Class<T> domainClass, int id) {
		try {
			return entityManager.find(domainClass, new Integer(id));
		} catch (Exception e) {
			throw new DataAccessException("Exception getting "
					+ domainClass.getName() + " with id = " + id, e);
		}
	}
	
	/**
	 * This method will fecth results based on object attribute values. It tries to mimic Hibernate
	 * Criteria API functionality but limited by primitive types i.e. only primitive attributes value
	 * should be populated and passed to this method.
	 * NOTE: ONLY PRIMITIVE TYPE ATTRIBUTES WILL WORK, COMPLEX TYPE ATTRIBUTES WON'T WORK!!!
	 */
	public List<T> get(Class<T> domainClass, T domainObject) {
		
		List<T> results = new ArrayList<T>();

		@SuppressWarnings("rawtypes")
		Map props = null;

		try {
			props = BeanUtils.describe(domainObject);
		}catch(Exception e) {
			return null;
		}
		
		String queryStr = "select c from "+domainObject.getClass().getSimpleName()+" c where ";
		if(props != null) {
			for(Object key : props.keySet()) {
				if(!key.equals("class") && !(key.equals("updatedDate"))) {
					Object value = props.get(key);
					if(value != null && !(value.equals("0")) && !(value.equals("0.0"))) {
						try {
							ConvertUtils.convert(props.get(key), PropertyUtils.getPropertyType(domainObject, key.toString()));
							queryStr += "c."+key+" =:"+key+" and ";
						}catch(Exception e) {}
					}
				}
			}
			
			queryStr = queryStr.substring(0, queryStr.indexOf("and"));
			Query query = entityManager.createQuery(queryStr);
			for(Object key : props.keySet()) {
				if(!key.equals("class")) {
					if(props.get(key) != null) {
						try {
							Object obj = ConvertUtils.convert(props.get(key), PropertyUtils.getPropertyType(domainObject, key.toString()));
							query.setParameter(key.toString(), obj);
						}catch(Exception e) {}
					}
				}
			}
			
			@SuppressWarnings("unchecked")
			List<T> resultList = query.getResultList();
			if(resultList != null) {
				results.addAll(resultList);
			}
		}
		
		return results;
	}

	/** 
	 * This will return all the data for the given entity mapped 
	 * DO class
	 * 
	 * (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#getAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<T> getAll(Class<T> domainClass) {
		String queryStr = "select c from "+domainClass.getSimpleName()+" c";
		  Query query = entityManager.createQuery(queryStr);
		
		return query.getResultList();
	}

	/**
	 * Delete Transaction gives me an error, since find and delete respectively., are 
	 * giving different transactions.So it leads to an error 'java.lang.IllegalArgumentException - removing a
	 * detached instance. To fix this issue, by fetching the actual domain object reference from EntityManager
	 * and removing the object. 
	 * 
	 * (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#delete(com.defiance.ums.base.dataobject.BaseDO)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void delete(T domainObject, Integer id) {
		try {
			domainObject = (T) entityManager.getReference(
					domainObject.getClass(), id);
			entityManager.remove(domainObject);
		} catch (Exception e) {
			throw new DataAccessException("Exception deleting "
					+ domainObject.getClass().getName(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.defiance.ums.base.dao.Dao#deleteAll(java.util.Collection)
	 */
	@Transactional
	public void deleteAll(Collection<T> domainObjectList) {
		
		try {
			@SuppressWarnings("rawtypes")
			Iterator iterator = domainObjectList.iterator();
			while(iterator.hasNext()){
				entityManager.remove(entityManager.merge(iterator.next()));
			}
		} catch (Exception e) {
			throw new DataAccessException("Exception deleting "
					+  e);
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List executeStoredProc(String storedProcName, String[] fieldArray , T domainObject){
		try{
			List results = new ArrayList(); 
			String queryName = "call " + storedProcName ;
			Query query = entityManager.createNativeQuery(queryName);
			Map props = null;				
			props = BeanUtils.describe(domainObject);				
			int i=1;
			for(Object key : props.keySet()) {
				if(!key.equals("class") && !(key.equals("updatedDate"))) {
					Object value = props.get(key);
					if(value != null) {
						try {
							query.setParameter(i,value);
							i++;
							}catch(Exception e) {}
						}
					}
				}
						
				List resultlist = query.getResultList();
				if(resultlist.size() > 0){
					Object[][] requestobj = (Object[][]) resultlist.toArray(new Object[resultlist.size()][]);
					for(int k=0;k<requestobj.length;k++){
						HashMap resultmap = new HashMap();						
						for(int j=0;j<requestobj[k].length;j++){
							resultmap.put(fieldArray[j], requestobj[k][j]);
						}
						results.add(resultmap);
					}
				}	
			return results;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("Exception executing procedure" +e);
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Transactional
	public List executeStoredProc(String storedProcName, String[] fieldArray , HashMap parameterMap){
		try{
			List results = new ArrayList(); 
			String queryName = "call " + storedProcName ;
			Query query = entityManager.createNativeQuery(queryName);
			List<T> resultlist = new ArrayList<T>();			
			int i=1;
			for(Object key : parameterMap.keySet()) {
				Object value = parameterMap.get(key);
				if(value != null) {
					try {
							query.setParameter(i,value);
							i++;
							}catch(Exception e) {}
						}
					}
				if(fieldArray==null){
					
					int result = query.executeUpdate();
				}
				else{
					resultlist = query.getResultList();
				}
				if((resultlist.size() > 0) && (fieldArray!=null)){
					Object[][] requestobj = (Object[][]) resultlist.toArray(new Object[resultlist.size()][]);
					for(int k=0;k<requestobj.length;k++){
						HashMap resultmap = new HashMap();
						for(int j=0;j<requestobj[k].length;j++){
							resultmap.put(fieldArray[j], requestobj[k][j]);
						}
						results.add(resultmap);
					}
				}
			return results;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("Exception executing procedure" +e);
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })	
	public List executeQuery(String queryName, String[] fieldArray , HashMap parameterMap){
		try{
			List results = new ArrayList(); 
			
			Query query = entityManager.createNativeQuery(queryName);
			List<T> resultlist = new ArrayList<T>();			
			int i=1;
			for(Object key : parameterMap.keySet()) {
				Object value = parameterMap.get(key);
				if(value != null) {
					try {
							query.setParameter(i,value);
							i++;
							}catch(Exception e) {}
						}
					}
				if(fieldArray==null){
					int result = query.executeUpdate();
				}
				else{
					resultlist = query.getResultList();
				}
				if((resultlist.size() > 0) && (fieldArray!=null)){
					Object[][] requestobj = (Object[][]) resultlist.toArray(new Object[resultlist.size()][]);
					for(int k=0;k<requestobj.length;k++){
						HashMap resultmap = new HashMap();
						for(int j=0;j<requestobj[k].length;j++){
							resultmap.put(fieldArray[j], requestobj[k][j]);
						}
						results.add(resultmap);
					}
				}
			return results;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("Exception executing procedure" +e);
			
		}
	}

    
        
        
}
