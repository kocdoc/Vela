package vela.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "project")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@NamedQueries({
        @NamedQuery(name = "Project.getAll", query = "SELECT p FROM project p"),
        @NamedQuery(name = "Project.getProjectById", query = "SELECT p FROM project p WHERE p.projectID = :projectID"),
        @NamedQuery(name = "Project.delete", query = "DELETE FROM project p WHERE p.projectID = :projectID")
})
public class Project implements Serializable {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonNull
    private Integer projectID;

    @NonNull
    private String description;

    @NonNull
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "projectList", fetch = FetchType.EAGER)
    private List<User> userList;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Task> taskList;

    public Project(String description, String name) {
        this.description = description;
        this.name = name;
        userList = new ArrayList<>();
        taskList = new ArrayList<>();
    }

    public Project(Integer projectID, String description, String name, List<User> userList, List<Task> taskList) {
        this.projectID = projectID;
        this.description = description;
        this.name = name;
        if(userList == null){
            this.userList = new ArrayList<>();
        } else{
            this.userList = userList;
        }
        if(taskList == null){
            this.taskList = new ArrayList<>();
        } else{
            this.taskList = taskList;
        }
    }

    public void addUser(User user){
        if(!userList.contains(user)){
            userList.add(user);
        }
    }

    public void addTask(Task task){
        taskList.add(task);
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
