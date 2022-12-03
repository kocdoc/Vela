package vela.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vela.pojos.Task;
import vela.pojos.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static DBConnection instance;

    private EntityManagerFactory emf;
    private EntityManager em;

    private DBConnection() {
        this.connect();
    }

    private void connect() {
        emf = Persistence.createEntityManagerFactory("PU_VelaDB");
        em = emf.createEntityManager();
    }

    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public void disconnect() {
        em.close();
        emf.close();
    }

    private void addUserWithTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(null, LocalDateTime.now(), null, "Description1", "Title1", null, null));
        taskList.add(new Task(null, LocalDateTime.now(), null, "Description2", "Title2", null, null));
        taskList.add(new Task(null, LocalDateTime.now(), null, "Description3", "Title3", null, null));
        User user = new User("admin", "admin", "admin", "admin@gmail.com", "admin", null, null, null, null, null);
        user.setTaskList(taskList);
        taskList.forEach(task -> task.setUser(user));
        em.persist(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }


    public List<Task> getTaskList(String username){
        TypedQuery<Task> taskTypedQuery = em.createNamedQuery("Task.getAllTasks", Task.class);
        taskTypedQuery.setParameter("username",username);
        return taskTypedQuery.getResultList();
    }

    public Task getTaskById(Integer ID){
        TypedQuery<Task> taskTypedQuery = em.createNamedQuery("Task.getTaskByID", Task.class);
        taskTypedQuery.setParameter("id",ID);
        return taskTypedQuery.getSingleResult();
    }

    public void addTaskToDatabase(Task task, String username){
        User user = em.find(User.class,username);
        task.setUser(user);
        user.getTaskList().add(task);
        user.getTaskList().forEach(task1 -> task1.setUser(user));
        user.getTaskList().forEach(task1 -> System.out.println(task1.getUser()));
        em.persist(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void removeTaskFromDatabase(Task task, String username) {
        User user = em.find(User.class,username);
        task.setUser(user);
        Task taskToDelete = em.find(Task.class, task.getTaskID());
        taskToDelete.setUser(null);
        user.getTaskList().forEach(task1 -> task1.setUser(user));
        user.getTaskList().forEach(task1 -> System.out.println(task1.getUser()));
        em.getTransaction().begin();
        em.remove(taskToDelete);
        em.getTransaction().commit();
    }

    public void updateTaskFromDatabase(Task task, String username){
        Task updatedTask = em.find(Task.class, task.getTaskID());
        User user = em.find(User.class, username);
        user.getTaskList().forEach(task1 -> task1.setUser(user));
        updatedTask.setUser(user);
        if(!task.getTitle().isEmpty()){
            updatedTask.setTitle(task.getTitle());
        }
        if(!task.getDescription().isEmpty()){
            updatedTask.setDescription(task.getDescription());
        }
        em.getTransaction().begin();
        em.merge(updatedTask);
        em.getTransaction().commit();


    }

    public static void main(String[] args) {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.addUserWithTasks();
        dbConnection.disconnect();
    }


}