/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.login.controller;

import com.antony.springrestws.Log;
import com.antony.springrestws.login.dataobject.Employee;
import com.antony.springrestws.login.service.EmployeeService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 *
 * @author antony
 */

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    
	private static @Log Logger LOG;	
	Employee emp;
    
    @Autowired
    private EmployeeService employeeService;
    
    //Retrieve User Details	
    @RequestMapping(value="/retrieveEmployee", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody Employee  retrieveEmployee(@RequestParam("empID") String empID){					
    	LOG.info("Start:: test12355fffdfffs896EmployeeController --> retrieveEmployee()");    	
        emp = new Employee();       
        emp.setId(new Integer(empID));
        Employee obj=employeeService.retrieveEmployee(emp,emp.getId());
        LOG.info("End:: EmployeeController --> retrieveEmployee()");
        return obj;				 
    }
    
    //Retrieve User Details	
    @RequestMapping(value="/retrieveAllEmployee", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public@ResponseBody List<Employee>  retrieveAllEmployee(){					
    	LOG.info("Start:: EmployeeController --> retrieveAllEmployee()");  
        emp = new Employee();
        List<Employee> obj=employeeService.retrieveAllEmployee(emp); 
        LOG.info("End:: EmployeeController --> retrieveAllEmployee()");
        return obj;				 
    }
    
    
}
