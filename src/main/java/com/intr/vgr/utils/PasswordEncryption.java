package com.intr.vgr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {
   @Value("${P_S_D.secret}")
    private String P_SEC_KEY;
    public String passwordEncoder (String password){
        return new BCryptPasswordEncoder().encode(password+P_SEC_KEY);
    }
    public boolean passwordMatcher (String providedPassword, String savedPassword){
        System.out.println(providedPassword+P_SEC_KEY+" PVD:::::SVP "+savedPassword);
        return new BCryptPasswordEncoder().matches(providedPassword+P_SEC_KEY,savedPassword);
    }
}
