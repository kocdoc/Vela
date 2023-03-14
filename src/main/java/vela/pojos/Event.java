package vela.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Event.getEventsByUser", query = "SELECT e FROM Event e WHERE e.user.username = :username")
})
public class Event implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "date_of_event")
    private LocalDate date;

    @Column(name = "begin_of_event")
    private LocalTime begin;

    @Column(name = "end_of_event")
    private LocalTime end;

    private String description;
    private String name;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;
}
