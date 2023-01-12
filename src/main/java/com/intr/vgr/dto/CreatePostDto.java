
package com.intr.vgr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CreatePostDto {
    private String postName;
    private String description;
    private MultipartFile img;

}
