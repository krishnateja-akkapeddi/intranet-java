package com.intr.vgr.controller;

import com.intr.vgr.bo.CreatePostBo;
import com.intr.vgr.bo.RequestPostParamsBo;
import com.intr.vgr.ga_responses.GaResponse;
import com.intr.vgr.service.PostService;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/publish")
    public ResponseEntity createPost(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postDescription") String postDescription,
            @RequestParam("postImage") MultipartFile postImage,
            @RequestAttribute("unauthorized") boolean unauthorized,
            @RequestAttribute("user") String userEmail) throws IOException {
        System.out.println("UNAUTH_" + unauthorized);
        if (unauthorized) {
            return new GaResponse().unautorizedResponse();
        } else {
            CreatePostBo createPostBo = new CreatePostBo();
            createPostBo.setPostDescription(postDescription).setPostImage(postImage)
                    .setUser(userEmail).setPostTitle(postTitle);
            System.out.println(createPostBo);
            System.out.println("EMAIL_FROM_CONTEXT_" + userEmail);
            try {
                postService.createPost(createPostBo);
                return new GaResponse().successResponse("Post creation successful");
            } catch (Exception e) {
                System.out.println("ERROR_FROM_CONTROLLER__" + e);
                return new GaResponse().customResponse("Post created failed", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllPosts(@RequestParam("category") Optional<Integer> category,
            @RequestParam("elementsLength") Optional<Integer> elementsLength,
            @RequestParam("page") Optional<Integer> page,
            @RequestAttribute("unauthorized") boolean unauthorized,
            @RequestAttribute("user") String userEmail) {
        if (unauthorized) {
            return new GaResponse().unautorizedResponse();
        } else {
            RequestPostParamsBo requestPostParamsBo = new RequestPostParamsBo();
            requestPostParamsBo.setElementsLength(elementsLength).setCategory(category).setPage(page);
            var postResponse = postService.getPosts(requestPostParamsBo);
            return new ResponseEntity(postResponse, HttpStatus.OK);
        }

    }

}
