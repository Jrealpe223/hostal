package co.com.usc.hostalusc.api.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserPrincipal {

    Boolean isAdmin;
    Long userId;


}


