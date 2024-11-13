package co.com.usc.hostalusc.repository.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "room", schema = "common")
@Entity
@EqualsAndHashCode(of = "id")

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number", unique = true, nullable = false)
    private String roomNumber;

    @Column(name = "room_type", length = 50)
    private String roomType;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "status") // e.g., Available, Reserved, Maintenance
    private String status;


}
