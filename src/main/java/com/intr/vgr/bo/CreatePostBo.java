package com.intr.vgr.bo;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostBo {
    private String postTitle;
    private String postDescription;
    private MultipartFile postImage;
    private String user;
    private String category;
}
