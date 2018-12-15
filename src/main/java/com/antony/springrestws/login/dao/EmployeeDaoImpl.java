/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.login.dao;

import com.antony.springrestws.Base.dao.BaseJPA;
import com.antony.springrestws.Base.exception.DataAccessException;
import com.antony.springrestws.login.dataobject.Employee;
import com.antony.springrestws.login.valueobject.EmployeeVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author antony
 */
@Repository
public class EmployeeDaoImpl extends BaseJPA<Employee> implements EmployeeDao{

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
            // TODO Auto-generated method stub
            return this.entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
            // TODO Auto-generated method stub
            this.entityManager=entityManager;
    }

    @Transactional
	public void save(Employee domainObject) {
		// TODO Auto-generated method stub
		//super.save(domainObject);
		try {	
			entityManager.persist(domainObject);
		} catch (Exception e) {
			throw new DataAccessException("Exception saving "
					+ domainObject.getClass().getName() +" "+e.getMessage(), e);
		}
	}

	@Transactional
	public void update(Employee domainObject) {
		// TODO Auto-generated method stub
        try {					
		   entityManager.merge(domainObject);
			
		} catch (Exception e) {
			throw new DataAccessException("Exception in updating "
					+ domainObject.getClass().getName() +" "+e.getMessage(), e);
		}
	}

	@Override
	public void saveAll(Collection<Employee> domainObjects) {
		// TODO Auto-generated method stub
		super.saveAll(domainObjects);
	}

	@Transactional
	public Employee get(Class<Employee> domainClass, int id) {
		// TODO Auto-generated method stub
        try {
			return entityManager.find(domainClass, new Integer(id));
		} catch (Exception e) {
			throw new DataAccessException("Exception getting "
					+ domainClass.getName() + " with id = " + id, e);
		}
	}

	@Transactional
	public List<Employee> get(Class<Employee> domainClass, Employee domainObject) {
		// TODO Auto-generated method stub
        List<Employee> results = new ArrayList<Employee>();

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
			List<Employee> resultList = query.getResultList();
			if(resultList != null) {
				results.addAll(resultList);
			}
		}
		
		return results;
	}

	@Override
	public Collection<Employee> getAll(Class<Employee> domainClass) {
		// TODO Auto-generated method stub
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
		
		try {
			return query.getResultList();
		} catch (NoResultException e ) {
			return null;
		}
	}

	@Transactional
	public void delete(Employee domainObject, Integer id) {
		// TODO Auto-generated method stub
        try {
			domainObject = (Employee) entityManager.getReference(
					domainObject.getClass(), id);
			entityManager.remove(domainObject);
		} catch (Exception e) {
			throw new DataAccessException("Exception deleting "
					+ domainObject.getClass().getName(), e);
		}
	}

	@Override
	public void deleteAll(Collection<Employee> domainObjectList) {
		// TODO Auto-generated method stub
		super.deleteAll(domainObjectList);
	}

	@Override
	public List executeStoredProc(String storedProcName, String[] fieldArray,
			Employee domainObject) {
		// TODO Auto-generated method stub
		return super.executeStoredProc(storedProcName, fieldArray, domainObject);
	}

	@Override
	public List executeStoredProc(String storedProcName, String[] fieldArray,
			HashMap parameterMap) {
		// TODO Auto-generated method stub
		return super.executeStoredProc(storedProcName, fieldArray, parameterMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })	
	@Transactional (isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public List executeQuery(String queryName, String[] fieldArray , HashMap parameterMap){
		try{
			List results = new ArrayList(); 
			System.out.println("entityManager:"+queryName);
			Query query = entityManager.createNativeQuery(queryName);
			List<EmployeeVO> resultlist = new ArrayList<EmployeeVO>();	
			
			int i=1;
			for(Object key : parameterMap.keySet()) {
				System.out.println("key:"+key);
				Object value = parameterMap.get(key);
				if(value != null) {
					try {
							query.setParameter(i,value);
							i++;
							}catch(Exception e) {}
						}
					}
				if(fieldArray==null){
					//entityManager.getTransaction().begin();
					int result = query.executeUpdate();
					//entityManager.getTransaction().commit();				   
				}
				else{
					resultlist = query.getResultList();
				}				
				
				if((resultlist.size() > 0) && (fieldArray!=null)){
					Object[][] requestobj = (Object[][]) resultlist.toArray(new Object[resultlist.size()][]);
					
					System.out.println("requestobj:"+requestobj);
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
    @Override
    public Employee executeNativeQuery(Class<Employee> domainClass, String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
