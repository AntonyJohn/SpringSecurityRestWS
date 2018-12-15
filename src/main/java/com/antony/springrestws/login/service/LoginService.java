/**
 * 
 */
package com.antony.springrestws.login.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antony.springrestws.Log;
import com.antony.springrestws.login.dao.LoginDao;
import com.antony.springrestws.login.dataobject.Users;

import com.antony.springrestws.login.valueobject.UsersVO;
import java.util.ArrayList;

/**
 * @author antony
 * 
 */
@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;     
	private static @Log Logger LOG;	
        
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAuthenticate(UsersVO loginvo) {

		LOG.info("LoginService --> getAuthenticate()");
		
		// Query Frame
		String statusQuery = "SELECT username,password,enabled, id FROM users where username=? AND password=?";

		// Result Fields
		String[] fields = { "username","password","enabled", "id" };
		
		// Parameters to pass
		HashMap map = new HashMap();
		map.put("username", loginvo.getUsername());
		map.put("password", loginvo.getPassword());
		List userList = loginDao.executeQuery(statusQuery, fields, map);
		return userList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getEmployeeDetails(UsersVO loginvo) {

		LOG.info("LoginService --> getEmployeeDetails()");
		String statusQuery = "SELECT e.id,e.employee_number,CONCAT(e.first_name,' ',e.last_name),b.name FROM employees e inner join bands b on b.id=e.band_id where e.employee_number=?";
		String[] fields = { "userID", "userName", "employeeName", "band" };
		HashMap map = new HashMap();
		map.put("employee_number", loginvo.getUsername());
		List periodlist = loginDao.executeQuery(statusQuery, fields, map);
		return periodlist;
	}
        
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List createUser(Users logindo) {
    	LOG.info("LoginService --> createUser()");
        List results = new ArrayList(); 
        HashMap resultmap = new HashMap();            
        loginDao.save(logindo);
        resultmap.put("id",logindo.getId());
        results.add(resultmap);
        return results;
    }
        
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List updateUser(Users logindo) {
    	LOG.info("LoginService --> updateUser()");
        List results = new ArrayList(); 
        HashMap resultmap = new HashMap();            
        loginDao.update(logindo);            
        resultmap.put("id",logindo.getId());
        results.add(resultmap);
        return results;
    }
        
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List removeUser(Users logindo) {
    	LOG.info("LoginService --> removeUser()");   
        List results = new ArrayList(); 
        HashMap resultmap = new HashMap();            
        loginDao.delete(logindo,logindo.getId());           
        resultmap.put("result","Deleted Sucessfully");
        results.add(resultmap);
        return results;
    }
        
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List retrievUser(Users logindo) {
    	LOG.info("LoginService --> retrievUser()");           
        List results = new ArrayList(); 
        HashMap resultmap = new HashMap();                 
        Users resultObj=loginDao.get((Class<Users>) logindo.getClass(),Integer.parseInt(logindo.getId().toString()));                        
        resultmap.put("userName",resultObj.getUsername());
        resultmap.put("passWord",resultObj.getPassword());
        results.add(resultmap);
        return results;                   
    }
                        
	@SuppressWarnings({ "unchecked" })
	public Users findUserByName(Users logindo,String userName) {
		LOG.info("LoginService --> findUserByName()"); 
		return loginDao.executeNativeQuery((Class<Users>) logindo.getClass(),userName);                      
	}       
}
