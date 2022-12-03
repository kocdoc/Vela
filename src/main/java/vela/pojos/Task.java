package vela.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Task.getAllTasks", query = "SELECT t FROM Task t WHERE t.user.username LIKE :username"),
        @NamedQuery(name = "Task.getTaskByID", query = "SELECT t FROM Task t WHERE t.taskID = :id")
})
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Integer taskID;
    private LocalDateTime deadline;

    @Column(name = "finished_date")
    private LocalDate finishedDate;
    private String category;
    private String title;

    @ManyToOne()
    @JoinColumn(name = "username")
    @ToString.Exclude
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    @ToString.Exclude
    private Project project;
}
