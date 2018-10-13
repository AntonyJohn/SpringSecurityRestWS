/**
 * 
 */
package com.antony.springrestws.login.controller;


import com.antony.springrestws.login.dataobject.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.antony.springrestws.login.service.LoginService;
import com.antony.springrestws.login.valueobject.UsersVO;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author antony
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    Users users;
    
    //Authenticate	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/authenticate", method=RequestMethod.POST, headers="Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  authenticate(@RequestBody UsersVO loginid){					
    
        System.out.println("Login Controller Inside SampleWS: "+loginid.getId());							
        List obj=loginService.getAuthenticate(loginid);		
        return obj;				 
    }
    
    //Create User	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/user", method=RequestMethod.POST, headers={"Content-Type=application/json","Accept=application/json"})
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  user(@RequestBody UsersVO loginvo){					
    
        users=new Users();
        users.setUsername(loginvo.getUsername());
        users.setPassword(loginvo.getPassword());        
        users.setEnabled((short)1);
        users.setId(loginvo.getId());
        
        System.out.println("Login Controller Inside SampleWS: "+loginvo.getUsername());							
        List obj=loginService.createUser(users);		
        return obj;				 
    }
    
    //Retrieve User Details	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/user", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  retrieveUser(@RequestParam("userID") String userID){					
           
        users=new Users();
        //users.setUsername(userID); 
        users.setId(new Integer(userID));
        List obj=loginService.retrievUser(users);        
        return obj;				 
    }
    
    //Update User	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/user", method=RequestMethod.PUT, headers="Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  updateUser(@RequestBody UsersVO loginvo){					
    
        users=new Users();
        users.setUsername(loginvo.getUsername());      
        users.setPassword(loginvo.getPassword());
        users.setEnabled((short)1);
        users.setId(1);
        
        System.out.println("Login Controller Inside SampleWS update: "+loginvo.getId());							
        List obj=loginService.updateUser(users);		
        return obj;				 
    }
    
    //Delete User	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/user", method=RequestMethod.DELETE, headers="Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  removeUser(@RequestBody UsersVO loginvo){					
    
        users=new Users();
        users.setId(loginvo.getId());        							
        List obj=loginService.removeUser(users);		
        return obj;				 
    }   
    
    public static String encrypt(String source) {
        String md5 = null;
        try {
        MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
        mdEnc.update(source.getBytes(), 0, source.length());
        md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
        } catch (Exception ex) {
        return null;
        }
        return md5;
    }

}
