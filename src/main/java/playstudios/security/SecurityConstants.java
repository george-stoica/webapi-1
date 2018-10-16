package playstudios.security;

public interface SecurityConstants {
    long EXPIRATION_TIME_MS = 900000; // ms
    String SECRET = "SecretKeyToGenJWTs";
    String AUTHORIZATION_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
}
