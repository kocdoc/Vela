package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project implements Serializable {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer projectID;
    private String description;
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "projectList")
    private List<User> userList;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    private List<Meeting> meetingList;
}
