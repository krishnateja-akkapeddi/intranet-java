package com.intr.vgr.service;

import com.intr.vgr.Exceptions.GaException;
import com.intr.vgr.dto.JwtRequest;
import com.intr.vgr.dto.RegisterRequest;
import com.intr.vgr.model.NotificationEmail;
import com.intr.vgr.model.User;
import com.intr.vgr.model.VerificationToken;
import com.intr.vgr.repository.UserRepository;
import com.intr.vgr.repository.VerificationTokenRepository;


import com.intr.vgr.utility.GaResponse;
import com.intr.vgr.utility.JWTUtility;
import com.intr.vgr.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.lang.String;


@Service
public class AuthService {
    @Autowired
    ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private  final MailService mailService;
    private final JWTUtility jwtUtility;
    private final PasswordEncryption passwordEncryption;

   public AuthService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, JWTUtility jwtUtility, PasswordEncryption passwordEncryption){
       this.userRepository = userRepository;
       this.verificationTokenRepository = verificationTokenRepository;
       this.mailService = mailService;
       this.jwtUtility = jwtUtility;
       this.passwordEncryption = passwordEncryption;
   }

    //Added this annotation as we are interacting with the db
    @Transactional
     public void signup(RegisterRequest registerRequest, MultipartFile img) throws IOException {

       User user = new User();
         user.setEmail(registerRequest.getEmail());
         user.setPassword( passwordEncryption.passwordEncoder(registerRequest.getPassword()));
         user.setCreated(Instant.now());
         user.setEnabled(false);
         user.setName(registerRequest.getName());
     String imgUrl =   imageUploadService.uploadImage(img);
     user.setProfilePic(imgUrl);
         userRepository.save(user);
       String tok =   generateVerificationToken(user);
       mailService.sendMail(new NotificationEmail("Please activate the account", user.getEmail(),
               "Thanks for signing up, please access the link below to complete the verification" +
                       "http://localhost:8080/api/auth/accountVerification/"+tok));
     }
     private String generateVerificationToken(User user){
       String token = UUID.randomUUID().toString();
         VerificationToken verificationToken = new VerificationToken();
         verificationToken.setToken(token);
         verificationToken.setUser(user);
         verificationTokenRepository.save(verificationToken);
         return token;
     }

     public void verifyAccount(String token){
     Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
     verificationToken.orElseThrow(()->new GaException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
     }

     @Transactional
     private void fetchUserAndEnable(VerificationToken token){
        String email = token.getUser().getEmail();
      User user =  userRepository.findByEmail(email).orElseThrow(()->{
       return new GaException("No user found");
        });
      user.setEnabled(true);
      userRepository.save(user);
     }

     public ResponseEntity<Map> login(JwtRequest req) throws Exception {
         User reqUser = userRepository.findByEmail(req.getEmail()).orElseThrow(()->new GaException("No user found"));
         if(reqUser.isEnabled()){
             if(passwordEncryption.passwordMatcher(req.getPassword(),reqUser.getPassword())){
             String token =  jwtUtility.generateToken(reqUser);
             return new GaResponse().customException(token,true,HttpStatus.OK);
             }
             else{
               return new GaResponse().customException("Incorrect password", false,HttpStatus.UNAUTHORIZED);
             }
         }
         else {
             return  new GaResponse().customException("Account not activated",false,HttpStatus.BAD_REQUEST);
         }

   }

}
