package basicCRUD.resources;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Singleton
@Alternative
public class EntityManagerProducer {

    @Produces
    public static EntityManager h2() {
        return Persistence.createEntityManagerFactory("h2").createEntityManager();
    }
}
