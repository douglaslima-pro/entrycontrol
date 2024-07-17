package edu.douglaslima.entrycontrol.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${projeto.security.jwt.secret}")
	private String secret;
	@Value("${projeto.security.jwt.expiration}")
	private int expiration;
	
	public String generateToken(UserDetails user) {
		return Jwts.builder()
				.subject(user.getUsername())
				.issuer("EntryControl")
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + expiration))
				.signWith(signingKey())
				.compact();
	}
	
	private SecretKey signingKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		return key;
	}
	
	public String getUsername(String token) {
		try {
			return Jwts.parser()
					.verifyWith(signingKey())
					.build()
					.parseSignedClaims(token)
					.getPayload()
					.getSubject();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Token nulo ou vazio");
		}
	}
	
	public boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}
	
	public Date getExpiration(String token) {
		return Jwts.parser()
				.verifyWith(signingKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
	}
	
	public boolean validateToken(String token, UserDetails user) {
		return getUsername(token).equals(user.getUsername()) && !isTokenExpired(token);
	}
	
}
