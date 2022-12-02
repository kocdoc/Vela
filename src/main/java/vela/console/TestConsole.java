package vela.console;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


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

    public static void main(String[] args) {
        TestConsole console =  new TestConsole();
        console.connect();
        console.disconnect();
    }
}
