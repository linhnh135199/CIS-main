package com.example.demo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;

public class TokenUtils {
	/** The Constant signingKey. */
	private static final String SECRET_KEY = "cms";
	
	public static String genarateToken(Object data, long ttlMillis) {

		String signingKey = Base64.encodeBase64String(SECRET_KEY.getBytes());

		Claims claims = Jwts.claims();
		claims.put("data", data);
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(now)
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, signingKey);

		if (ttlMillis > 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}
	
	public static boolean validateToken(String token) {
		Claims claims = getClaims(token);
		Date expireDate = claims.getExpiration();
		Date now = new Date();
		if(now.after(expireDate)) {
			return false;
		}
		return true;
	}
	
	public static Claims getClaims(String jwt) {
		String signingKey = Base64.encodeBase64String(SECRET_KEY.getBytes());
	    Claims claims = Jwts.parser()
	            .setSigningKey(signingKey)
	            .parseClaimsJws(jwt).getBody();
	    return claims;
	}
}
