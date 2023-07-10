package com.tracking.activitytracking.service.implementation;

import com.tracking.activitytracking.dto.request.AppUserRequestDto;
import com.tracking.activitytracking.dto.request.LoginRequestDto;
import com.tracking.activitytracking.dto.response.AppUserResponseDto;
import com.tracking.activitytracking.dto.response.LoginResponseDto;
import com.tracking.activitytracking.exceptions.ResourceNotFoundException;
import com.tracking.activitytracking.model.AppUser;
import com.tracking.activitytracking.repository.AppUserRepository;
import com.tracking.activitytracking.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUserResponseDto createUser(@Valid AppUserRequestDto request) {
        Optional<AppUser> appUser = appUserRepository.findByEmail(request.getEmail());

        if (appUser.isPresent()) {
            throw new ResourceNotFoundException("User already has an account");
        }


        //Using Builder
        AppUser newappUser = AppUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        AppUser saveappUser = appUserRepository.save(newappUser);

        return AppUserResponseDto.builder()
                .username(saveappUser.getUsername())
                .email(saveappUser.getEmail())
                .build();

    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(loginRequestDto.getEmail());
        if(optionalAppUser.isEmpty()){
            throw new ResourceNotFoundException("invalid Email and Password");
        }
        AppUser appUser = optionalAppUser.get();
        if (!loginRequestDto.getPassword().equals(optionalAppUser.get().getPassword())){
            throw new ResourceNotFoundException("Invalid email and password");
        }
        HttpSession session = request.getSession();
        session.setAttribute("appUser", appUser);

        return LoginResponseDto.builder().email(appUser.getEmail()).id(appUser.getId()).build();
    }
}
