package org.siriusxi.fa.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.fa.api.model.request.ChangePasswordRequest;
import org.siriusxi.fa.api.model.request.CreateUserRequest;
import org.siriusxi.fa.api.model.response.UserResponse;
import org.siriusxi.fa.domain.Role;
import org.siriusxi.fa.domain.User;
import org.siriusxi.fa.infra.UuidUtil;
import org.siriusxi.fa.infra.exception.RefreshTokenException;
import org.siriusxi.fa.infra.mapper.UserMapper;
import org.siriusxi.fa.infra.security.jwt.JwtTokenHelper;
import org.siriusxi.fa.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.lang.String.format;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    /**
     * This method is responsible to create a new user.
     *
     * @param request data to create a new user
     * @return UserView of created user.
     */
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (this.repository.findByUsernameIgnoreCase(request.username()).isPresent())
            throw new ValidationException("Username exists!");

        // Add user
        User user = this.repository.save(this.userMapper.toUser(request));

        // Add user authorities
        user.setAuthorities(Role.CLIENT);

        // Update user to add authorities
        this.repository.save(user);

        System.out.println("Saved User: "+ user);

        // Return user view
        return this.userMapper.toView(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.repository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User with username - %s, not found", username)));
    }


    /**
     * Method to generate the refresh token
     *
     * @param user required to hve a new refresh token
     * @return the updated user with new refresh token
     */
    @Transactional
    public User generateRefreshToken(User user) {
        user.setTokenExpiryDate(now().plus(JwtTokenHelper.refreshTokenExpiration(), DAYS));
        user.setRefreshToken(UuidUtil.newUuid());
        // Update user to add authorities
        return this.repository.save(user);

    }

    /**
     * This method return the user based on a valid refresh token.
     *
     * @param token refresh token to find user.
     * @return an optional user represented by the token.
     */
    public Optional<User> findByRefreshToken(String token) {
        return this.repository.findByRefreshToken(UuidUtil.uuidFrom(token));
    }

    /**
     * Method to verify the user refresh token validly, either it is expired or not.
     * If valid return the same user object, else throw an exception indicating that user token is expired.
     *
     * @param user to validate its token expiration status.
     * @return user that has valid refresh token.
     */
    @Transactional
    public User verifyRefreshTokenExpiration(User user) {
        if (user.getTokenExpiryDate().compareTo(now()) < 0) {
            this.invalidateRefreshTokenById(user.getId());
            throw new RefreshTokenException(user.getRefreshToken().toString(),
                    "Refresh token expired. Please signin!");
        }

        return user;
    }

    /**
     * Method to reset user refresh token and its expiration date.
     *
     * @param userId to reset token values for.
     */
    public void invalidateRefreshTokenById(Integer userId) {
        this.repository.invalidateRefreshTokenById(userId);
    }

    /**
     * Method update user password.
     *
     * @param userId of user need to be updated.
     */
    public void updatePassword(Integer userId, ChangePasswordRequest changePassRequest) {
        if (!changePassRequest.newPassword().equals(changePassRequest.newPasswordAgain()))
            throw new IllegalArgumentException("Passwords doesn't match!");

        var user = this.repository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException(
                        format("User with id - %d, not found", userId)));
        this.userMapper.updatePassword(changePassRequest, user);
        this.repository.save(user);
    }

    public UserMapper mapper() {
        return this.userMapper;
    }
}
