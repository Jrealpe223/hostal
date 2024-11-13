package co.com.usc.hostalusc.repository.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;
//configuracion del mapeo de entidades sean crean las clases y se guian para ser mapeadas
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users", schema = "common")
@Entity
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 80)
    private String firstName;

    @Column(name = "last_name", length = 80)
    private String lastName;

    @Column(name = "phone", length = 10 , unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_password")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "user_status")
    private Boolean status;

    @Column(name = "policy_privacy")
    private Boolean privacy;

    @Column(name = "user_admin")
    private Boolean admin;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "date_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = new Date();
        status = Boolean.TRUE;
    }
    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}


