package com.opensw.safeguard.repository.image;

import com.opensw.safeguard.domain.image.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ImageRepository extends JpaRepository<ImageUpload,Long> {
    List<ImageUpload> findByMemberIdOrderByIdDesc(Long memberId);
}
