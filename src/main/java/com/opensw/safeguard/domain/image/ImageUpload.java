package com.opensw.safeguard.domain.image;

import com.opensw.safeguard.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Entity
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
@AllArgsConstructor
public class ImageUpload {
    @Id
    @GeneratedValue
    @Column(name = "image_upload_id")
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;
    @Column
    private long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
