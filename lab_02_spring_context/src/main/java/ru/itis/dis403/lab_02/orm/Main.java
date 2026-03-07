package ru.itis.dis403.lab_02.orm;

import ru.itis.dis403.lab_02.orm.model.City;
import ru.itis.dis403.lab_02.orm.model.Country;
import ru.itis.dis403.lab_02.orm.scanner.EntityScanner;
import ru.itis.dis403.lab_02.orm.schema.SchemaCreator;
import ru.itis.dis403.lab_02.orm.schema.SchemaValidator;

import java.sql.Connection;
import java.util.List;
public class Main {

    public static void main(String[] args) throws Exception {

        EntityManagerFactory factory = new EntityManagerFactory();

        EntityManager em = factory.getEntityManager();

        Connection connection = ((EntityManagerImpl) em).getConnection();

        List<Class<?>> entities =
                EntityScanner.find("ru.itis.dis403.lab_02.orm.model");

//        SchemaCreator.createTables(connection, entities);

//        SchemaValidator.validate(connection, entities);


        Country country = new Country();
        country.setName("Россия");




        City city = new City();
        city.setCountry(country);
        city.setName("Казань");
        em.save(country);
        em.save(city);

//        em.remove(country);

        List<City> cities = em.findAll(City.class);
        for (City c :cities) {
            System.out.println(c);
        }



        factory.close();
    }
}