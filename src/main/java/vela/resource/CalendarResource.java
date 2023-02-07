package vela.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.pojos.Event;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/calendar")
public class CalendarResource {

    DBConnection dbConnection = DBConnection.getInstance();

    @PostMapping
    public ResponseEntity addEvent(@RequestBody Event event){
        return ResponseEntity.ok().body(dbConnection.addEventToDB(event));
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
}
