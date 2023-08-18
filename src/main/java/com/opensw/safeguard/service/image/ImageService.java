package com.opensw.safeguard.service.image;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.image.ImageUpload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    public void addImage(ImageUpload imageUpload , List<MultipartFile> multipartFile, Member member) throws Exception;


    public ImageUpload findImage(Long id);
}
