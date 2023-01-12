package com.intr.vgr.repository;

import com.intr.vgr.model.Category;
import com.intr.vgr.model.Post;
import com.intr.vgr.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    Page<Post> findByCategory(Category category, PageRequest pageable);

}
