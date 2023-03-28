package vela.database;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import vela.pojos.Event;
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
        User user = new User("admin", "admin", "admin", "admin@gmail.com", "admin", new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Project>(), null, null);
        User user1 = new User("admin1", "admin1", "admin1", "admin1@gmail.com", "admin", new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Project>(), null, null);
        User user2 = new User("admin2", "admin2", "admin2", "admin2@gmail.com", "admin", new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Project>(), null, null);
        User user3 = new User("admin3", "admin3", "admin3", "admin3@gmail.com", "admin", new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Project>(), null, null);

        user1.setTaskList(taskList);
        taskList.forEach(task -> task.setUser(user1));
//        taskList.forEach(task -> em.persist(task));
        user1.getRequestList().add(user2);
        user1.getRequestList().add(user3);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }


    public List<Task> getTaskList(String username, String sortTyp, Long projectId){
        TypedQuery<Task> taskTypedQuery;
        if(projectId != null){
          taskTypedQuery = em.createNamedQuery("Task.getProjectTasks", Task.class);
          taskTypedQuery.setParameter("pId", projectId);
        }
          else if(sortTyp == "deadline"){
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

    public Project getProject(Integer projectId){
        TypedQuery<Project> projectTypedQuery = em.createNamedQuery("Project.getProjectById", Project.class);
        projectTypedQuery.setParameter("projectID", projectId);
        return projectTypedQuery.getSingleResult();
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

    public void editUserData(User editedUser){
        if(!getAllUsers().contains(editedUser)){
            throw new NoSuchElementException("user does not exist");
        }
        em.merge(editedUser);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public List<Project> getAllProjects(){
        return em.createNamedQuery("Project.getAll").getResultList();
    }

    public List<Project> getProjectsByUser(String username){
        List<Project> allProjects = getAllProjects();
        List<Project> projects = new ArrayList<>();
        for(Project project : allProjects){
            project.getUserList().forEach(user -> {
                if(user.getUsername().equals(username)){
                    projects.add(new Project(project.getProjectID(), project.getDescription(), project.getName()));
                }
            });
        }
        return projects;
    }

    public List<Task> getTasksOfProject(int projectId){
        List<Project> projects = getAllProjects();
        List<Task> tasks = new ArrayList<>();
        projects.forEach(project -> {
            if(project.getProjectID() == projectId){
                tasks.addAll(project.getTaskList());
            }
        });
        return tasks;
    }

    public void deleteProject(int projectId){
//        em.createNamedQuery("Project.delete", Project.class).setParameter("projectID", projectId);
        List<Project> projects = getAllProjects();
        Project project = null;
        for(Project p : projects){
            if(p.getProjectID() == projectId){
                project = p;
            }
        }
        if(project == null){
            throw new NoSuchElementException("project does not exist");
        }
        em.remove(project);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public Event addEventToDB(Event event){
        em.persist(event);
        em.getTransaction().begin();
        em.getTransaction().commit();

        return event;
    }

    public void deleteEventFromDB(int eventId){
        try{
            Event event = em.find(Event.class, eventId);
            em.remove(event);
            em.getTransaction().begin();
            em.getTransaction().commit();
        } catch (Exception e){
            throw new NoSuchElementException("event does not exist");
        }
    }

    public Event updateEventOnDB(Event event){
        Event dbEvent = em.find(Event.class, event.getEventId());
        if(event.getDate() != null){
            dbEvent.setDate(event.getDate());
        }
        if(event.getDescription() != null){
            dbEvent.setDescription(event.getDescription());
        }
        if(event.getTitle() != null){
            dbEvent.setTitle(event.getTitle());
        }
        em.merge(dbEvent);
        em.getTransaction().begin();
        em.getTransaction().commit();

        return dbEvent;
    }

    public List<Event> getEventsByUser(String username){
        return em.createNamedQuery("Event.getEventsByUser").setParameter("username", username).getResultList();
    }

    public Project addUserToProject(int projectId, String username){
        if(username != null){
           Project project = em.find(Project.class, projectId);
           if(project == null){
               throw new NoSuchElementException("project does not exist");
           }
           User user = em.find(User.class, username);
           if(user == null){
               throw new NoSuchElementException("user does not exist");
           }
           project.addUser(user);
           user.addProject(project);
           em.merge(user);
           em.getTransaction().begin();
           em.getTransaction().commit();
           return project;
        }
        throw new NoSuchElementException("user does not exist");
    }

    public void saveFriendRequests(User user){
        em.merge(user);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void addFriendToDatabase(User mainUser){
        em.merge(mainUser);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.addUserWithTasks();
        //dbConnection.getTaskList("jartoc18", "title", null);
        System.out.println(dbConnection.getTaskList("admin1",null,null));
        System.out.println(dbConnection.getTaskList("admin2",null,null));

    }
}
