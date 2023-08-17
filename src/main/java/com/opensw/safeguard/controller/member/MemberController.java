package com.opensw.safeguard.controller.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.QMember;
import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.domain.dto.EmailConfirmDTO;
import com.opensw.safeguard.domain.dto.MemberJoinDTO;
import com.opensw.safeguard.domain.dto.MemberLoginDTO;
import com.opensw.safeguard.domain.image.ImageUpload;
import com.opensw.safeguard.domain.image.ImageUploadDTO;
import com.opensw.safeguard.email.AuthCode;
import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.security.service.MemberAdapter;
import com.opensw.safeguard.security.token.TokenInfo;
import com.opensw.safeguard.service.image.ImageService;
import com.opensw.safeguard.service.image.ImageServiceImpl;
import com.opensw.safeguard.service.member.MemberService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.SizeLimitExceededException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.opensw.safeguard.domain.QMember.member;


@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService mailService;

    private final ImageService imageService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginDTO memberLoginDTO){

        TokenInfo tokenInfo = memberService.login(memberLoginDTO.getUsername(),memberLoginDTO.getPassword());

        return tokenInfo;
    }
    @PostMapping("/join")
    public Member join(@RequestBody MemberJoinDTO memberJoinDTO){

        return memberService.join(
                memberJoinDTO.getUsername(), memberJoinDTO.getPassword(), memberJoinDTO.getEmail(),
                memberJoinDTO.getRealName(),memberJoinDTO.getPhoneNumber(),memberJoinDTO.getAssociatedPhoneNumber()
        );


    }

    @PostMapping("/upload")
    public ResponseEntity<?> createImage(
             @RequestParam("files") List<MultipartFile> files,@AuthenticationPrincipal MemberAdapter memberAdapter) throws Exception {

            Member member = memberService.findByUsername(memberAdapter.getUsername());
            imageService.addImage(
                    ImageUpload
                            .builder()
                            .build(), files, member);
            ImageUpload image = imageService.findImage(member.getId());
            String storedFileName = image.getStoredFileName();

            String path = new File("").getAbsolutePath() + "/" + storedFileName;
            return ResponseEntity.ok().body(ImageUploadDTO.builder().message("ok").build());


    }
    @GetMapping("/upload")
    public ResponseEntity<?> getImage(@AuthenticationPrincipal MemberAdapter memberAdapter) throws IOException {
                String username = memberAdapter.getUsername();
                Member byUsername = memberService.findByUsername(username);
                ImageUpload image = imageService.findImage(byUsername.getId());


                String path = new File("").getAbsolutePath() + "/" + image.getStoredFileName();
                FileSystemResource resource = new FileSystemResource(path);
                HttpHeaders header = new HttpHeaders();
                Path filePath = null;
                filePath = Paths.get(path);
                header.add("Content-Type", Files.probeContentType(filePath));
                return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @PostMapping("/join/emailConfirm")
    public AuthCode emailConfirm(@RequestBody EmailConfirmDTO emailConfirmDTO) throws MessagingException, UnsupportedEncodingException {

        return mailService.sendEmail(emailConfirmDTO.getEmail());

    }

    @PostMapping("/join/duplicate")
    public DuplicateUsername duplicate(@RequestBody DuplicateUsername duplicateUsername){
        return memberService.duplicateCheckUsername(duplicateUsername.getUsername());

    }
    @PostMapping("/test")
    public void test(@AuthenticationPrincipal MemberAdapter memberContext){
        log.info(memberContext.toString());
    }
}
