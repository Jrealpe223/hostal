package co.com.usc.hostalusc.api.v1.controller;

import co.com.usc.hostalusc.api.utils.CurrentUserPrincipal;
import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.model.LoginInfo;
import co.com.usc.hostalusc.domain.service.UserService;
import co.com.usc.hostalusc.repository.model.common.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "/v1/users")
@Tag(name = "User Controller")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get users"
    )
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getUsers(@CurrentSecurityContext(expression = "authentication.principal") CurrentUserPrincipal currentUserPrincipal) {
        log.info("Ejecuta servicio getUsers with param isAdmin: {}", currentUserPrincipal.getIsAdmin());
        try {
            List<User> users = userService.getAllUsers(currentUserPrincipal.getIsAdmin());
            return ResponseUtils.buildOutputContractSuccessResponse(users,   HttpStatus.OK.value(), (long) users.size());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error getUsers() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }



    @Operation(
            summary = "Get users by"
    )
    @GetMapping(value = "/by", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getUsersBy(Pageable pageable,@CurrentSecurityContext(expression = "authentication.principal") CurrentUserPrincipal currentUserPrincipal) {
        log.info("Ejecuta servicio getUsersBy con los parametros {} cuando es isAdmin {}", pageable,currentUserPrincipal.getIsAdmin());
        try {
            Page<User> user = userService.getAllUsersBy(pageable,currentUserPrincipal.getIsAdmin());
            return ResponseUtils.buildOutputContractSuccessResponse(user.getContent(),   HttpStatus.OK.value(), user.getTotalElements());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error getUsersBy() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(
            summary = "Get user by id"
    )
    @GetMapping(value = "/byId/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getUserById(@PathVariable("id") Long id) {
        log.info("Ejecuta servicio getUserById con el id: {}", id);
        try {
            User user= userService.getUserById(id);
            return ResponseUtils.buildOutputContractSuccessResponse(user,   HttpStatus.OK.value());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error getUserById() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(
            summary = "Create user"
    )
    @PostMapping(value = "/save",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> createUser(@RequestBody User user) {
        log.info("Ejecuta servicio createUser con el siguiente body: {}", user);
        try {
            User createUser= userService.createUser(user);
            return ResponseUtils.buildOutputContractSuccessResponse(createUser,   HttpStatus.OK.value());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error createUser() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    @Operation(
            summary = "Update user"
    )
    @PutMapping(value = "/update/{id}",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> updateUser(@RequestBody User user,@PathVariable("id") Long id) {
        log.info("Ejecuta servicio updateUser con el id {}  y el siguiente body: {}", id, user);
        try {
            User updateUser= userService.updateUser(id, user);
            return ResponseUtils.buildOutputContractSuccessResponse(updateUser,   HttpStatus.OK.value());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error updateUser() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    @Operation(
            summary = "Login user"
    )
    @PostMapping(value = "/login",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> validateLogin(@RequestBody BodyLogin bodyLogin) {
        log.info("Ejecuta servicio validateLogin con el siguiente body: {}", bodyLogin);
        try {
            LoginInfo validateLogin= userService.validateLogin(bodyLogin.getEmail(), bodyLogin.getPassword());
            return ResponseUtils.buildOutputContractSuccessResponse(validateLogin,   HttpStatus.OK.value());
        } catch (Exception ex){
            return ResponseUtils.buildOutputContractErrorResponse("Error validateLogin() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
