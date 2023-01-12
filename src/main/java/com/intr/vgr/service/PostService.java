package com.intr.vgr.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.intr.vgr.bo.CreatePostBo;
import com.intr.vgr.bo.RequestPostParamsBo;
import com.intr.vgr.model.Post;

public interface PostService {
    public Boolean createPost(CreatePostBo createPostBo) throws IOException;

    public HashMap getPosts(RequestPostParamsBo requestPostParamsBo);
}
