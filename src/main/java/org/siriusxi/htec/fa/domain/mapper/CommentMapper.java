package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.request.UpSrtCommentRequest;
import org.siriusxi.htec.fa.domain.dto.response.CommentView;
import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Comment;
import org.siriusxi.htec.fa.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface CommentMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "city", source = "city")
    Comment toNewModel(UpSrtCommentRequest request, User user, City city);
    
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java( LocalDateTime.now() )")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "id", source = "commentId")
    Comment toUpdateModel(UpSrtCommentRequest request, int commentId, User user, City city);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "city", source = "city.name")
    @Mapping(target = "by", source = "user.fullName")
    CommentView toView(Comment comment);
    
    List<CommentView> toViews(List<Comment> comment);
}