package com.example.jwt.global.jwt.contoller;

import com.example.jwt.global.config.CustomUserDetailsService;
import com.example.jwt.global.jwt.dto.RefreshRequest;
import com.example.jwt.global.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TokenController {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public TokenController(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Public endpoint accessible without authentication");
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("Private endpoint accessible with authentication");
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {
        String username = jwtUtil.extractUsername(refreshRequest.getAccessToken());
        if (jwtUtil.isRefreshTokenValid(username, refreshRequest.getRefreshToken())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);

            // Return the new tokens
            return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newToken)
                .header("Refresh-Token", newRefreshToken)
                .body("Token refreshed successfully");
        } else {
            // Invalid refresh token
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
}