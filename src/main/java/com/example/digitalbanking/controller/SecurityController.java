package com.example.digitalbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;


    @GetMapping("/profile")
    public Authentication getAuthentication(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/login")
    public Map<String,String> login( String username,  String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Instant instant = Instant.now();

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plusSeconds(600))
                .subject(username)
                .claim("scope", authorities)
                .build();

        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(),jwtClaimsSet);

        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        return Map.of("token", jwt);




    }
}
