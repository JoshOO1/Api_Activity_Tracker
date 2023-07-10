package com.tracking.activitytracking.service;

import com.tracking.activitytracking.dto.request.LoginRequestDto;
import com.tracking.activitytracking.dto.response.AppUserResponseDto;
import com.tracking.activitytracking.dto.request.AppUserRequestDto;
import com.tracking.activitytracking.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AppUserService {
//
    AppUserResponseDto createUser( AppUserRequestDto request);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto, HttpServletRequest request);
}
