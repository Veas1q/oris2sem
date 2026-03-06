package ru.itis.dis403.lab_02.orm.schema;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SchemaCreator {

    public static void createTables(Connection connection, List<Class<?>> entities) {

        for (Class<?> entity : entities) {

            String sql = SchemaGenerator.generateCreateTable(entity);

            try (Statement statement = connection.createStatement()) {

                statement.execute(sql);

                System.out.println("Table created for: " + entity.getSimpleName());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}