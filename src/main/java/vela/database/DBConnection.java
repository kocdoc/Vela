package vela.database;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import vela.pojos.Project;
import vela.pojos.Task;
import vela.pojos.User;
import vela.resource.TaskManagerResource;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
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
        taskList.add(new Task(null, LocalDate.now(), null, "Description1", "Title1", null, null,null));
        taskList.add(new Task(null, LocalDate.now(), null, "Description2", "Title2", null, null,null));
        taskList.add(new Task(null, LocalDate.now(), null, "Description3", "Title3", null, null,null));
        User user = new User("admin", "admin", "admin", "admin@gmail.com", "admin", null, null, null, null, null);
        user.setTaskList(taskList);
        taskList.forEach(task -> task.setUser(user));
//        taskList.forEach(task -> em.persist(task));
        em.persist(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }


    public List<Task> getTaskList(String username, String sortTyp){
        TypedQuery<Task> taskTypedQuery;
        if(sortTyp == "deadline"){
            taskTypedQuery = em.createNamedQuery("Task.getAllTasks", Task.class);
        } else if (sortTyp == "category") {
            taskTypedQuery = em.createNamedQuery("Task.SortedByCategory", Task.class);
        }
        else{
            taskTypedQuery = em.createNamedQuery("Task.SortedByTitle", Task.class);
        }
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
        if(!task.getCategory().isEmpty()){
            updatedTask.setCategory(task.getCategory());
        }
//        if(task.getDeadline() != null){
            updatedTask.setDeadline(task.getDeadline());
//        }
//        if(task.getFinishedDate() != null){
            updatedTask.setFinishedDate(task.getFinishedDate());
//        }
        em.persist(updatedTask);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public User getUserByUsername(String username){
        User user = null;
        try{
            user = em.createNamedQuery("user.getUserByUsername", User.class).setParameter("username", username).getSingleResult();
        } catch(NoResultException e){
            user = null;
        }
        return user;
    }

    public User login(String username, String password){
        User user = getUserByUsername(username);
        try{
            if(user.getPassword().equals(password)){
                return user;
            } else{
                return null;
            }
        } catch(NullPointerException e){
            return null;
        }
    }

    public void addUser(User user) throws KeyAlreadyExistsException {
        if(!getAllUsers().contains(user)){
            em.persist(user);
            em.getTransaction().begin();
            em.getTransaction().commit();
        } else{
            log.info("username already exists");
            throw new KeyAlreadyExistsException("username already exists");
        }
    }

    public List<User> getAllUsers(){
        List<User> users = em.createNamedQuery("user.getAllUsers", User.class).getResultList();
        return users;
    }

    public void addProjectToDatabase(Project project, String username){
        User user = em.find(User.class, username);
        project.addUser(user);
        try{
            user.getProjectList().add(project);
        } catch (NullPointerException e){
            throw new NoSuchElementException("user does not exist");
        }
        em.merge(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void editProject(Project editedProject){
        if(!getAllProjects().contains(editedProject)){
            throw new NoSuchElementException("project does not exist");
        }
        em.merge(editedProject);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public List<Project> getAllProjects(){
        return em.createNamedQuery("Project.getAll").getResultList();
    }

    public static void main(String[] args) {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.login("jartoc18", "geheim123");
        dbConnection.getTaskList("jartoc18", "title");
    }
}
