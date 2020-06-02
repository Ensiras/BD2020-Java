package basicCRUD.dao;

import basicCRUD.domain.Car;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CarDao {

    @PersistenceContext
    EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Car get(int id) {
        return em.find(Car.class, id);
    }

    public Car insert(Car car) {
        em.persist(car);
        return car;
    }
}
