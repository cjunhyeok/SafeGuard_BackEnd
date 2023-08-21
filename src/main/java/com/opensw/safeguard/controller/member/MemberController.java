package com.opensw.safeguard.controller.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.domain.dto.EmailConfirmDTO;
import com.opensw.safeguard.domain.dto.MemberJoinDTO;
import com.opensw.safeguard.domain.dto.MemberLoginDTO;
import com.opensw.safeguard.domain.image.ImageUpload;
import com.opensw.safeguard.email.AuthCode;
import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.security.service.MemberAdapter;
import com.opensw.safeguard.security.token.TokenInfo;
import com.opensw.safeguard.service.image.ImageService;
import com.opensw.safeguard.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Tag(name = "member",description = "멤버 API")
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService mailService;

    private final ImageService imageService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody @Parameter(description = "id,pw",required = true)  MemberLoginDTO memberLoginDTO){

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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","OK");
            log.info("json = {}",jsonObject);
            return ResponseEntity.ok().body(jsonObject.toMap());


    }
    @GetMapping("/upload")
    public ResponseEntity<?> getImage(@AuthenticationPrincipal MemberAdapter memberAdapter) throws IOException {
                String username = memberAdapter.getUsername();
                Member byUsername = memberService.findByUsername(username);
                ImageUpload image = imageService.findImage(byUsername.getId());


                String path = new File("").getAbsolutePath() + "/" + image.getStoredFileName();
                FileSystemResource resource = new FileSystemResource(path);
                HttpHeaders header = new HttpHeaders();
                Path filePath = Paths.get(path);
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
