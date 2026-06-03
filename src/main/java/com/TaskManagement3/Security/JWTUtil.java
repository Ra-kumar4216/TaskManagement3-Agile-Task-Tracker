package com.TaskManagement3.Security;


import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.TaskManagement3.Entity.UserAuth;
import com.TaskManagement3.Enum.Permission;

import io.jsonwebtoken.Claims; // Naya import
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private final Key key;
    private final long expireToken = 1000L * 60 * 60 * 12; // 12 Hours

    public JWTUtil() {
        String secret = System.getenv("JWT_SECRET");
        
        if (secret == null || secret.isEmpty()) {
            secret = "Replace This with a secreat key!!"; 
        }
        key = Keys.hmacShaKeyFor(secret.getBytes());
        }
    // 1. Method to Generate Token
    public String generateToken(UserAuth user) {
    	
        Map<String, Object> claims = new HashMap<>();
        Set<Permission> perm = RoleBasedPermission.getRolePermission().get(user.getRole());
        
        List<String> permName = perm == null ? new ArrayList<>() : perm.stream().map(Enum::name).collect(Collectors.toList());

        claims.put("permission", permName);

        Date now = new Date();
        Date expire = new Date(now.getTime() + expireToken);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserOfficialEmail())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Method to Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 3. New Method: Get Claims from Token (Line 70)
    public Claims getClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUserEmail(String token) {
        return getClaim(token).getSubject();
    }
    public String extractToken(String header) {
    	if(header !=null && header.startsWith("Bearer ")) {
    		return header.substring(7);
    	}
    	
    	return null;
    }
    
    
}