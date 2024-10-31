package co.com.usc.hostalusc.api.utils;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    String title;
    String message;
}
