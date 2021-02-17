package com.alex.restservice.filter;

import com.alex.restservice.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String authHead = ((HttpServletRequest) request).getHeader("Authorization");
        if (authHead != null) {
            String[] authHeadArr = authHead.split("Bearer");
            if (authHeadArr.length > 1 && authHeadArr[1] != null) {
                String token = authHeadArr[1];
                try {
                    Claims claim = Jwts.parser().setSigningKey(Constants.API_KEY)
                            .parseClaimsJws(token).getBody();
                    httpServletRequest.setAttribute("userId", Integer.parseInt(claim.get("userId").toString()));
                } catch (Exception e) {
                    System.out.println(e);
                    httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/experied token");
                    return;
                }
            } else {
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
                return;
            }
        } else {
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        chain.doFilter(request, response);
    }
}
