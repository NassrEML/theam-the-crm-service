package com.nassreml.crm.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/jwts")
public class JwtResource {

    private JwtService jwtService;

    @Autowired
    public JwtResource(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = "/token")
    public String login(@AuthenticationPrincipal User activeUser) {
        List<String> roleList = activeUser.getAuthorities().stream().map
                (authority -> authority.getAuthority()).collect(Collectors.toList());
        return jwtService.createToken(activeUser.getUsername(), roleList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String verify() {
        return "OK";
    }
}
