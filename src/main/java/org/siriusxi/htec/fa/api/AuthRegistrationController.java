package org.siriusxi.htec.fa.api;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.UserView;
import org.siriusxi.htec.fa.domain.dto.request.AuthRequest;
import org.siriusxi.htec.fa.domain.dto.request.CreateUserRequest;
import org.siriusxi.htec.fa.domain.mapper.UserMapper;
import org.siriusxi.htec.fa.domain.model.User;
import org.siriusxi.htec.fa.infra.security.jwt.JwtTokenHelper;
import org.siriusxi.htec.fa.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * Authentication controller used to handle users authentication.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@RestController
@RequestMapping("public")
@Log4j2
public class AuthRegistrationController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    
    public AuthRegistrationController(AuthenticationManager authenticationManager,
                                      UserService userService,
                                      UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }
    
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
                                           user.getId().longValue(),
                                           user.getUsername()))
                           .body(userMapper.toUserView(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping(value = "register")
    public UserView register(@RequestBody @Valid CreateUserRequest userRequest) {
        
        if (!userRequest.password().equals(userRequest.rePassword())) {
            throw new ValidationException("Passwords doesn't match!");
        }
        log.debug("User information to be created: {}", userRequest);
        return userService.create(userRequest);
    }
    
}
