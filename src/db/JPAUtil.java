package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JPAUtil {
    private static EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        File propFile = new File("resources/application.properties");
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Persistence.createEntityManagerFactory("DEP",properties);
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
}