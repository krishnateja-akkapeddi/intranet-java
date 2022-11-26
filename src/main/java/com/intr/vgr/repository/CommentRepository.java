package com.intr.vgr.repository;

import com.intr.vgr.model.Comment;
import com.intr.vgr.model.Post;
import com.intr.vgr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
