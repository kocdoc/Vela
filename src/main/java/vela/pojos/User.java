package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "vela_user")
@NamedQueries({
        @NamedQuery(name = "user.getUserByUsername", query = "SELECT u FROM vela_user u WHERE u.username = :username")
})
public class User implements Serializable {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "friend", joinColumns = {
            @JoinColumn(name = "own_username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "friend_username")
    })
    private List<User> friendList;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "friend_request", joinColumns = {
            @JoinColumn(name = "own_username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "request_username")
    })
    private List<User> requestList;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_project", joinColumns = {
            @JoinColumn(name = "username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "project_id")
    })
    private List<Project> projectList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Task> taskList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Event> eventList;
}
