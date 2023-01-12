package com.intr.vgr.controller;

import com.intr.vgr.ga_responses.GaResponse;
import com.intr.vgr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequestMapping("/auth")
@RestController

public class AuthController {
    @Autowired
    AuthService authService;

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity login(@RequestParam("authId") String authToken) throws GeneralSecurityException, IOException {

        var user = authService.loginUser(authToken);

        return new GaResponse().successResponse(user);

    }

    @GetMapping("/validate-user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity validateUser(@RequestHeader("Authorization") String authToken) {
        var user = authService.validateUser(authToken.substring(7));
        System.out.println("FROM_VAL_USER" + user);
        if (null == user) {
            return new GaResponse().unautorizedResponse();
        } else {
            return new GaResponse().successResponse(user);
        }

    }

}