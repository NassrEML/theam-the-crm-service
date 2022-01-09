package com.nassreml.crm.security.jwt;

public class JwtException extends RuntimeException {

    private static final String DESCRIPTION = "Jwt exception";

    public JwtException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
