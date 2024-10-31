package co.com.usc.hostalusc.domain.service.impl;

import co.com.usc.hostalusc.domain.exceptions.UscException;
import co.com.usc.hostalusc.domain.model.LoginInfo;
import co.com.usc.hostalusc.domain.service.UserService;
import co.com.usc.hostalusc.domain.utils.JWTHelper;
import co.com.usc.hostalusc.repository.model.common.User;
import co.com.usc.hostalusc.repository.repositories.common.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JWTHelper jwtHelper;


    public UserServiceImpl(UserRepository userRepository, JWTHelper jwtHelper) {
        this.userRepository = userRepository;
        this.jwtHelper = jwtHelper;
    }


    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String bcryptEncryptor(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<User> getAllUsers(Boolean isAdmin) {
        if (Boolean.FALSE.equals(isAdmin)) {
            throw new UscException("Error el usuario no es Administrador");
        }
        return userRepository.getAll();
    }

    @Override
    public Page<User> getAllUsersBy(Pageable pageable, Boolean isAdmin) {
        if (Boolean.FALSE.equals(isAdmin)) {
            throw new UscException("Error el usuario no es Administrador");
        }
        return userRepository.getAllBy(pageable);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id).orElseThrow(() -> new UscException(String.format("Usuario %s no encontrado",id )));
    }

    @Override
    public User createUser(User user) {
        if (user.getPrivacy() == null || !user.getPrivacy()){
            throw new UscException("para continuar debes aceptar nuestra politica de tratamiento de datos");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            throw new UscException("Por favor ingresa un correo electronico");
        }
        if (user.getPhone() == null || user.getPhone().isEmpty()){
            throw new UscException("Por favor ingresa un numero de telefono");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            throw new UscException("Por favor ingresa una contraseña");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()){
            throw new UscException("Por favor ingresa un nombre");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()){
            throw new UscException("Por favor ingresa un apellido");
        }
        Optional<User> byEmail = userRepository.getByEmail(user.getEmail().trim());
        if (byEmail.isPresent()){
            throw new UscException("El correo electronico ya existe");
        }
        if (user.getPhone().trim().matches("\\d+")){
            Optional<User> byPhone = userRepository.getByPhone(user.getPhone().trim());
            if (byPhone.isPresent()){
                throw new UscException("El numero de telefono ya existe");
            }

        }
        else {
            throw new UscException(String.format("El campo de %s solo acepta numeros", user.getPhone()));
        }

         user.setPassword(bcryptEncryptor(user.getPassword()));
         return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        if ( id == null){
            throw new UscException("El usuario no se puede actualizar ID null");
        }

        User userToUpdate = userRepository.getById(id).orElseThrow(() -> new UscException(String.format("Usuario %s no encontrado",id )));
        userToUpdate.setStatus(user.getStatus());
        if (user.getFirstName()!=null && !user.getFirstName().isEmpty())
        {
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (user.getLastName()!=null && !user.getLastName().isEmpty())
        {
            userToUpdate.setLastName(user.getLastName());
        }
        if (user.getPassword()!=null && !user.getPassword().isEmpty())
        {
            userToUpdate.setPassword(bcryptEncryptor(user.getPassword()));
        }
        if (user.getEmail()!=null && !user.getEmail().isEmpty()){

            Optional<User> byEmail = userRepository.getByEmail(user.getEmail().trim());

            if (!userToUpdate.getEmail().equalsIgnoreCase(user.getEmail()) && byEmail.isPresent())
            {
                throw new UscException("El correo electronico ya existe");
            }
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPhone()!=null && !user.getPhone().isEmpty() && user.getPhone().trim().matches("\\d+")) {

            Optional<User> byPhone = userRepository.getByPhone(user.getPhone().trim());

            if (!userToUpdate.getPhone().equalsIgnoreCase(user.getPhone()) && byPhone.isPresent()) {
                throw new UscException("El numero de telefono ya existe");
            }
            userToUpdate.setPhone(user.getPhone());
        }

        return userRepository.save(userToUpdate);
    }

    @Override
    public LoginInfo validateLogin(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){
            throw new UscException("El correo electronico y la contranseña son requeridos");
        }
        User byEmail = userRepository.getByEmail(email).orElseThrow(() -> new UscException(String.format("Usuario %s no encontrado", email)));
        if (!byEmail.getStatus()){
            throw new UscException("El usuario fue inactivado");
        }

        Boolean passwordValidated = doPasswordsMatch(password, byEmail.getPassword());
        if (!passwordValidated){
            throw new UscException("Correo electronico o contraseña incorrecta");
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(byEmail);
        loginInfo.setToken(jwtHelper.getJWTToken(byEmail.getFirstName()+ " " + byEmail.getLastName(), byEmail));
        loginInfo.setTokenExpireDate(new Date(System.currentTimeMillis() + 86400000));
        return loginInfo;
    }
}
