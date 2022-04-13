package org.siriusxi.htec.fa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.api.model.request.AuthRequest;
import org.siriusxi.htec.fa.api.model.request.CreateUserRequest;
import org.siriusxi.htec.fa.api.model.response.UserView;
import org.siriusxi.htec.fa.domain.User;
import org.siriusxi.htec.fa.infra.mapper.UserMapper;
import org.siriusxi.htec.fa.infra.security.jwt.JwtTokenHelper;
import org.siriusxi.htec.fa.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Authentication controller used to handle users authentication.
 *
 * @author Mohamed Taman
 * @version 1.0
 */

// TODO: add refresh token method, change password.
@Log4j2
@Tag(name = "Authentication",
    description = "A set of public APIs, for managing user authentication, and the registration.")
@RestController
@RequestMapping("public")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }
    
    @Operation(description = """
        An API call to authenticate user before using the system,
        and if successful a valid token is returned.
        """)
    @PostMapping(value = "login")
    public ResponseEntity<UserView> authenticate(@RequestBody @Valid AuthRequest request) {
        try {
            var authenticate = authenticationManager
                                   .authenticate(new UsernamePasswordAuthenticationToken(
                                       request.username(),
                                       request.password()));
            
            User user = (User) authenticate.getPrincipal();
            
            return ResponseEntity.ok()
                       .header(HttpHeaders.AUTHORIZATION,
                           JwtTokenHelper.generateAccessToken(
                               user.getId(),
                               user.getUsername()))
                       .body(userMapper.toView(user));
        } catch (BadCredentialsException ex) {
            throw new HttpClientErrorException(UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase());
        }
    }
    
    @Operation(description = """
        An API call, to register the user,
        to be able to authenticate and use the system.
        """)
    @PostMapping(value = "register")
    public UserView register(@RequestBody @Valid CreateUserRequest userRequest) {
        
        log.debug("User to be created: {}", userRequest);
        return userService.create(userRequest);
    }
    
}
