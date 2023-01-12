package com.intr.vgr.service.implementation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.intr.vgr.bo.CreatePostBo;
import com.intr.vgr.bo.RequestPostParamsBo;
import com.intr.vgr.model.Category;
import com.intr.vgr.model.Post;
import com.intr.vgr.model.Role;
import com.intr.vgr.model.User;
import com.intr.vgr.repository.CategoryRepository;
import com.intr.vgr.repository.PostRepository;
import com.intr.vgr.repository.UserRepository;
import com.intr.vgr.service.PostService;
import com.intr.vgr.utility.ImageUploadService;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageUploadService imageUploadService;

    @Override
    public Boolean createPost(CreatePostBo createPostBo) throws IOException {
        try {
            User user = userRepository.getUserByEmail(createPostBo.getUser());
            Post post = new Post();
            post.setUser(user);
            Role role = user.getRole();
            Category category = categoryRepository.getById(role.getRoleId());
            post.setCategory(category);
            post.setPostName(createPostBo.getPostTitle());
            post.setDescription(createPostBo.getPostDescription());
            var imageUrl = imageUploadService.uploadImage(createPostBo.getPostImage());
            post.setUrl(imageUrl);
            postRepository.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public HashMap<String, Object> getPosts(RequestPostParamsBo requestPostParamsBo) {
        PageRequest pageable = PageRequest.of(requestPostParamsBo.getPage().get(),
                requestPostParamsBo.getElementsLength().get());

        long catId = requestPostParamsBo.getCategory().get();

        Optional<Category> category = catId == 0 ? categoryRepository.findById(1L) : categoryRepository.findById(catId);

        var posts = catId == 0 ? postRepository.findAll(pageable)
                : postRepository.findByCategory(category.get(), pageable);
        HashMap<String, Object> paginationDetails = new HashMap<String, Object>();
        paginationDetails.put("elementsPerPage", posts.getPageable().getPageSize());
        paginationDetails.put("totalPages", posts.getTotalPages());
        paginationDetails.put("currentPage", posts.getPageable().getPageNumber());
        paginationDetails.put("totalElements", posts.getTotalElements());

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("paginationDetails", paginationDetails);
        result.put("success", true);
        result.put("body", posts.getContent());
        return result;
    }

}
