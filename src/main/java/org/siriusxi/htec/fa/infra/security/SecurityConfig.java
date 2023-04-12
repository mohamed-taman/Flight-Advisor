package org.siriusxi.htec.fa.infra.security;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.Role;
import org.siriusxi.htec.fa.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;
import static org.springframework.security.core.context.SecurityContextHolder.setStrategyName;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true
)
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final String allowedOrigins;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter,
                          @Value("${app.allowedOrigins:*}") String allowedOrigins,
                          UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.allowedOrigins = allowedOrigins;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        // Inherit security context in async function calls
        setStrategyName(MODE_INHERITABLETHREADLOCAL);
    }

    // Configure DaoAuthenticationProvider for username and password
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(this.passwordEncoder);

        return authProvider;
    }

    // Expose authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.userService;
    }

    // Security configurations
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // List of Swagger URLs, root page, Our public endpoints, images
        final var AUTH_WHITELIST = new String[]{
                "/api-docs/**", "/webjars/**", "/public/**",
                "/swagger-ui/**", "/doc/**", "/", "/index.html",
                "/assets/**"};

        http
                // Enable CORS
                .cors()
                .and()

                //Disable CSRF
                .csrf()
                .disable()

                // Set session management to stateless
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()

                // Set unauthorized requests exception handler
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            log.error("Unauthorized request - {}", ex.getMessage());
                            response.sendError(SC_UNAUTHORIZED, ex.getMessage());
                        })
                .and()
                // Set permission to allow open db-console
                .authorizeHttpRequests(auth ->
                {
                    try {
                        auth.requestMatchers(antMatcher("/db-console/**"))
                                .permitAll()
                                .and()
                                // This will allow frames with same origin which is much more safe
                                .headers(headers ->
                                        headers.frameOptions()
                                                .sameOrigin()
                                                .disable());
                    } catch (Exception e) {
                        log.error("Exception in headers - {}", e.getMessage());
                    }
                })

                // Enable all whitelisted pages
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(AUTH_WHITELIST)
                                .permitAll())

                // Only Admin Allowed to do the following
                .authorizeHttpRequests(auth ->
                        // Upload files and manage countries
                        auth.requestMatchers("/upload/**", "/countries/**")
                                .hasAuthority(Role.ADMIN).
                                // Create cities
                                requestMatchers(HttpMethod.POST, "/cities")
                                .hasAuthority(Role.ADMIN))

                //Our private endpoints
                .authorizeHttpRequests(auth ->
                        auth.anyRequest()
                                .authenticated())

                // Add JWT token filter
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        /*
          When allowCredentials is true, allowedOrigins cannot contain the special value "*" since
          that cannot be set on the "Access-Control-Allow-Origin" response header.
           
          To allow credentials to a set of origins, list them explicitly or consider
          using "allowedOriginPatterns" instead.
         */
        config.addAllowedOrigin(allowedOrigins);
        
        /*
          When we have a client application say Angular, there is a problem will occur when
          reading headers from the client application which is related to the CORS handshaking:
          
          - If the server does not explicitly allow the client application to read the headers,
             the browser will hide them from the client application.
          
          -- Then the solution is that the server must add in its responses the header
             "Access-Control-Expose-Headers:<header_name>,<header-name2>" in order to let the client
              read them. So we use here config.addExposedHeader() method.
         */
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
