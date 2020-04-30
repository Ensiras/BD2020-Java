package mappingNativeTest;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class App {

    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("MySQL").createEntityManager();
        /*Employee employee = new Employee();
        employee.setName("Bert");

        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.detach(employee);*/

        // Native query to retrieve record (manually inserted in DB), mapped to an entity:
        Query q = em.createNativeQuery("SELECT * FROM employee", "employeeResult");
        List<Employee> resultList = q.getResultList();
        boolean contains = em.contains(resultList.get(0));
        String empString = resultList.get(0).toString();
        System.out.println(empString + " is a managed entity: " + contains);
    }
}
