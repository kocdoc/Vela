package vela.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.jwt.JWTNeededFilter;
import vela.pojos.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/friendrequest")
public class FriendRequestResource {
    private DBConnection instance = DBConnection.getInstance();

    @GetMapping("/getRequests")
    public List<String> getRequests(@RequestParam String username) throws ParseException {
        User user = instance.getUserByUsername(JWTNeededFilter.getUsername(username));
        System.out.println(user);
        List<String> usernameList = new ArrayList<>();
        user.getRequestList().forEach(user1 -> usernameList.add(user1.getUsername()));
        return usernameList;
    }

    @PostMapping("/addUserToFR")
    public HttpStatus addUserToFriendRequestList(@RequestBody String mainUsername, @RequestParam String usernameToAdd) throws ParseException {
        String token[] = mainUsername.split("\"");
        User userToAdd = instance.getUserByUsername(usernameToAdd);
        User mainUser = instance.getUserByUsername(JWTNeededFilter.getUsername(token[3]));
        System.out.println(userToAdd);
        System.out.println(mainUser);
        //mainUser.setRequestList(new ArrayList<>());
        if(userToAdd == null){
            return HttpStatus.NOT_FOUND;
        }
        if(mainUser.getRequestList().contains(userToAdd)){
            return HttpStatus.CONFLICT;
        }
        mainUser.getRequestList().add(userToAdd);
        System.out.println(mainUser.getRequestList());
        try {
            instance.saveFriendRequests(mainUser);
        }
        catch (Exception e){
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.ACCEPTED;
    }


    //Get Friends
    @GetMapping("/getAllFriends")
    public ResponseEntity getFriendsOfAUser(@RequestParam String username) throws ParseException {
        User user = instance.getUserByUsername(JWTNeededFilter.getUsername(username));
        System.out.println(user);
        user.getFriendList().forEach(user1 -> user1.setFriendList(null));
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user.getFriendList());
    }

    // accept or decline
    @PostMapping("/proceedUser")
    public HttpStatus proceedUser(@RequestBody String user, @RequestParam String userToAccept, @RequestParam Boolean requestAnswer) throws ParseException {
        String token[] = user.split("\"");
        User mainUser = instance.getUserByUsername(JWTNeededFilter.getUsername(token[3]));
        User addedUser = instance.getUserByUsername(userToAccept);
        System.out.println(mainUser);
        System.out.println(addedUser);
        if(!requestAnswer || addedUser == null || mainUser == null){
            if(!requestAnswer){
                mainUser.getRequestList().removeIf(user1 -> user1.equals(addedUser));
                instance.saveFriendRequests(mainUser);
                instance.saveFriendRequests(addedUser);
            }
            return HttpStatus.CONFLICT;
        }

        mainUser.getFriendList().add(addedUser);
        System.out.println(mainUser.getFriendList());
        addedUser.getFriendList().add(mainUser);
        mainUser.getRequestList().removeIf(user1 -> user1.equals(addedUser));

        instance.saveFriendRequests(mainUser);
        return HttpStatus.OK;
    }

    public static void main(String[] args) {

    }

}
