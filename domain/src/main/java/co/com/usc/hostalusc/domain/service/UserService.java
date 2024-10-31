package co.com.usc.hostalusc.domain.service;


import co.com.usc.hostalusc.domain.model.LoginInfo;
import co.com.usc.hostalusc.repository.model.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface UserService {

    List<User> getAllUsers(Boolean isAdmin);
    Page<User> getAllUsersBy(Pageable pageable, Boolean isAdmin);

    User getUserById(Long id);
    //User getUserByEmail(String email);
    //User getUserByPhone(String phone);

    User createUser(User user);
    User updateUser(Long id, User user);

    LoginInfo validateLogin(String email, String password);
}