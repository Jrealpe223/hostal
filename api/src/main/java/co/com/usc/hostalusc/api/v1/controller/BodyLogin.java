package co.com.usc.hostalusc.api.v1.controller;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyLogin{
    private String email;
    private String password;
}
