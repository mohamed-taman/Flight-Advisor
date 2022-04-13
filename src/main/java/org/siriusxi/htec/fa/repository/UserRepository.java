package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.User;
import org.siriusxi.htec.fa.infra.exception.NotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
    
    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);
    
    @Caching(evict = {
        @CacheEvict(key = "#p0.id"),
        @CacheEvict(key = "#p0.username")
    })
    <S extends User> S save(S entity);
    
    @Cacheable
    Optional<User> findById(Integer id);
    
    @Cacheable
    default User getById(Integer id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new NotFoundException(User.class, id);
        }
        return optionalUser.get();
    }
    
    @Cacheable
    Optional<User> findByUsernameIgnoreCase(String username);
    
}
