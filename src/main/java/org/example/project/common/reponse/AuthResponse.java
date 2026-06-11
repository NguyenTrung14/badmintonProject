package org.example.project.common.reponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String accessToken;
    String refreshToken;
}
