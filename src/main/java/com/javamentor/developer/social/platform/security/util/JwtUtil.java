package com.javamentor.developer.social.platform.security.util;

import com.javamentor.developer.social.platform.models.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.sessionTime}")
    private long sessionTime;

    @Value("${jwt.emailVerificationTime}")
    private long emailVerificationTime;

    public String extractUsername( String token ) {
        return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration( String token ) {
        return extractClaim(token , Claims::getExpiration);
    }

    public <T> T extractClaim( String token , Function<Claims, T> claimsResolver ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims( String token ) throws MalformedJwtException, ExpiredJwtException {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired( String token ) {
        return extractExpiration(token).before(new Date());
    }

    public String createEmailVerificationToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + emailVerificationTime))
                .signWith(SignatureAlgorithm.HS256 , SECRET_KEY).compact();
    }

    public String createToken( Map<String, Object> claims , String subject ) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + sessionTime))
                .signWith(SignatureAlgorithm.HS256 , SECRET_KEY).compact();
    }

    public String generateToken( UserDetails userDetails ) {
        Map<String, Object> claims = new HashMap<>();
        String commaSeparatedListOfAuthorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        claims.put("authorities" , commaSeparatedListOfAuthorities);
        return createToken(claims , userDetails.getUsername());
    }

    public Boolean validateToken( String token , UserDetails userDetails ) {
        final String username = extractUsername(token);
        return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
