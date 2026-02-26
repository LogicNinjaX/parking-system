package com.app.parking.controller;


import com.app.parking.controller.doc.AuthApiDoc;
import com.app.parking.dto.request.LoginRequest;
import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.ApiResponse;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.entity.User;
import com.app.parking.service.UserService;
import com.app.parking.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDoc {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(path = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request){
        var response = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "user registered successfully", response));
    }


    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails, user.getUserId(), user.getRole().name());

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "login successful"
                , Collections.singletonMap("token", token)
        ));
    }
}
