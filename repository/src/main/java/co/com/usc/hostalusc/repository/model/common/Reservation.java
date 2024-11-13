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
@Table(name = "reservation", schema = "common")
@Entity
@EqualsAndHashCode(of = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkInDate;

    @Column(name = "check_out_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOutDate;

    @Column(name = "reservation_status") // e.g., Pending, Confirmed, Canceled
    private String reservationStatus;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name="reservation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
}
