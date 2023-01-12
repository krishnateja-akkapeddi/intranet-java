package com.intr.vgr.utility;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "djpvhju0x",
            "api_key", "848815865454857",
            "api_secret", "MLXd04rtEw8nLj-rNOLNwOVkLnY"));

    public String uploadImage(MultipartFile file) throws IOException {
        Map img = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return img.get("url").toString();
    }

    public String uploadImage(String file) throws IOException {
        Map img = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        return img.get("url").toString();
    }
}
