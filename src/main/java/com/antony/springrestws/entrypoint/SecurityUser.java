/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.entrypoint;

import com.antony.springrestws.Log;
import com.antony.springrestws.login.dataobject.UserRoles;
import com.antony.springrestws.login.dataobject.Users;
import org.slf4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author antony
 */
public class SecurityUser extends Users implements UserDetails{
	
    public SecurityUser(Users user) {
	    if(user != null) {
	        this.setId(user.getId());
	        this.setUsername(user.getUsername());       
	        this.setPassword(user.getPassword());
	        this.setEnabled(user.getEnabled());    
	        this.setUserRolesCollection(user.getUserRolesCollection());
	    }
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	System.out.println("Start:: SecurityUser --> getAuthorities()"); 
        Collection<GrantedAuthority> authorities;
        authorities = new ArrayList<>();
        Collection<UserRoles> userRoles = this.getUserRolesCollection();
        if(userRoles != null) {
            for (UserRoles role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
                authorities.add(authority);
            }
        }
        System.out.println("End:: SecurityUser --> getAuthorities()"); 
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
