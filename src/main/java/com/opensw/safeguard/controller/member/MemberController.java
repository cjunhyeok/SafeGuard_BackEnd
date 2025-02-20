package com.opensw.safeguard.controller.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.*;
import com.opensw.safeguard.domain.image.ImageUpload;
import com.opensw.safeguard.email.AuthCode;
import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.security.service.MemberAdapter;
import com.opensw.safeguard.security.token.TokenInfo;
import com.opensw.safeguard.service.image.ImageService;
import com.opensw.safeguard.service.member.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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
    private final EmailService emailService;
    private final ImageService imageService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public TokenInfo login(@RequestBody @Valid MemberLoginDTO memberLoginDTO){

        TokenInfo tokenInfo = memberService.login(memberLoginDTO.getUsername(),memberLoginDTO.getPassword());

        return tokenInfo;
    }

    /**
     * ID 회원가입
     */
    @PostMapping("/join")
    public Member join(@RequestBody @Valid MemberJoinDTO memberJoinDTO){

        return memberService.join(
                memberJoinDTO.getUsername(), memberJoinDTO.getPassword(), memberJoinDTO.getEmail(),
                memberJoinDTO.getRealName(),memberJoinDTO.getPhoneNumber(),memberJoinDTO.getAssociatedPhoneNumber()
        );


    }

    /**
     * 이미지 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
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

    /**
     * 이미지 불러오기
     */
    @GetMapping("/upload")
    public ResponseEntity<?> getImage(@AuthenticationPrincipal MemberAdapter memberAdapter) throws IOException {
                Member byUsername = memberService.findByUsername(memberAdapter.getUsername());
                ImageUpload image = imageService.findImage(byUsername.getId());

                String path = new File("").getAbsolutePath() + "/" + image.getStoredFileName();

                FileSystemResource resource = new FileSystemResource(path);
                HttpHeaders header = new HttpHeaders();
                Path filePath = Paths.get(path);
                header.add("Content-Type", Files.probeContentType(filePath));
                return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }


    /**
     * 이메일인증
     */

    @PostMapping("/join/emailConfirm")
    public AuthCode emailConfirm(@RequestBody @Valid EmailConfirmDTO emailConfirmDTO) throws MessagingException, UnsupportedEncodingException {

        return emailService.sendEmail(emailConfirmDTO.getEmail(),"confirm");

    }

    /**
     * ID 중복검사
     */

    @PostMapping("/join/duplicate")
    public DuplicateUsername duplicate(@RequestBody @Valid DuplicateUsername duplicateUsername){

        return memberService.duplicateCheckUsername(duplicateUsername.getUsername());
    }
    @PostMapping("/test")
    public void test(@AuthenticationPrincipal MemberAdapter memberContext){
        log.info(memberContext.toString());
    }


    /**
     *
     * 아이디 찾기 1단계
     * 해당 이메일 ,실명  존재한다면 메일인증 실행
     */

    @PostMapping("/findByUsername/email")
    public AuthCode findByUsernameEmail(@RequestBody FindByUsernameRequest request) throws MessagingException, UnsupportedEncodingException {
        if (memberService.existByRealNameAndEmail(request.getRealName(),request.getEmail()))
        {
            return emailService.sendEmail(request.getEmail(),"find");
        }
        else
        {
            return AuthCode.builder().build();
        }
    }

    /**
     *
     * 아이디 찾기 2단계
     * 1단계 성공시 해당 실명 , 이메일에 해당하는 아이디를 반환
     */


    @GetMapping("/findByUsername/get")
    public FindByUsernameResponse findByUsername(@ModelAttribute FindByUsernameRequest request){
        return FindByUsernameResponse.builder()
                .username(
                        memberService.findByRealName(request.getRealName()).getUsername()
                )
                .exitsMember(true)
                .build();
    }
}
