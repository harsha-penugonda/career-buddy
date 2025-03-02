package com.careerbuddy.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiException extends RuntimeException {

    private int statusCode;
    private String message;
}
