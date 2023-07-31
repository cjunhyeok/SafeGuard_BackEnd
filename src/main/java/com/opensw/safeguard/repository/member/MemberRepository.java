package com.opensw.safeguard.repository.member;

import com.opensw.safeguard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
