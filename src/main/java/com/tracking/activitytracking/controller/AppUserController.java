package com.tracking.activitytracking.controller;

import com.tracking.activitytracking.dto.request.LoginRequestDto;
import com.tracking.activitytracking.dto.response.AppUserResponseDto;
import com.tracking.activitytracking.repository.AppUserRepository;
import com.tracking.activitytracking.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.tracking.activitytracking.dto.request.AppUserRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/path")

public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid AppUserRequestDto request) {

        var response = appUserService.createUser(request);
        // The same as above
//        UserRegistrationResponse response1 = userService.registerUser(request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto userLoginRequest, HttpServletRequest request){
        var loginResponse = appUserService.loginUser(userLoginRequest, request);
        return ResponseEntity.ok(loginResponse);
    }


}
