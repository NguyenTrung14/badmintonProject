package org.example.project.common.enumType.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse <T>{
    private int status;
    private boolean success;
    private String message;
    private T data;
    private T error;
}
