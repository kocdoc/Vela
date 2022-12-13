package vela.resource;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JWSObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.jwt.JWTNeededFilter;
import vela.pojos.Task;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/taskmanager")
public class TaskManagerResource {

    private DBConnection instance = DBConnection.getInstance();

    @PostMapping("/getTasks")
    public List<Task> getTasks(@RequestParam(value="user") String user,@RequestBody String sortType) throws ParseException {
        List<Task> tasksList;

        tasksList = instance.getTaskList(JWTNeededFilter.getUsername(user), sortType);

        System.out.println(tasksList);

        tasksList.stream().forEach(task -> task.setUser(null));
        return tasksList;
    }

    @GetMapping("/getTaskByID")
    public Task getTaskByID(@RequestParam(value = "taskID") Integer taskID){
       Task task = instance.getTaskById(taskID);

       log.info(task.toString());

       task.setUser(null);
       return task;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PostMapping("/addTask")
    public ResponseEntity addNewTask(@RequestBody Task task, @RequestParam(value="user") String user) throws ParseException {
        List<Task> tasksList;
        tasksList = instance.getTaskList(JWTNeededFilter.getUsername(user), null);

        log.info(task.toString());
        System.out.println(tasksList);

        if (!tasksList.stream().filter(task1 -> task1.getTaskID().equals(task.getTaskID())).findFirst().isPresent()) {
            tasksList.add(task);
            instance.addTaskToDatabase(task,JWTNeededFilter.getUsername(user));
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Task already exists");
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestBody Task task, @RequestParam(value="user") String user) throws ParseException {
        List<Task> tasksList;
        tasksList = instance.getTaskList(JWTNeededFilter.getUsername(user), null);

        log.info("Help: task: "+  task.toString());
        tasksList.stream().forEach(task1 -> System.out.println(task1));

        if(tasksList.stream().filter(task1 -> task1.getTaskID().equals(task.getTaskID())).findFirst().isPresent()){
            tasksList.removeIf(task1 -> task1.getTaskID().equals(task.getTaskID()));
            instance.removeTaskFromDatabase(task,JWTNeededFilter.getUsername(user));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(task.toString());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Task can not be deleted");
    }

    @PatchMapping("/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody Task task, @RequestParam(value="user") String user) throws ParseException {
        List<Task> tasksList;
        tasksList = instance.getTaskList(JWTNeededFilter.getUsername(user), null);

        System.out.println("-------------------------");
        System.out.println("UpdateTask");
        System.out.println(task);
        System.out.println(JWTNeededFilter.getUsername(user));
        System.out.println(tasksList);

        if(tasksList.stream().filter(task1 -> task1.getTaskID().equals(task.getTaskID())).findFirst().isPresent()){
            System.out.println("is present");
            instance.updateTaskFromDatabase(task, JWTNeededFilter.getUsername(user));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(task.toString());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Task not available");
    }

    @PutMapping("/finishTask")
    public ResponseEntity<String> finishTask(@RequestBody Task task,@RequestParam(value="user") String user) throws ParseException {
        List<Task> tasksList;
        tasksList = instance.getTaskList(JWTNeededFilter.getUsername(user), null);

        if(tasksList.stream().filter(task1 -> task1.getTaskID().equals(task.getTaskID())).findFirst().isPresent()) {
            tasksList.stream().filter(task1 -> task1.getTaskID().equals(task.getTaskID())).findFirst().get().setFinishedDate(LocalDate.now());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(task.toString());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Task not found");
    }

    public static void main(String[] args) {
        TaskManagerResource test = new TaskManagerResource();
    }


}
