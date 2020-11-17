package org.siriusxi.htec.fa.infra.security;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.infra.security.jwt.JwtTokenHelper;
import org.siriusxi.htec.fa.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Log4j2
public class JwtTokenFilter extends OncePerRequestFilter {
    
    private final UserRepository userRepository;
    
    public JwtTokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!JwtTokenHelper.validate(token)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Get user identity and set it on the spring security context
        var userDetails = userRepository.findByUsername(JwtTokenHelper.getUsername(token))
                                          .orElse(null);
        
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities()
        );
        
        authentication
                .setDetails(new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
        
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

