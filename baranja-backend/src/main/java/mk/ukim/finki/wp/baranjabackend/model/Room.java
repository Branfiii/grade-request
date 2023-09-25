package mk.ukim.finki.wp.baranjabackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {

    @Id
    private String name;

    private String locationDescription;

    private String equipmentDescription;

    @Enumerated
    private RoomType type;

    private Long capacity;
}
