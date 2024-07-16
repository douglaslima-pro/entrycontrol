package edu.douglaslima.entrycontrol.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	private int expiration = 3_600_000;
	
	public String generateToken(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + expiration))
				.signWith(signingKey())
				.compact();
	}
	
	private SecretKey signingKey() {
		return Jwts.SIG.HS512.key().build();
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(signingKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(signingKey())
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (IllegalArgumentException | JwtException e) {
			return false;
		}
	}
	
}
