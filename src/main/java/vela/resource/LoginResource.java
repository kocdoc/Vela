package vela.resource;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vela.database.DBConnection;
import vela.pojos.LoginData;
import vela.pojos.User;

import java.util.Map;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping("/api/login")
public class LoginResource {

    DBConnection dbConnection = DBConnection.getInstance();

    public static final String JWT_SECRET = "adhjklukjkhsp85768qt6576845uhgihauiw%%%$";

    public String createJWT(User user) throws JOSEException {
        JWSObject jwsObj = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(user.getUsername())
        );
        jwsObj.sign(new MACSigner(JWT_SECRET.getBytes()));
        return jwsObj.serialize();
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginData loginData) {
        log.info(loginData.toString());
        User user = dbConnection.login(loginData.getUsername(), loginData.getPassword());
        if (user == null){
            log.info("status: unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized - wrong username or password");
        }
        try{
            log.info("status: authorized");
            return ResponseEntity.ok(Map.of("token", createJWT(user), "username", user.getUsername()));

        }
        catch(JOSEException ex){
            log.info("status: bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }
    }
}
