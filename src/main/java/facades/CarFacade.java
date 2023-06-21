package facades;

import dtos.CarDto;
import entities.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {

    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */

    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CarDto> getAllCars() {
        EntityManager em = getEntityManager();
        List<Car> cars;
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
            cars = query.getResultList();
            return CarDto.getDtos(cars);
        } finally {
            em.close();
        }
    }

    public CarDto getCarById(long id) {
        EntityManager em = getEntityManager();
        try {
            Car car = em.find(Car.class, id);
            return new CarDto(car);
        } finally {
            em.close();
        }
    }

    public CarDto createCar(CarDto carDto) {
        EntityManager em = getEntityManager();
        Car car = new Car(carDto.getRegistration_number(), carDto.getBrand(), carDto.getMake(), carDto.getYear());
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CarDto(car);
    }

    public CarDto deleteById(long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Car car = em.find(Car.class, id);
            em.remove(car);
            em.getTransaction().commit();
            return new CarDto(car);
        } finally {
            em.close();
        }
    }

    public CarDto editCar(CarDto carDto) {
        EntityManager em = getEntityManager();
        try {
            Car car = em.find(Car.class, carDto.getId());
            car.setRegistration_number(carDto.getRegistration_number());
            car.setBrand(carDto.getBrand());
            car.setMake(carDto.getMake());
            car.setYear(carDto.getYear());
            em.getTransaction().begin();
            em.merge(car);
            em.getTransaction().commit();
            return new CarDto(car);
        } finally {
            em.close();
        }

    }

}
