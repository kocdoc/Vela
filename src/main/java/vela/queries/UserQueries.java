package vela.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vela.pojos.User;

public class UserQueries extends Queries{
    public static User getUserByUsername(String username){
        connect();
        User user = em.createNamedQuery("user.getUserByUsername", User.class).setParameter("username", username).getSingleResult();
        disconnect();
        return user;
    }

}
