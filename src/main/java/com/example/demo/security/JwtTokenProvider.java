package com.example.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.model.ERole;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private long expirationTimeInSeconds;

	@Autowired
	private UserRepository userRepository;
	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date now = new Date() ;
		Date expirationDate = new Date(now.getTime() + (1000* expirationTimeInSeconds));
		
		User user = userRepository.findByEmail(username).get();
		
		var jwt = Jwts.builder()
			.setSubject(username)
			.claim("name", user.getFirstName() + " " + user.getLastName())
			.setIssuedAt(now)
			.setExpiration(expirationDate);
		jwt.claim("admin",user.getRoles().stream().filter(r -> r.getRoleName() == ERole.ROLE_ADMIN).count() > 0 );
		jwt.claim("golden",user.getRoles().stream().filter(r -> r.getRoleName() == ERole.ROLE_USER_GOLDEN).count() > 0 );

		return jwt.signWith(key()).compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from Jwt token
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		String username = claims.getSubject();
		return username;
	}

	// validate Jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
