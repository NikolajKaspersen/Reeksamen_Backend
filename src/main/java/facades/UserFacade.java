package facades;

import dtos.UserDto;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    //Private Constructor to ensure Singleton
    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserDto getUserByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            return new UserDto(user);
        } finally {
            em.close();
        }
    }


    public List<UserDto> getAllUsers() {
        EntityManager em = getEntityManager();
        List<User> users;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
            return UserDto.getDtos(users);
        } finally {
            em.close();
        }
    }

    public UserDto createUser(UserDto userDto) {
        EntityManager em = getEntityManager();
        User user = new User(userDto.getUserName(), userDto.getUserPass());
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return new UserDto(user);
        } finally {
            em.close();
        }
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDto deleteUserByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, username);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            return new UserDto(user);
        } finally {
            em.close();
        }
    }

    public UserDto editUser(UserDto userDto) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, userDto.getUserName());
            user.setUserName(userDto.getUserName());
            user.setUserPass(userDto.getUserPass());
            em.getTransaction().begin();
            em.getTransaction().commit();
            return new UserDto(user);
        } finally {
            em.close();
        }
    }

}
