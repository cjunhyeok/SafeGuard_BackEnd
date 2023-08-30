package com.opensw.safeguard.service.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void findAllTest() {
        // given
        List<Member> mockMembers = new ArrayList<>();
        mockMembers.add(Member.builder().build());

        // stub
        when(memberRepository.findAll()).thenReturn(mockMembers);

        // when
        List<Member> findMembers = memberService.findAll();

        // then
        verify(memberRepository, times(1)).findAll();
        assertThat(findMembers).isNotEmpty();
    }

    @Test
    void findByUsernameTest() {
        // given
        String username = "id1";

        // stub
        when(memberRepository.findByUsername(username)).thenReturn(Optional.of(Member.builder().build()));

        // when
        Member findMember = memberService.findByUsername(username);

        // then
        verify(memberRepository, times(1)).findByUsername(username);
        assertThat(findMember).isNotNull();
    }

    @Test
    void findByUsernameFailTest() {
        // given
        String username = "id1";

        // stub
        when(memberRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> memberService.findByUsername(username));
    }

}
