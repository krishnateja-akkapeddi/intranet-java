package com.intr.vgr.ga_responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GaResponse {
    public ResponseEntity successResponse (Object body){
        HashMap responseObject =new HashMap();
        responseObject.put("success",true);
        responseObject.put("body",body);
        return new ResponseEntity(responseObject,HttpStatus.OK);
    }
    public ResponseEntity unautorizedResponse (){

        HashMap responseObject =new HashMap();
responseObject.put("success",false);
responseObject.put("message","unauthorized");
        return new ResponseEntity(responseObject,HttpStatus.UNAUTHORIZED);
    }
    public ResponseEntity customResponse (Object body,HttpStatus httpStatus ){
        return new ResponseEntity(body,httpStatus);
    }
}
