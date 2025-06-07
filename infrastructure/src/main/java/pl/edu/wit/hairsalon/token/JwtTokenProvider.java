package pl.edu.wit.hairsalon.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public interface JwtTokenProvider {

    String generateAccessToken(UserDetails user);

    String generateRefreshToken(String username);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token);

    @Slf4j
    @Component
    @RequiredArgsConstructor
    class Default implements JwtTokenProvider {

        private final OAuthServerClientProperties clientProperties;

        @Override
        public String generateAccessToken(UserDetails user) {
            Date now = new Date();
            Date expiry = new Date(now.getTime() + clientProperties.getAccessTokenValidity().toMillis());

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList())
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .signWith(toSecretKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        @Override
        public String generateRefreshToken(String username) {
            var expiry = new Date(System.currentTimeMillis() + clientProperties.getRefreshTokenValidity().toMillis());

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expiry)
                    .signWith(toSecretKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        @Override
        public String getUsernameFromToken(String token) {
            return parseClaims(token).getSubject();
        }

        @Override
        public boolean isTokenValid(String token) {
            try {
                parseClaims(token);
                return true;
            } catch (JwtException | IllegalArgumentException ex) {
                log.error("JwtTokenProvider isTokenValid {}", ex.getMessage());
                return false;
            }
        }

        private Claims parseClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(toSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        
        private SecretKey toSecretKey() {
            return Keys.hmacShaKeyFor(
                    clientProperties.convertClientPasswordToBytes()
            );
        }
        
    }
    
}
