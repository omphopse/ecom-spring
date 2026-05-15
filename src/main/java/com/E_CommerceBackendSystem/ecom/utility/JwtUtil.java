package com.E_CommerceBackendSystem.ecom.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String Secrate_Key= "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";
	
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Secrate_Key.getBytes());
	}
	
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}
	 
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	  
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	public String generateToken(String username) {
	      Map<String, Object> claims = new HashMap<>();
	      return createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setHeaderParam("typ", "jwt")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 minutes expiration time
                .signWith(getSigningKey())
                .compact();
	}
	
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
