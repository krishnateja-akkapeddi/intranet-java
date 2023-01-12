package com.intr.vgr.service.implementation;

import com.intr.vgr.bo.UserFromOauthBo;
import com.intr.vgr.model.User;
import com.intr.vgr.repository.RoleRepository;
import com.intr.vgr.repository.UserRepository;
import com.intr.vgr.service.AuthService;
import com.intr.vgr.utility.GoogleOAuthService;
import com.intr.vgr.utility.ImageUploadService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class AuthServiceImplementation implements AuthService {
    @Autowired
    GoogleOAuthService googleOAuthService;
    @Autowired
    ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User loginUser(String authToken) throws GeneralSecurityException, IOException {
        UserFromOauthBo userFromOAuth = googleOAuthService.verifyToken(authToken);
        var userThere = userRepository.findByEmail(userFromOAuth.getEmail()).isPresent();
        System.out.println("USER_THERE_ " + userThere);
        if (userThere == false) {
            User user = new User();
            user.setName(userFromOAuth.getName());
            user.setEmail(userFromOAuth.getEmail());
            user.setRole(roleRepository.findById(1L).get());
            user.setProfilePic(userFromOAuth.getImageUrl());
            User savedUser = userRepository.save(user);
            return savedUser;
        } else if (userThere == true) {
            return userRepository.findByEmail(userFromOAuth.getEmail()).get();
        } else {
            return null;
        }

    }

    @Override
    public User validateUser(String authToken) {
        log.info("Verifiying user");
        try {
            UserFromOauthBo userFromOAuth = googleOAuthService.verifyToken(authToken);
            var user = userRepository.findByEmail(userFromOAuth.getEmail()).get();
            System.out.println("OAUTH_" + userFromOAuth);
            log.info("User is valid");
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("Failed fetching userFromOAuth");
            return null;
        }
    }
}
