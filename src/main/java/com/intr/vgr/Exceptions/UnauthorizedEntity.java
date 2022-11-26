package com.intr.vgr.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnauthorizedEntity{
    public ResponseEntity<String> notAuthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized");
    }
}
