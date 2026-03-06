package ru.itis.dis403.lab_02.orm.schema;

import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Id;
import ru.itis.dis403.lab_02.orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SchemaGenerator {

    public static String generateCreateTable(Class<?> entity) {

        String tableName = entity.getSimpleName().toLowerCase();

        StringBuilder sql = new StringBuilder();
        sql.append("create table ").append(tableName).append(" (\n");

        Field[] fields = entity.getDeclaredFields();

        List<String> columns = new ArrayList<>();

        for (Field field : fields) {

            if (field.isAnnotationPresent(Id.class)) {
                columns.add(field.getName() + " bigserial primary key");
            }

            if (field.isAnnotationPresent(Column.class)) {
                columns.add(field.getName() + " varchar(255)");
            }

            if (field.isAnnotationPresent(ManyToOne.class)) {
                columns.add(field.getName() + "_id bigint");
            }
        }

        sql.append(String.join(",\n", columns));

        sql.append("\n);");

        return sql.toString();
    }
}