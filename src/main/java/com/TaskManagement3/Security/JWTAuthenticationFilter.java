package com.TaskManagement3.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    

    @Autowired
    private CustomUserDetailsService customerDetails;

    @Autowired
    private TokenBlockListService tokenBlockService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = jwtUtil.extractToken(header);
        
        // String token = null;
        
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
        
             token = header.substring(7);
        
        }
        if(token !=null) {
        	if(tokenBlockService.isBlock(token)) {
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        		response.getWriter().write("Token is logged out");
        	}
        	
        }

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        if (token != null) {
            // Check if token is blocked
            if (tokenBlockService.isBlock(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is logged");
                return;
            }
        }

        if (token != null && jwtUtil.validateToken(token)) {

            String userEmail = jwtUtil.getUserEmail(token);

            try {
                UserDetails userDetails = customerDetails.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
