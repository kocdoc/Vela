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
import vela.pojos.User;

import java.text.ParseException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping("/getUserData")
    public ResponseEntity getUserData(@RequestParam(value="user") String jwt){
        try {
            User user = dbConnection.getUserByUsername(JWTNeededFilter.getUsername(jwt));
            User user2 = new User(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
            return ResponseEntity.ok(user2);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt not valid");
        }
    }

    @PostMapping("/editUserData")
    public ResponseEntity editUserData(@RequestBody User editedUser){
        try{
            dbConnection.editUserData(editedUser);
            return ResponseEntity.ok("user " + editedUser.getUsername() + " edited");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user does not exist");
        }
    }
}
