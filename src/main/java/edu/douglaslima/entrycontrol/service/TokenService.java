package edu.douglaslima.entrycontrol.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${projeto.security.jwt.secret}")
	private String secret;
	@Value("${projeto.security.jwt.expiration}")
	private int expiration;
	
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
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		return key;
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
