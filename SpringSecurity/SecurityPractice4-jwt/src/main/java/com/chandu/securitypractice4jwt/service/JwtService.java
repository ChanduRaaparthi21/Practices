package com.chandu.securitypractice4jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private  String SECRET = " ";

    public JwtService(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            SECRET = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {

        Map<String, Object> Claims = new HashMap<String, Object>();

        return Jwts.builder()
                .setClaims(Claims)
                .addClaims(Claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*30))
                .signWith(secreteKey())
                .compact();
    }


    private Key secreteKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String extractUserName(String authToken) {

        return extractClaim(authToken, Claims::getSubject);
    }

    private <T> T extractClaim(String authToken, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(authToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String authToken) {

        return Jwts.parserBuilder()
                .setSigningKey(secreteKey())
                .build()
                .parseClaimsJws(authToken).getBody();
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = extractUserName(authToken);

        return (username.equals(userDetails.getUsername())&& !isTokenExpired(authToken)) ;
    }

    private boolean isTokenExpired(String authToken) {
        return  extractExpiration(authToken).before(new Date());
    }

    private Date extractExpiration(String authToken) {
        return extractClaim(authToken, Claims::getExpiration);
    }
}
