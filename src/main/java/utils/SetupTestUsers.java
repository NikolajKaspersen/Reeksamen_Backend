package utils;


import entities.Role;
import entities.User;
import entities.Car;
import entities.Booking;
import entities.WashingAssistant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    Car car1 = new Car(123456, "BMW", "M3", "2018");
    Car car2 = new Car(654321, "Audi", "A4", "2019");
    Car car3 = new Car(123654, "Mercedes", "C63", "2017");

    user.addCar(car1);
    user.addCar(car2);
    user.addCar(car3);

    Booking b1 = new Booking("2019-12-01","2 hours");
    Booking b2 = new Booking("2019-12-03","3 hours");
    Booking b3 = new Booking("2019-12-05","1 hour");

    car1.addBooking(b1);
    car2.addBooking(b2);
    car3.addBooking(b3);




    WashingAssistant wa1 = new WashingAssistant("John","English","18 years", 20);
    WashingAssistant wa2 = new WashingAssistant("Peter","Danish","20 years", 25);
    WashingAssistant wa3 = new WashingAssistant("Hans","German","22 years", 30);

    b1.addWashingAssistant(wa1);
    b2.addWashingAssistant(wa2);
    b3.addWashingAssistant(wa3);

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
