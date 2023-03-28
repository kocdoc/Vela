package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request_hist")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer requestId;

    private String requestingUsername;
    private String usernameToAdd;
    private Boolean accepted;


}
