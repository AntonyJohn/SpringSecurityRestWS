/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antony.springrestws.entrypoint;

/**
 *
 * @author Elcot
 */
import com.antony.springrestws.entrypoint.MyAuthenticationDetailsSource.MyAuthenticationDetails;
import com.antony.springrestws.login.dataobject.Users;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;



import java.util.Collection;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
 

 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
    @Autowired
    private CustomUserDetailsService userService;
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationProvider <<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>> authenticate()");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
 
        //Users user = (Users) userService.loadUserByUsername(username);
        SecurityUser secUser = (SecurityUser) userService.loadUserByUsername(username);
 
        if (secUser == null) {
            throw new BadCredentialsException("Username not found.");
        }
 
        System.out.println("Password:"+password+" DB Password:"+secUser.getPassword());
        if (!password.equals(secUser.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
 
        //In-progress>>>>>>>>>>>>>>>> <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        //Collection<? extends GrantedAuthority> authorities = user.getUserRolesCollection();
        
        Collection<? extends GrantedAuthority> authorities =secUser.getAuthorities();
        return new UsernamePasswordAuthenticationToken(secUser, password, authorities);
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
/*public class MyAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    // cast as it pass the support method
    UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
    String principal = (String) auth.getPrincipal();
    String credential = (String) auth.getCredentials();

    Object obj = auth.getDetails();
    if (obj instanceof MyAuthenticationDetails) {
      MyAuthenticationDetails details = (MyAuthenticationDetails) obj;
      // Doing the extra check such as referer
      if ("http://example.com/referer".equalsIgnoreCase(details.getReferer())) {
        // do the further check ...
        // return the result if passed validation
        UsernamePasswordAuthenticationToken result = 
            new UsernamePasswordAuthenticationToken(principal, credential);
        return result;
      }
    }

    throw new BadCredentialsException("Bad Authentication");

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);

  }
}
*/