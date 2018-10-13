/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.entrypoint;

import com.antony.springrestws.login.dataobject.Users;
import com.antony.springrestws.login.service.LoginService;
import com.antony.springrestws.login.valueobject.UsersVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elcot
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginService loginService;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService >>>>>>>>>>>>> loadUserByUsername");
    
    Users user = loginService.findUserByName(new Users(),userName);

    if(user == null){
        throw new UsernameNotFoundException("UserName "+userName+" not found");
    } 
    return new SecurityUser(user);
}
    


}
