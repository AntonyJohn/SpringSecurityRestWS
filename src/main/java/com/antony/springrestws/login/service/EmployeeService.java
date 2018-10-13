/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.login.service;

import com.antony.springrestws.login.dao.EmployeeDao;
import com.antony.springrestws.login.dao.LoginDao;
import com.antony.springrestws.login.dataobject.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author antony
 */

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Employee retrieveEmployee(Employee empDo, Integer empId) {

        System.out.println("ZZZZZZZZZZZZZZZZZZ:findUserByName():"+empId);     
        return employeeDao.get((Class<Employee>) empDo.getClass(),empId);                                      
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Employee> retrieveAllEmployee(Employee empDo) {

        System.out.println("ZZZZZZZZZZZZZZZZZZ:retrieveAllEmployee():");     
        return (List<Employee>) employeeDao.getAll((Class<Employee>) empDo.getClass());                                      
    }
}
