package ru.itis.dis403.lab_01.config;

import ru.itis.dis403.lab_01.annotation.Component;
import ru.itis.dis403.lab_01.annotation.Controller;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PathScan {

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(".", "/");
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + scannedPackage);
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                Class<?> clazz = Class.forName(className);

                if (isComponent(clazz)) {
                    classes.add(clazz);
                }
            } catch (ClassNotFoundException ignore) {
            }

        }
        return classes;
    }

    private static boolean isComponent(Class<?> clazz) {

        if (clazz.isAnnotation() || clazz.isInterface()) {
            return false;
        }

        if (clazz.isAnnotationPresent(Component.class)) {
            return true;
        }

        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType()
                    .isAnnotationPresent(Component.class)) {
                return true;
            }
        }

        return false;
    }
}