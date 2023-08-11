package com.opensw.safeguard.service.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
}
