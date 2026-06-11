package org.example.project.model.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequestDto {
    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "password không được để trống")
    private String password;
}
