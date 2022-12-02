package vela.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.pojos.User;
import vela.queries.UserQueries;

import javax.management.openmbean.KeyAlreadyExistsException;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    @PostMapping
    public ResponseEntity register(@RequestBody User user){
        try{
            log.info(user.toString());
            UserQueries.addUser(user);
            log.info("user registered");
            return ResponseEntity.ok().body("registered");
        } catch(KeyAlreadyExistsException ex) {
            log.info("status: conflict (username already exists)");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict - username already exists");
        }
    }
}
