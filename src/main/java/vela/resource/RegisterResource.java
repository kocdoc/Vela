package vela.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.pojos.User;

import javax.management.openmbean.KeyAlreadyExistsException;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping("/api/register")
public class RegisterResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping
    public ResponseEntity register(@RequestBody User user){
        try{
            log.info(user.toString());
            dbConnection.addUser(user);
            log.info("user registered");
            return ResponseEntity.ok().body("registered");
        } catch(KeyAlreadyExistsException ex) {
            log.info("status: conflict (username already exists)");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict - username already exists");
        }
    }
}
