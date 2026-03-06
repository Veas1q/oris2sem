package ru.itis.dis403.lab_02.orm;

import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Id;
import ru.itis.dis403.lab_02.orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private Connection connection;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }


    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T save(T entity) {
        // Определяем имя таблицы по имени класса.toLower()
        // Ищем в классе поля (Id, Column, ManyToOne)
        // Строим SQL запрос: Insert (id = null), Update (id !=null)
        // выполняем через JDBC запрос


        try {

            Class<?> clazz = entity.getClass();
            String table = clazz.getSimpleName().toLowerCase();

            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Column.class)) {
                    columns.add(field.getName());
                    values.add(field.get(entity));
                }

                if (field.isAnnotationPresent(ManyToOne.class)) {

                    Object ref = field.get(entity);

                    if (ref != null) {
                        Field refId = ref.getClass().getDeclaredField("id");
                        refId.setAccessible(true);

                        columns.add(field.getName() + "_id");
                        values.add(refId.get(ref));
                    }
                }
            }

            if (idValue == null) {

                StringBuilder sql = new StringBuilder();
                sql.append("insert into ").append(table).append(" (");

                sql.append(String.join(",", columns));
                sql.append(") values (");

                sql.append("?,".repeat(columns.size()));
                sql.setLength(sql.length() - 1);
                sql.append(")");

                PreparedStatement ps = connection.prepareStatement(sql.toString());

                for (int i = 0; i < values.size(); i++) {
                    ps.setObject(i + 1, values.get(i));
                }

                ps.executeUpdate();

            } else {

                StringBuilder sql = new StringBuilder();
                sql.append("update ").append(table).append(" set ");

                for (String col : columns) {
                    sql.append(col).append("=?,");
                }

                sql.setLength(sql.length() - 1);

                sql.append(" where id=?");

                PreparedStatement ps = connection.prepareStatement(sql.toString());

                int index = 1;

                for (Object val : values) {
                    ps.setObject(index++, val);
                }

                ps.setObject(index, idValue);

                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public void remove(Object entity) {
        try {

            Class<?> clazz = entity.getClass();
            String table = clazz.getSimpleName().toLowerCase();

            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);

            Object id = idField.get(entity);

            String sql = "delete from " + table + " where id=?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public <T> T find(Class<T> entityType, Object key) {
        // по имени класса получае имя таблицы, фиксируем id
        // select * from tableName where id = key
        // если результат не пустой - создаем объект
        // Ищем в классе поля (Id, Column, ManyToOne)
        // Для каждого поля пытаемся получить значение из ResultSet по имени
        // задаем значения
        // возвращаем созданный объект
        try {

            String table = entityType.getSimpleName().toLowerCase();

            String sql = "select * from " + table + " where id=?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, key);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            T entity = entityType.getDeclaredConstructor().newInstance();

            for (Field field : entityType.getDeclaredFields()) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Id.class) ||
                        field.isAnnotationPresent(Column.class)) {

                    field.set(entity, rs.getObject(field.getName()));
                }
            }

            return entity;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) {
        List<T> list = new ArrayList<>();

        try {

            String table = entityType.getSimpleName().toLowerCase();

            String sql = "select * from " + table;

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                T entity = entityType.getDeclaredConstructor().newInstance();

                for (Field field : entityType.getDeclaredFields()) {

                    field.setAccessible(true);

                    if (field.isAnnotationPresent(Id.class) ||
                            field.isAnnotationPresent(Column.class)) {

                        field.set(entity, rs.getObject(field.getName()));
                    }
                }

                list.add(entity);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }


}