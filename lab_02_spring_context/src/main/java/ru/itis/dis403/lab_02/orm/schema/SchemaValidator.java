package ru.itis.dis403.lab_02.orm.schema;


import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Id;
import ru.itis.dis403.lab_02.orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

public class SchemaValidator {

    public static void validate(Connection connection, List<Class<?>> entities) {

        try {

            DatabaseMetaData metaData = connection.getMetaData();

            for (Class<?> entity : entities) {

                String tableName = entity.getSimpleName().toLowerCase();

                ResultSet tables = metaData.getTables(null, null, tableName, null);

                if (!tables.next()) {
                    System.out.println("Table not found: " + tableName);
                    continue;
                }

                System.out.println("Table exists: " + tableName);

                for (Field field : entity.getDeclaredFields()) {

                    String columnName = null;

                    if (field.isAnnotationPresent(Id.class)) {
                        columnName = field.getName();
                    }

                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getName();
                    }

                    if (field.isAnnotationPresent(ManyToOne.class)) {
                        columnName = field.getName() + "_id";
                    }

                    if (columnName == null) {
                        continue;
                    }

                    ResultSet columns = metaData.getColumns(null, null, tableName, columnName);

                    if (!columns.next()) {
                        System.out.println("Column not found: " + tableName + "." + columnName);
                    } else {
                        System.out.println("Column exists: " + tableName + "." + columnName);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}