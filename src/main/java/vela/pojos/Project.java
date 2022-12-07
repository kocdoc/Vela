package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "project")
@NoArgsConstructor
@Data
@NamedQueries({
        @NamedQuery(name = "Project.getAll", query = "SELECT p FROM project p")
})
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

    public Project(String description, String name) {
        this.description = description;
        this.name = name;
        userList = new ArrayList<>();
        meetingList = new ArrayList<>();
    }

    public Project(Integer projectID, String description, String name, List<User> userList, List<Meeting> meetingList) {
        this.projectID = projectID;
        this.description = description;
        this.name = name;
        if(userList == null){
            this.userList = new ArrayList<>();
        } else{
            this.userList = userList;
        }
        if(meetingList == null){
            this.meetingList = new ArrayList<>();
        } else{
            this.meetingList = meetingList;
        }
    }

    public void addUser(User user){
        if(!userList.contains(user)){
            userList.add(user);
        }
    }

    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Objects.equals(getProjectID(), project.getProjectID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectID());
    }
}
