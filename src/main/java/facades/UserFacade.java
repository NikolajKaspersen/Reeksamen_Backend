package facades;

import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
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

    public User getUserByUserId(String id) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, id);
            return user;
        } finally {
            em.close();
        }
    }

    public User getAllUsers() {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, "all");
            return user;
        } finally {
            em.close();
        }
    }

    public User createUser(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
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

    public User deleteUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, username);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

    public User editUsername(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, username);
            em.getTransaction().begin();
            user.setUserName(username);
            user.setUserPass(password);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

}
