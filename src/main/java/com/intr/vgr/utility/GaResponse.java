package com.intr.vgr.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GaResponse {
    public ResponseEntity<Map> customException(String message, Boolean success, HttpStatus status){
        HashMap messageObject = new HashMap();
        messageObject.put("message",message);
        messageObject.put("success",success);
        return new ResponseEntity<>(messageObject,status);
    }
}
