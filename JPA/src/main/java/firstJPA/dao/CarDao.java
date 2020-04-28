package firstJPA.dao;

import firstJPA.domain.Car;

import javax.persistence.EntityManager;

public class CarDao {

    private final EntityManager em;

    public CarDao(EntityManager em) {
        this.em = em;
    }

    public Car getById(int id) {
        return em.find(Car.class, id);
    }

    public Car getByBrand(String brand) {
        return em.createQuery("SELECT c FROM Car c WHERE c.brand = :brand", Car.class)
                .setParameter("brand", brand).getSingleResult();
    }

}
