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
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class MyAuthenticationDetailsSource extends
    WebAuthenticationDetailsSource {

  @Override
  public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new MyAuthenticationDetails(context);
  }

  @SuppressWarnings("serial")
  class MyAuthenticationDetails extends WebAuthenticationDetails {

    private final String referer;

    public MyAuthenticationDetails(HttpServletRequest request) {
      super(request);
      this.referer = request.getHeader("Referer");
    }

    public String getReferer() {
      return referer;
    }
  }

}