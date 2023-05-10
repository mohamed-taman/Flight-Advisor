package org.siriusxi.fa.repository;

import lombok.NonNull;
import org.siriusxi.fa.domain.User;
import org.siriusxi.fa.infra.exception.NotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends CrudRepository<User, Integer> {

    @Modifying
    @Query("""
            UPDATE User user
            SET user.refreshToken = null,
                user.tokenExpiryDate = null
            WHERE user.id = :userId
            """)
    void invalidateRefreshTokenById(Integer userId);

    @Override
    @CacheEvict(allEntries = true)
    <S extends User> @NonNull List<S> saveAll(@NonNull Iterable<S> entities);
    
    @Override
    @Caching(evict = {
        @CacheEvict(key = "#p0.id"),
        @CacheEvict(key = "#p0.username")
    })
    <S extends User> @NonNull S save(@NonNull S entity);
    
    @Override
    @Cacheable
    @NonNull
    Optional<User> findById(@NonNull Integer id);
    
    @Cacheable
    default User getById(Integer id) {
        return findById(id)
                .filter(User::isEnabled)
                .orElseThrow(() -> new NotFoundException(User.class, id));

    }
    
    @Cacheable
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByRefreshToken(UUID token);

    
}
