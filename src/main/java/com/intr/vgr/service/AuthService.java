package com.intr.vgr.service;

import com.intr.vgr.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService{
    public User loginUser(String authToken) throws GeneralSecurityException, IOException;
    public User validateUser(String authToken);
}