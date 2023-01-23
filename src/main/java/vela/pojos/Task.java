package vela.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Task.getAllTasks", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username AND t.project.projectID = NULL"),
        @NamedQuery(name = "Task.getTaskByID", query = "SELECT t FROM Task t WHERE t.taskID = :id"),
        @NamedQuery(name = "Task.getProjectTasks", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username AND t.project.projectID = :pId"),
        @NamedQuery(name = "Task.SortedByCategory", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username AND t.project.projectID = NULL ORDER BY t.category"),
        @NamedQuery(name = "Task.SortedByDeadline", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username AND t.project.projectID = NULL ORDER BY t.deadline"),
        @NamedQuery(name = "Task.SortedByTitle", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username AND t.project.projectID = NULL ORDER BY t.title")
})
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Integer taskID;

    @JsonFormat(pattern = "yyyy-MM-dd",locale = "de_AT")
    private LocalDate deadline;

    @Column(name = "finished_date")
    private LocalDate finishedDate;
    private String category;
    private String title;

    @ManyToOne()
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @Transient
    private Long projectID;
}
