package co.com.usc.hostalusc.repository.repositories.common;

//consultas personalidad de cada entida crea anteriormente
import co.com.usc.hostalusc.repository.model.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.status=true")
    @Transactional(readOnly = true)
    List<User> getAll();

    @Query("select u from User u ")
    @Transactional(readOnly = true)
    Page<User> getAllBy(Pageable pageable);

    @Query("select u from User u where upper(u.email)=upper(?1) and u.status=true")
    @Transactional(readOnly = true)
    Optional<User> getByEmail(String email);

    @Query("select u from User u  where u.id= ?1 ")
    @Transactional(readOnly = true)
    Optional<User>  getById(Long id);

    @Query("select u from User u where u.phone=?1 and u.status=true")
    @Transactional(readOnly = true)
    Optional<User> getByPhone(String phone);



}