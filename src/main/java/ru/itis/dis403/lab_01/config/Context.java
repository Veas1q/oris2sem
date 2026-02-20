package ru.itis.dis403.lab_01.config;

import ru.itis.dis403.lab_01.annotation.Controller;
import ru.itis.dis403.lab_01.annotation.GetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private String scanPath = "ru.itis.dis403.lab_01";

    private Map<Class<?>, Object> components = new HashMap<>();
    private Map<String, Handler> handlerMapping = new HashMap<>();

    public Context() {
        scanComponent();
    }

    public Object getComponent(Class clazz) {
        return components.get(clazz);
    }

    public Handler getHandler(String path) {
        return handlerMapping.get(path);
    }

    private void scanComponent() {
        List<Class<?>> classes = PathScan.find(scanPath);

        // Создание экземпляров компонент
        // перебираем список классов
        // находим конструктор с аргументами, если такого нет - создаем экземпляр
        // размещаем в components, удаляем из списка
        // если есть конструктор с аргументами (только компоненты)
        // пытаемся получить из components объекты - аргументы
        // если полный набор - создаем экземпляр, иначе пропускаем итерацию
        int countClasses = classes.size();
        while (countClasses > 0) {
            // Перебираем классы компоненты
            objectNotFound:
            for (Class c : classes) {
                if (components.get(c) != null) continue;

                // берем первый попавшийся контруктор
                Constructor constructor = c.getConstructors()[0];
                // извлекаем типы аргументов конструктора
                Class[] types = constructor.getParameterTypes();
                // Пытаемся найти готовые компоненты по аргументу
                Object[] args = new Object[types.length];
                for (int i = 0; i < types.length; ++i) {
                    args[i] = components.get(types[i]);
                    if (args[i] == null) {
                        continue objectNotFound;
                    }
                }

                try {
                    Object o = constructor.newInstance(args);
                    components.put(c, o);
                    if (c.isAnnotationPresent(Controller.class)) {
                        registerHandlerMethods(o);
                    }
                    countClasses--;
                    System.out.println(c + " добавлен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void registerHandlerMethods(Object controller) {
        Class<?> clazz = controller.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            String path = mapping.value();

            handlerMapping.put(path, new Handler(controller, method));
        }
    }

}