package com.example.jwt.global.config;

import com.example.jwt.domain.member.entity.Member;
import com.example.jwt.domain.member.repository.MemberRepository;
import com.example.jwt.domain.member.util.MemberMapper;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
            .map(MemberMapper::toUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException(username + "을 DB에서 찾을 수 없습니다."));
    }
}