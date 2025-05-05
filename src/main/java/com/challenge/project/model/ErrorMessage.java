package com.challenge.project.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorMessage {
    private int code;
    private String description;
}
