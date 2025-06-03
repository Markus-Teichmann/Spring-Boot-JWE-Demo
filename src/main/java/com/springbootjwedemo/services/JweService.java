package com.springbootjwedemo.services;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.springbootjwedemo.JweConfig;
import com.springbootjwedemo.dtos.AnythingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JweService {
    private JweConfig config;

    public Jwe generateToken(AnythingDto request) throws Exception {
        var claimSetBuilder = new JWTClaimsSet.Builder()
                .subject(request.getEmail())
                .claim("password", request.getPassword())
                .issuer(config.getIssuer())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * config.getTokenExpiration()));
        return new Jwe(
                claimSetBuilder.build(),
                config.signingKey(),
                config.encryptionKey()
        );
    }

    public Jwe parse(String token) {
        try {
            JWEObject jwe = JWEObject.parse(token);
            jwe.decrypt(new RSADecrypter(config.encryptionKey().toRSAPrivateKey()));
            SignedJWT signedJWT = jwe.getPayload().toSignedJWT();
            if(signedJWT.verify(new RSASSAVerifier(config.signingKey()))) {
                return new Jwe(
                        signedJWT.getJWTClaimsSet(),
                        config.signingKey(),
                        config.encryptionKey()
                );
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}