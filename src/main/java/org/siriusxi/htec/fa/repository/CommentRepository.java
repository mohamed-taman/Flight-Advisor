package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Comment;
import org.siriusxi.htec.fa.domain.model.CommentPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, CommentPK> {

}
