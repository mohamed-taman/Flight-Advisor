package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.request.CreateUserRequest;
import org.siriusxi.htec.fa.domain.dto.response.UserView;
import org.siriusxi.htec.fa.domain.model.Role;
import org.siriusxi.htec.fa.domain.model.User;
import org.siriusxi.htec.fa.infra.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring", imports = Utils.class)
public abstract class UserMapper {
    
    @Autowired
    protected PasswordEncoder passwordEncoder;
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "password",
        expression = "java( passwordEncoder.encode(request.password()) )")
    @Mapping(target = "userUuid", expression = "java( Utils.generateUuid() )")
    public abstract User toUser(CreateUserRequest request);
    
    public abstract UserView toView(User user);
    
    protected String[] map(Set<Role> authorities){
        return authorities
                   .stream()
                   .map(Role::getAuthority)
                   .toArray(String[]::new);
    }
}
