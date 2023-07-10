package com.tracking.activitytracking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AppUserRequestDto {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "email is required")
    @Email(message = "provide a valid email")
    private String email;
    @NotBlank(message = "password id required")
    private String password;
//    private String confirmPassword;
}
