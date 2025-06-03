package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:objectdb/biblioteca.odb");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}