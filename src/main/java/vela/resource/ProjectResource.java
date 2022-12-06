package vela.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.pojos.Project;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/project")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping("/addProject")
    public ResponseEntity addProject(@RequestBody Project project, @RequestParam(value="username") String username){
        try{
            Project myProject = new Project(project.getDescription(), project.getName());
            dbConnection.addProjectToDatabase(myProject, username);
            return ResponseEntity.ok("task added");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username does not exist");
        }
    }

    @PostMapping("/editProject")
    public ResponseEntity editProject(@RequestBody Project editedProject){
        try{
            dbConnection.editProject(editedProject);
            return ResponseEntity.ok("project " + editedProject.getProjectID() + " edited");
        } catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("project does not exist");
        }
    }
}
