package io.gwteaching.tmptool.security;

public class SecurityConfigConsts {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_ADD = "/h2-console";
    public static final String SECRETE_KEY = "SecretKeyToGenJWTs";

    public static final String TOKEN_PREFIX = "TMP ";

    public static final String HEADER_STRING = "Authorization";

    public static final long EXPIRATION_TIME = 60_000; // 60s
}
