package com.intr.vgr.filter;

import com.intr.vgr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String url = httpServletRequest.getRequestURI();
        System.out.println(url);
        String email = "";
        String token = "";
        if (url.startsWith("/api/v1/auth")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        System.out.println("ANTMATCH_" + httpServletRequest.getRequestURI());
        if (null != authorization) {
            token = authorization.substring(7);
            System.out.println("TOK_R__" + token);
            if (null != token) {
                var emailfromauth = authService.validateUser(token);
                if (emailfromauth != null) {
                    httpServletRequest.setAttribute("unauthorized", false);
                    httpServletRequest.setAttribute("user", emailfromauth.getEmail());
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletRequest.setAttribute("unauthorized", true);
                    httpServletRequest.setAttribute("user", "No user");
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                }
            }

        } else {
            httpServletRequest.setAttribute("unauthorized", true);
            httpServletRequest.setAttribute("user", "No user");

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }
}
