package com.intr.vgr.utility;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.intr.vgr.Exceptions.GaException;
import com.intr.vgr.bo.UserFromOauthBo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleOAuthService {

    NetHttpTransport transport= new NetHttpTransport();
JacksonFactory jsonFactory = new JacksonFactory();
public UserFromOauthBo verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList("133690514125-rsd4q5iaaoju3dpavpbh309b398toup6.apps.googleusercontent.com"))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();

// (Receive idTokenString by HTTPS POST)

    GoogleIdToken idToken = verifier.verify(idTokenString);
    if (idToken != null) {
        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
        System.out.println("EMAIL_FROM_OAUTH__"+email);
    UserFromOauthBo user = new UserFromOauthBo();
    user.setEmail(email).setName(name).setImageUrl(pictureUrl);

return user;
    } else {
        throw new GaException("Unauthorized");
    }
}
}
