package com.apps.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.apps.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author doanhai
 *
 */
@Component
public class JwtTokenProvider {

	private String secretKey = "secretKey";

	private long validityInMilliseconds = 3600000;

	@Autowired
	MyUserDetails myUserDetails;

	/**
	 * 
	 */
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * create token with param is username and role
	 * 
	 * @param username
	 * @param role
	 * @return
	 */
	public String createToken(String username, List<Role> role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", role.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull)
				.collect(Collectors.toList()));
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	/**
	 * @param token
	 * @return
	 */
	public org.springframework.security.core.Authentication getAuth(String token) {
		UserDetails userDetails = myUserDetails.loadUserByUsername(getUserNameToToken(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * get username from token
	 * 
	 * @param token
	 * @return
	 */
	public String getUserNameToToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * @param request
	 * @return
	 */
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/**
	 * validate token
	 * 
	 * @param token
	 * @return
	 * @throws JwtException
	 * @throws IllegalArgumentException
	 */
	public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		return true;
	}
}
