package vela.console;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vela.pojos.User;
import vela.resource.LoginResource;

import java.text.ParseException;


public class TestConsole {

    private EntityManager em;
    private EntityManagerFactory emf;

    private void connect(){
        emf = Persistence.createEntityManagerFactory("PU_VelaDB");
        em = emf.createEntityManager();
    }

    private void disconnect(){
        em.close();
        emf.close();
    }

    public static void main(String[] args) throws JOSEException, ParseException {
        LoginResource loginResource = new LoginResource();
        String jwt = loginResource.createJWT(new User("admin2", "admin", "admin", "admin@gmail.com", "admin", null, null, null, null, null));
        System.out.println(jwt);
        jwt = jwt.replace("Bearer", "");
        JWSObject jwsObject = JWSObject.parse(jwt);
        System.out.println(jwsObject.getPayload());
    }
}
