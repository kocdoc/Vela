package vela.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import vela.pojos.User;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

@Slf4j
public class UserQueries extends Queries{
    public static User getUserByUsername(String username){
        connect();
        User user = em.createNamedQuery("user.getUserByUsername", User.class).setParameter("username", username).getSingleResult();
        disconnect();
        return user;
    }

    public static User login(String username, String password){
        User user = getUserByUsername(username);
        if(user.getPassword().equals(password)){
            return user;
        } else{
            return null;
        }
    }

    public static void addUser(User user) throws KeyAlreadyExistsException {
        if(!getAllUsers().contains(user)){
            connect();
            em.persist(user);
            em.getTransaction().begin();
            em.getTransaction().commit();
            disconnect();
        } else{
            log.info("username already exists");
            throw new KeyAlreadyExistsException("username already exists");
        }
    }

    public static List<User> getAllUsers(){
        connect();
        List<User> users = em.createNamedQuery("user.getAllUsers", User.class).getResultList();
        disconnect();
        return users;
    }

    public static void main(String[] args) {
        System.out.println("user: " + getUserByUsername("jartoc18"));

    }
}
