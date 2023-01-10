package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "vela_user")
@NamedQueries({
        @NamedQuery(name = "User.getUserByUsername", query = "SELECT u FROM vela_user u WHERE u.username LIKE :username"),
        @NamedQuery(name = "user.getUserByUsername", query = "SELECT u FROM vela_user u WHERE u.username = :username"),
        @NamedQuery(name = "user.getAllUsers", query = "SELECT u FROM vela_user u")
})
public class User implements Serializable {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "friend", joinColumns = {
            @JoinColumn(name = "own_username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "friend_username")
    })
    private List<User> friendList;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "friend_request", joinColumns = {
            @JoinColumn(name = "own_username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "request_username")
    })
    private List<User> requestList;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_project", joinColumns = {
            @JoinColumn(name = "username")
    }, inverseJoinColumns = {
            @JoinColumn(name = "project_id")
    })
    private List<Project> projectList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Task> taskList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Event> eventList;

    private void addProject(Project project){
        if(!projectList.contains(project)){
            projectList.add(project);
        }
    }

    public User(String username, String firstname, String lastname, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
