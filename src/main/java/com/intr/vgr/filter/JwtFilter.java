package com.intr.vgr.filter;

import com.intr.vgr.model.User;
import com.intr.vgr.repository.UserRepository;
import com.intr.vgr.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        String authorization = httpServletRequest.getHeader("Authorization");
        String url = httpServletRequest.getRequestURI();
        System.out.println(url);
        String email="";
        String token="";
        if(url.startsWith("/api/auth")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        System.out.println("ANTMATCH_"+ httpServletRequest.getRequestURI());
        if(null!=authorization){
         token = authorization.substring(7);
        System.out.println("TOK_R__"+token);
        if(null!=token){
         email =  jwtUtility.getUsernameFromToken(token);
        }}
        User user =  userRepository.findByEmail(email).get();
        if(null!=user){
            Boolean isValidToke = jwtUtility.validateToken(token,user);
            if(isValidToke){
                HashMap userContext = new HashMap();
                userContext.put("email", email);
                userContext.put("id",jwtUtility.GetUserIdFromToken(token));
                httpServletRequest.setAttribute("userContext",userContext);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
            else{
                httpServletRequest.setAttribute("error","Login expired, please login again");
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }

        }
        else {
            httpServletRequest.setAttribute("error","Not Authorized!");
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }

    }

}