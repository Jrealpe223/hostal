package co.com.usc.hostalusc.repository.model.common;

import co.com.usc.hostalusc.repository.model.common.types.RoomStatusEnum;
import co.com.usc.hostalusc.repository.model.common.types.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.*;



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

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", length = 50, nullable = false)
    private RoomTypeEnum roomType;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "dormitories")
    private Integer dormitories;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false) // e.g., Available, Reserved, Maintenance
    private RoomStatusEnum status;
}
