/**
 * 
 */
package com.antony.springrestws.login.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAuthenticate(UsersVO loginvo) {

		System.out.println("loginDao Inside:" + loginvo.getUsername());
		
		// Query Frame
		String statusQuery = "SELECT username,password,enabled, id FROM users where username=? AND password=?";

		// Result Fields
		String[] fields = { "username","password","enabled", "id" };
		
		// Parameters to pass
		HashMap map = new HashMap();
		map.put("username", loginvo.getUsername());
		map.put("password", loginvo.getPassword());
            System.out.println("map:"+map);
		List userList = loginDao.executeQuery(statusQuery, fields, map);
		return userList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getEmployeeDetails(UsersVO loginvo) {

		System.out.println("loginDao Inside:" + loginvo.getUsername());
		String statusQuery = "SELECT e.id,e.employee_number,CONCAT(e.first_name,' ',e.last_name),b.name FROM employees e inner join bands b on b.id=e.band_id where e.employee_number=?";

		String[] fields = { "userID", "userName", "employeeName", "band" };
		HashMap map = new HashMap();
		map.put("employee_number", loginvo.getUsername());
		List periodlist = loginDao.executeQuery(statusQuery, fields, map);
		return periodlist;
	}
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
	public List createUser(Users logindo) {
            
            List results = new ArrayList(); 
            HashMap resultmap = new HashMap();            
            loginDao.save(logindo);
            System.out.println("GetID:"+logindo.getId());
            resultmap.put("id",logindo.getId());
            results.add(resultmap);
            return results;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
	public List updateUser(Users logindo) {
            
            List results = new ArrayList(); 
            HashMap resultmap = new HashMap();            
            loginDao.update(logindo);            
            resultmap.put("id",logindo.getId());
            results.add(resultmap);
            return results;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
	public List removeUser(Users logindo) {
            
            List results = new ArrayList(); 
            HashMap resultmap = new HashMap();            
            loginDao.delete(logindo,logindo.getId());           
            resultmap.put("result","Deleted Sucessfully");
            results.add(resultmap);
            return results;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
	public List retrievUser(Users logindo) {
                        
            List results = new ArrayList(); 
            HashMap resultmap = new HashMap();                 
            Users resultObj=loginDao.get((Class<Users>) logindo.getClass(),Integer.parseInt(logindo.getId().toString()));                        
            resultmap.put("userName",resultObj.getUsername());
            resultmap.put("passWord",resultObj.getPassword());
            results.add(resultmap);
            return results;
                        
        }
        
        
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
	public Users findUserByName(Users logindo,String userName) {

		          System.out.println("ZZZZZZZZZZZZZZZZZZ:findUserByName():"+userName);
		
		/* Query Frame
		String statusQuery = "SELECT username,password,enabled, id FROM users where username=?";

		// Result Fields
		String[] fields = { "username","password","enabled", "id" };
		
		// Parameters to pass
		HashMap map = new HashMap();
		map.put("username", userName);
		
            System.out.println("map:"+map);
		List userList = loginDao.executeQuery(statusQuery, fields, map);
                
                Users userObj = null;
                if(userList != null && userList.size()!=0) {
                    userObj = (Users) userList.get(0);
                }*/
                
                 
		return loginDao.executeNativeQuery((Class<Users>) logindo.getClass(),userName);      
                
                //return loginDao.executeNativeQuery(userName);
	}
        /*
         *
         * @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Users> retrievUser(Users logindo) {
                        
            List results = new ArrayList(); 
            HashMap resultmap = new HashMap();                 
            //Users resultObj=loginDao.get((Class<Users>) logindo.getClass(),Integer.parseInt(logindo.getUserID().toString()));
            List<Users> resultObj=loginDao.get((Class<Users>) logindo.getClass(),logindo);
            System.out.println("GetID:"+logindo.getUserID());
            //resultmap.put("userName",resultObj.getUserName());
            //resultmap.put("passWord",resultObj.getPassWord());
            results.add(resultmap);
            return resultObj;
        }*/
}
