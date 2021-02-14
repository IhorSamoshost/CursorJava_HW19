package com.cursor.library.controllers;

import com.cursor.library.services.JwtUtil;
import com.cursor.library.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest auth) throws AccessDeniedException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getPassword()));
        var userDetails = userService.login(auth.getUserName(), auth.getPassword());
        String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping(value = "/dummy")
    public ResponseEntity<String> createAuthenticationToken() {
        return ResponseEntity.ok("user");
    }

    @Data
    public static class AuthenticationRequest {
        private String userName;
        private String password;
    }


}

