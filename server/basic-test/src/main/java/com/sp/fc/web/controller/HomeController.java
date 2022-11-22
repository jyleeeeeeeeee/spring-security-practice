package com.sp.fc.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "홈페이지";
    }

    @RequestMapping("/auth")
    public Authentication auth() {
        // 사용자가 어떤 권한, 어떤 Authentication으로 접근했는지 확인
        return getAuthentication();
    }

    public Authentication getAuthentication() {
        // 인증 정보 가져오기
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')") // ROLE : USER 만 접근 가능
    @RequestMapping("/user")
    public SecurityMessage user() {
        return SecurityMessage.builder().auth(getAuthentication()).message("User 정보").build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // ROLE : ADMIN 만 접근 가능
    @RequestMapping("/admin")
    public SecurityMessage admin() {
        return SecurityMessage.builder().auth(getAuthentication()).message("관리자 정보").build();
    }
}
