package org.siriusxi.htec.fa.service;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.response.UserView;
import org.siriusxi.htec.fa.domain.dto.request.CreateUserRequest;
import org.siriusxi.htec.fa.domain.mapper.UserMapper;
import org.siriusxi.htec.fa.domain.model.User;
import org.siriusxi.htec.fa.infra.Utils;
import org.siriusxi.htec.fa.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import static java.lang.String.format;
import static org.siriusxi.htec.fa.domain.model.Role.CLIENT;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    
    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }
    
    /**
     * This method is responsible to create a new user.
     *
     * @param request data to create a new user
     * @return UserView of created user.
     */
    @Transactional
    public UserView create(CreateUserRequest request) {
        
        if (repository.findByUsername(request.username()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserUuid(Utils.generateUuid());
        // Add user
        user = repository.save(user);
        // Add user authorities
        user.setAuthorities(CLIENT);
        // Update user to add authorities
        repository.save(user);
        
        // Return user view
        return userMapper.toUserView(user);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                       .findByUsername(username)
                       .orElseThrow(
                               () -> new UsernameNotFoundException(
                                       format("User with username - %s, not found", username)));
    }
}
