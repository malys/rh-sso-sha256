package com.lyra.idm.sha256;

import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.UserCredentialModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @deprecated SHA-256 is not a strong hashing algorithm
 */
@Deprecated
public class SHA256PasswordHashProvider implements PasswordHashProvider {


    private final int defaultIterations;
    private final String providerId;

    public SHA256PasswordHashProvider(String providerId, int defaultIterations) {
        this.providerId = providerId;
        this.defaultIterations = defaultIterations;
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, CredentialModel credential) {
        int policyHashIterations = policy.getHashIterations();
        if (policyHashIterations == -1) {
            policyHashIterations = defaultIterations;
        }

        return credential.getHashIterations() == policyHashIterations && providerId.equals(credential.getAlgorithm());
    }

    @Override
    public String encode(String rawPassword, int iter) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");

            byte[] hashed = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            int iterations = iter - 1; //already hashed once above
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                hashed = digest.digest(hashed);
            }

            return new String(Base64.getEncoder().encode(hashed), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(providerId + " not supported.", e);
        }
    }

    @Override
    public void encode(String rawPassword, int iterations, CredentialModel credential) {
        if (iterations == -1) {
            iterations = defaultIterations;
        }

        String password = encode(rawPassword, iterations);
        credential.setAlgorithm(providerId);
        credential.setType(UserCredentialModel.PASSWORD);
        credential.setHashIterations(iterations);
        credential.setValue(password);
    }

    @Override
    public void close() {
        //Not implemented in this case
    }

    @Override
    public boolean verify(String rawPassword, CredentialModel credential) {
        if (rawPassword != null && credential.getValue() != null) {
            return credential.getValue().equals(encode(rawPassword, credential.getHashIterations()));
        } else {
            return false;
        }
    }
}
