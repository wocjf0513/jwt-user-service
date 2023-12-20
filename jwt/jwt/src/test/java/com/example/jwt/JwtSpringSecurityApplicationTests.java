package com.example.jwt;

import com.example.jwt.domain.member.authority.Authority;
import com.example.jwt.domain.member.entity.Member;
import com.example.jwt.domain.member.repository.MemberRepository;
import com.example.jwt.global.config.CustomUserDetailsService;
import com.example.jwt.global.jwt.dto.RefreshRequest;
import com.example.jwt.global.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = JwtApplication.class)
@AutoConfigureMockMvc
class JwtSpringSecurityApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String email = "test@email.com";


    @BeforeEach
    void init() {
        memberRepository.save(Member.builder().email(email).password("1234").authority(Authority.ROLE_USER).build());
    }


    @Test
    void publicEndpointShouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/public"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Public endpoint accessible without authentication"));
    }

    @Test
    void privateEndpointShouldRequireAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/private"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void privateEndpointShouldBeAccessibleWithValidToken() throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/private")
                .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Private endpoint accessible with authentication"));
    }

    @Test
    void refreshTokenEndpointShouldRefreshAccessToken() throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            String accessToken = jwtUtil.generateToken(userDetails);
        RefreshRequest refreshRequest = RefreshRequest.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/token/refresh")
                .content(objectMapper.writeValueAsBytes(refreshRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.header().exists("Authorization"))
            .andExpect(MockMvcResultMatchers.header().exists("Refresh-Token"))
            .andExpect(MockMvcResultMatchers.content().string("Token refreshed successfully"));
    }
}
