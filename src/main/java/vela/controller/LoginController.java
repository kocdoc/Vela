package vela.controller;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vela.pojos.User;
import vela.queries.UserQueries;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    public static final String JWT_SECRET = "adhjklukjkhsp85768qt6576845uhgihauiw%%%$";

    private User currentUser;

    public String createJWT(User user){
        JWSObject jwsObj = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(user.getUsername())
        );
        try {
            jwsObj.sign(new MACSigner(JWT_SECRET.getBytes()));
        } catch (JOSEException e) {
            log.info("problem at signing the token");
        }
        return jwsObj.serialize();
    }

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("jwtToken", createJWT(currentUser));
    }

    @PostMapping
    public String login(@ModelAttribute("loginUser") User user) {
        User userFromDB = UserQueries.getUserByUsername(user.getUsername());
        if (user == null || !user.getPassword().equals(userFromDB.getPassword())){
            log.info("login failed");
            return "nameOfHtmlFile";
        } else{
            log.info("login successful");
            currentUser = userFromDB;
            return "nameOfHtmlFile";
        }
    }
}
