/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.login.controller;

import com.antony.springrestws.login.dataobject.Employee;
import com.antony.springrestws.login.dataobject.Users;
import com.antony.springrestws.login.service.EmployeeService;
import com.antony.springrestws.login.service.LoginService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author antony
 */

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    
    Employee emp;
    
    @Autowired
    private EmployeeService employeeService;
    
    //Retrieve User Details	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/retrieveEmployee", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody Employee  retrieveEmployee(@RequestParam("empID") String empID){					
           
        emp = new Employee();
        //users.setUsername(userID); 
        emp.setId(new Integer(empID));
        Employee obj=employeeService.retrieveEmployee(emp,emp.getId());
        obj.setFirstName("XXXXXYMMMZZssCCKKLLMM");
        return obj;				 
    }
    
    //Retrieve User Details	
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/retrieveAllEmployee", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List  retrieveAllEmployee(){					
           
        emp = new Employee();
        //users.setUsername(userID); 
        //emp.setId(new Integer(empID));
        List obj=employeeService.retrieveAllEmployee(emp);        
        return obj;				 
    }
    
    
}
