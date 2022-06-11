package com.cyj.dto;

import com.cyj.pojo.Adopt;
import org.springframework.web.multipart.MultipartFile;

public class AdoptAddDto {
    Adopt adopt;
    MultipartFile[] files;
}
