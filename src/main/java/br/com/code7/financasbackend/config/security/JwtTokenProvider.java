package br.com.code7.financasbackend.config.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expirationTime;
	
	//gerar JWT token
	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expiredDate = new Date(currentDate.getTime() + expirationTime);
		
		String token = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(expiredDate)
				.signWith(Key())
				.compact();
		
		return token;
	}

	private Key Key() {
		return Keys.hmacShaKeyFor(
			Decoders.BASE64.decode(secret)
		);
	}
	//pegar email do jwt token
	public String getEmail(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(Key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		String email = claims.getSubject();
		return email;
	}
	
	//validando jwt token
	public Boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Key())
				.build()
				.parse(token);
			
			return true;
			
		} catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
		
        return false;
	}
}
