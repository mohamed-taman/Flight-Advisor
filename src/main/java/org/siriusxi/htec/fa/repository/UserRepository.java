package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
