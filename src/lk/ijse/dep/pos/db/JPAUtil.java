package lk.ijse.dep.pos.db;

import lk.ijse.dep.crypto.DEPCrypt;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {


    private static EntityManagerFactory emf = buildEntityManagerFactory();

    private static String username;
    private static String password;
    private static String host;
    private static String port;
    private static String database;

    private static EntityManagerFactory buildEntityManagerFactory() {
        File propFile = new File("resources/application.properties");
        try(FileInputStream fis = new FileInputStream(propFile)){

            Properties properties = new Properties();
            properties.load(fis);

            username = properties.getProperty("javax.persistence.jdbc.user");
            // root ? NHBlZHRvb3I=
            username = DEPCrypt.decode(username, "dep4");
            password = properties.getProperty("javax.persistence.jdbc.password");
            // root ? NHBlZHRvb3I=
            password = DEPCrypt.decode(password, "dep4");
            properties.setProperty("javax.persistence.jdbc.user", username);
            properties.setProperty("javax.persistence.jdbc.password", password);

            return Persistence.createEntityManagerFactory("dep4", properties);
        } catch (Exception e){
            Logger.getLogger("lk.ijse.dep.pos.db.JPAUtil").log(Level.SEVERE, null, e);
            System.exit(1);
            return null;
        }

    }


    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }



}
