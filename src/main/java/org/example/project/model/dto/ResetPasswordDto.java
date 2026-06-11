package org.example.project.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDto {

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}
