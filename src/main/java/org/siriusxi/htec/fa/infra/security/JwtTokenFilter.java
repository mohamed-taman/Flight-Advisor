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
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

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
        var authToken = getJwtTokenIfValid(request
                                               .getHeader(HttpHeaders.AUTHORIZATION));
        
        String token;
        
        if (authToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        } else token = authToken.get();
        
        // Get user identity and set it on the spring security context
        var userDetails = userRepository
                              .findByUsernameIgnoreCase(JwtTokenHelper.getUsername(token))
                              .orElse(null);
        
        var authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null,
            userDetails == null ? List.of() : userDetails.getAuthorities()
        );
        
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        chain.doFilter(request, response);
    }
    
    /**
     * This method take a header, and check if it contains authorization value,
     * if it is exists then validate this token.
     *
     * @param header that could contain authorization header
     * @return JWT token if exists and valid.
     */
    private Optional<String> getJwtTokenIfValid(String header) {
        String token;
        //check header value is exists
        if (hasText(header) && header.startsWith("Bearer ")) {
            // Get jwt token and validate
            token = header.split(" ")[1].trim();
            if (JwtTokenHelper.validate(token))
                return Optional.of(token);
        }
        
        return Optional.empty();
    }
}

