package com.opensw.safeguard.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender emailSender;
    private String authNum;

    @Value("${email}")
    private String setFrom;


    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();

        String toEmail = email; //받는 사람
        String title = "Safe-Guard 회원가입 인증 번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(new InternetAddress(setFrom,"Safe-Guard")); //보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }
    public AuthCode sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);

        AuthCode authcode = AuthCode.builder().authCode(authNum).build();

        return authcode; //인증 코드 반환
    }


    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }



}
