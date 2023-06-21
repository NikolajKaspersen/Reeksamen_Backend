package facades;

import dtos.WashingAssistantDto;
import entities.WashingAssistant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class WashingAssistantFacade {

    private static WashingAssistantFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private WashingAssistantFacade() {

    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */

    public static WashingAssistantFacade getWashingAssistantFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WashingAssistantFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<WashingAssistantDto> getAllWashingAssistants() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<WashingAssistant> query = em.createQuery("SELECT wa FROM WashingAssistant wa", WashingAssistant.class);
            List<WashingAssistant> washingAssistants = query.getResultList();
            return WashingAssistantDto.getDtos(washingAssistants);
        } finally {
            em.close();
        }
    }

    public WashingAssistantDto getWashingAssistantById(long id) {
        EntityManager em = getEntityManager();
        try {
            WashingAssistant washingAssistant = em.find(WashingAssistant.class, id);
            return new WashingAssistantDto(washingAssistant);
        } finally {
            em.close();
        }
    }

    public WashingAssistantDto createWashingAssistant(WashingAssistantDto washingAssistantDto) {
        EntityManager em = getEntityManager();
        WashingAssistant washingAssistant = new WashingAssistant(washingAssistantDto.getName(), washingAssistantDto.getPhone());
        try {
            em.getTransaction().begin();
            em.persist(washingAssistant);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new WashingAssistantDto(washingAssistant);
    }

    public WashingAssistantDto deleteWashingAssistant(long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            WashingAssistant washingAssistant = em.find(WashingAssistant.class, id);
            em.remove(washingAssistant);
            em.getTransaction().commit();
            return new WashingAssistantDto(washingAssistant);
        } finally {
            em.close();
        }
    }

    public WashingAssistantDto editWashingAssistant(WashingAssistantDto washingAssistantDto){
        EntityManager em = getEntityManager();
        try{
            WashingAssistant washingAssistant = em.find(WashingAssistant.class, washingAssistantDto.getId());
            washingAssistant.setName(washingAssistantDto.getName());
            washingAssistant.setPrimary_language(washingAssistantDto.getPrimary_language());
            washingAssistant.setYears_of_experience(washingAssistantDto.getYears_of_experience());
            washingAssistant.setPrice_per_hour(washingAssistantDto.getPrice_per_hour());
            em.getTransaction().begin();
            em.merge(washingAssistant);
            em.getTransaction().commit();
            return new WashingAssistantDto(washingAssistant);
        }finally {
            em.close();
        }
    }

}
