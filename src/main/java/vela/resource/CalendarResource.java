package vela.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.jwt.JWTNeededFilter;
import vela.pojos.Event;
import vela.pojos.User;

import java.text.ParseException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/calendar")
public class CalendarResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping
    public ResponseEntity addEvent(@RequestBody Event event, @RequestParam("user") String jwt){
        try {
            String username = JWTNeededFilter.getUsername(jwt);
            User user = dbConnection.getUserByUsername(username);
            event.setUser(user);
            return ResponseEntity.ok().body(dbConnection.addEventToDB(event));
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt not valid");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteEvent(@RequestParam("id") int eventId){
        try{
            dbConnection.deleteEventFromDB(eventId);
            return ResponseEntity.ok().body("event " + eventId + " deleted");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody Event event){
        return ResponseEntity.ok().body(dbConnection.updateEventOnDB(event));
    }

    @GetMapping
    public ResponseEntity getEventsByUser(@RequestParam("user") String jwt){
        try {
            String username = JWTNeededFilter.getUsername(jwt);
            return ResponseEntity.ok().body(dbConnection.getEventsByUser(username));
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt not valid");
        }
    }
}
