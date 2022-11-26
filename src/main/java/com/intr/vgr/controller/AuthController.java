package com.intr.vgr.controller;

import com.intr.vgr.Exceptions.UnauthorizedEntity;
import com.intr.vgr.dto.JwtRequest;
import com.intr.vgr.dto.RegisterRequest;
import com.intr.vgr.service.AuthService;
import com.intr.vgr.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private  final AuthService authService;
    private final  JWTUtility jwtUtility;
private AuthController(AuthService authService, JWTUtility jwtUtility ){
    this.authService = authService;
    this.jwtUtility = jwtUtility;
}
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("file") MultipartFile img,
                                         @RequestParam("name") String name
                                        ) throws IOException {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail(email);
    registerRequest.setPassword(password);
    registerRequest.setName(name);
        authService.signup(registerRequest,img);
        return new ResponseEntity<>("User Registered ", HttpStatus.OK);
    }
    @GetMapping("/accountVerification/{id}")
    public ResponseEntity<String> verifyUser(@PathVariable String id){
    log.info("ID_RAJ",id);
        authService.verifyAccount(id);
        return  new ResponseEntity<>("User verfied", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map> loginUser(@RequestBody JwtRequest request) throws Exception {
    return authService.login(request);}
}
