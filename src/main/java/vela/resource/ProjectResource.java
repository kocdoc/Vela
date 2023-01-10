package vela.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.jwt.JWTNeededFilter;
import vela.pojos.Project;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/project")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping("/getProjects")
    public ResponseEntity getProjectsByUser(@RequestParam(value = "user") String jwt){
        try {
            List<Project> projects = dbConnection.getProjectsByUser(JWTNeededFilter.getUsername(jwt));
            return ResponseEntity.ok(projects);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt not valid");
        }
    }

    @PostMapping("/addProject")
    public ResponseEntity addProject(@RequestBody Project project, @RequestParam(value="user") String jwt){
        try{
            Project myProject = new Project(project.getDescription(), project.getName());
            String username = JWTNeededFilter.getUsername(jwt);
            dbConnection.addProjectToDatabase(myProject, username);
            return ResponseEntity.ok("project added");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username does not exist");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt not valid");
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
