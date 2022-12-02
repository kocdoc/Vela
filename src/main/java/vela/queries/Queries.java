package vela.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Queries {
    public static EntityManager em;
    public static EntityManagerFactory emf;

    public static void connect(){
        emf = Persistence.createEntityManagerFactory("PU_VelaDB");
        em = emf.createEntityManager();
    }

    public static void disconnect(){
        em.close();
        emf.close();
    }
}
