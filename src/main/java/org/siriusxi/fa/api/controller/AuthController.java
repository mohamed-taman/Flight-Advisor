package org.siriusxi.fa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.fa.api.model.request.AuthRequest;
import org.siriusxi.fa.api.model.request.ChangePasswordRequest;
import org.siriusxi.fa.api.model.request.CreateUserRequest;
import org.siriusxi.fa.api.model.request.TokenRefreshRequest;
import org.siriusxi.fa.api.model.response.TokenRefreshResponse;
import org.siriusxi.fa.api.model.response.UserResponse;
import org.siriusxi.fa.domain.User;
import org.siriusxi.fa.infra.exception.NotAllowedException;
import org.siriusxi.fa.infra.exception.RefreshTokenException;
import org.siriusxi.fa.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.siriusxi.fa.infra.security.jwt.JwtTokenHelper.generateJwtToken;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Authentication controller used to handle users authentication.
 *
 * @author Mohamed Taman
 * @version 1.0
 */

@Log4j2
@Tag(name = "Authentication",
        description = "A set of public APIs, for managing user authentication, and the registration.")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Operation(description = """
            An API call to authenticate user before using the system,
            and if successful a valid token is returned.
            """)
    @PostMapping(value = "signin")
    public ResponseEntity<UserResponse> authenticate(@RequestBody @Valid AuthRequest request) {
        try {
            var authenticate = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()));

            User user = (User) authenticate.getPrincipal();

            // Generate refresh token
            user = this.userService.generateRefreshToken(user);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION,
                            generateJwtToken(
                                    user.getId(),
                                    user.getUsername()))
                    .body(this.userService.mapper().toView(user));
        } catch (BadCredentialsException ex) {
            throw new HttpClientErrorException(UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase());
        }
    }

    @Operation(description = """
            An API call, to register the user,
            to be able to authenticate and use the system.
            """)
    @PostMapping(value = "signup")
    public UserResponse register(@RequestBody @Valid CreateUserRequest userRequest) {

        log.debug("User to be created: {}", userRequest);
        return this.userService.create(userRequest);
    }


    /**
     * <pre>
     *   +--------+                                           +---------------+
     *   |        |--(A)------- Authorization Grant --------->|               |
     *   |        |                                           |               |
     *   |        |<-(B)----------- Access Token -------------|               |
     *   |        |               & Refresh Token             |               |
     *   |        |                                           |               |
     *   |        |                            +----------+   |               |
     *   |        |--(C)---- Access Token ---->|          |   |               |
     *   |        |                            |          |   |               |
     *   |        |<-(D)- Protected Resource --| Resource |   | Authorization |
     *   | Client |                            |  Server  |   |     Server    |
     *   |        |--(E)---- Access Token ---->|          |   |               |
     *   |        |                            |          |   |               |
     *   |        |<-(F)- Invalid Token Error -|          |   |               |
     *   |        |                            +----------+   |               |
     *   |        |                                           |               |
     *   |        |--(G)----------- Refresh Token ----------->|               |
     *   |        |                                           |               |
     *   |        |<-(H)----------- Access Token -------------|               |
     *   +--------+           & Optional Refresh Token        +---------------+
     * </pre>
     * <ul>
     *   <li>(A)  The client requests an access token by authenticating with the
     *        authorization server and presenting an authorization grant.</br></li>
     *
     *   <li>(B)  The authorization server authenticates the client and validates
     *        the authorization grant, and if valid, issues an access token
     *        and a refresh token.</br></li>
     *
     *   <li>(C)  The client makes a protected resource request to the resource
     *        server by presenting the access token.</br></li>
     *
     *   <li>(D)  The resource server validates the access token, and if valid,
     *        serves the request.</br></li>
     *
     *   <li>(E)  Steps (C) and (D) repeat until the access token expires.  If the
     *        client knows the access token expired, it skips to step (G);
     *        otherwise, it makes another protected resource request.</br></li>
     *
     *   <li>(F)  Since the access token is invalid, the resource server returns
     *        an invalid token error.</br></li>
     *
     *   <li>(G)  The client requests a new access token by authenticating with
     *        the authorization server and presenting the refresh token.  The
     *        client authentication requirements are based on the client type
     *        and on the authorization server policies.</br></li>
     *
     *   <li>(H)  The authorization server authenticates the client and validates
     *        the refresh token, and if valid, issues a new access token (and,
     *        optionally, a new refresh token).</br></li>
     * </ul>
     *
     * @param tokenRefreshRequest the request for generating a new token, and refresh token.
     * @return the new access token, and a new refresh token if not expired.
     */
    @Operation(description = """
            An API call, to refresh user JWT token without signing again,
            to be able to continue be authenticated and use the system.
            """)
    @PostMapping(value = "refresh_token")
    public TokenRefreshResponse refreshToken(@RequestBody @Valid TokenRefreshRequest tokenRefreshRequest) {
        log.debug("Token to be Refreshed: {}", tokenRefreshRequest);

        return this.userService
                .findByRefreshToken(tokenRefreshRequest.refreshToken())
                .map(this.userService::verifyRefreshTokenExpiration)
                .map(this.userService::generateRefreshToken)
                .map(user ->
                        new TokenRefreshResponse(generateJwtToken(
                                user.getId(),
                                user.getUsername()), user.getRefreshToken().toString())
                )
                .orElseThrow(() -> new RefreshTokenException(tokenRefreshRequest.refreshToken(), "Invalid refresh token, can't generate a new one!"));
    }

    @Operation(description = """
            An API call, to logout the user,
            But has to re-authenticate again to access the system.
            """,
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/signout")
    public ResponseEntity<String> logoutUser() {
        getUserFromSecurityContext()
                .ifPresentOrElse(user -> {
                    this.userService.invalidateRefreshTokenById(user.getId());
                    SecurityContextHolder.getContext().setAuthentication(null);
                }, () -> {
                    throw new NotAllowedException("Can't logout, please signin!");
                });

        return ResponseEntity.ok("Logged out successful!");
    }

    @Operation(description = """
            An API call, to change user password,
            But user has to re-authenticate again to access the system with new password.
            """,
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest changePassRequest) {
        getUserFromSecurityContext()
                .ifPresentOrElse(user -> {
                    this.userService.updatePassword(user.getId(), changePassRequest);
                    SecurityContextHolder.getContext().setAuthentication(null);
                }, () -> {
                    throw new NotAllowedException("Can't change password, please signin!");
                });

        return ResponseEntity.ok("Password changed successful!");
    }

    private Optional<User> getUserFromSecurityContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof User user ? Optional.of(user) : Optional.empty();
    }

}
