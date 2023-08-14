package com.opensw.safeguard.service.image;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.image.ImageUpload;
import com.opensw.safeguard.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageHandler imageHandler;
    private final ImageRepository imageRepository;
    @Override
    public void addImage(ImageUpload imageUpload, List<MultipartFile> multipartFile, Member member) throws Exception
         {
            List<ImageUpload> list = imageHandler.parseFileInfo(imageUpload.getId(), multipartFile);

            if (list.isEmpty()){
                // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
            }
            // 파일에 대해 DB에 저장하고 가지고 있을 것
            else{
                List<ImageUpload> pictureBeans = new ArrayList<>();
                for (ImageUpload images : list) {
                    images.setMember(member);
                    pictureBeans.add(imageRepository.save(images));
                }
            }

//            return imageRepository.save(imageUpload);
    }

    @Override
    public ImageUpload findImage(Long id) {
        return imageRepository.findByMemberIdOrderByIdDesc(id).stream().findFirst().orElseThrow(
                ()->new IllegalArgumentException("Image Not exist")
        );
    }
}
