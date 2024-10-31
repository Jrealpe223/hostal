package co.com.usc.hostalusc.domain.model;


import co.com.usc.hostalusc.repository.model.common.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    User user;
    String token;
    Date tokenExpireDate;

}
