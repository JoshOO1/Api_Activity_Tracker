package com.tracking.activitytracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUserResponseDto {
    private Long id;
    private String username;
    private String email;
}
