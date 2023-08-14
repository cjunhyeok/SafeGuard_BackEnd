package com.opensw.safeguard.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageUploadDTO {
    private String message;
    private ImageUpload imageUpload;
}
