package org.art.java_web.labs.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Class Analyzer implementation.
 * Prints the whole structure of the passed class or object,
 * including constructors, fields, methods and some other
 * meta information (super class name, etc.).
 */
public class ClassAnalyzer {

    public void analyze(Class clazz) {
        Objects.requireNonNull(clazz);
        Class superClazz = clazz.getSuperclass();
        String modifier = Modifier.toString(clazz.getModifiers());
        if (modifier.length() > 0) {
            System.out.print(modifier + " ");
        }
        System.out.print("class " + clazz.getSimpleName());
        if (superClazz != null && superClazz != Object.class) {
            System.out.print(" extends " + superClazz.getSimpleName());
        }
        System.out.println(" {\n");
        analyzeConstructors(clazz);
        System.out.println();
        analyzeFields(clazz);
        System.out.println();
        analyzeMethods(clazz);
        System.out.println("}");
    }

    public void analyze(Object object) {
        Objects.requireNonNull(object);
        Class clazz = object.getClass();
        analyze(clazz);
    }

    private void analyzeConstructors(Class clazz) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            String name = constructor.getName();
            System.out.print("  ");
            String modifier = Modifier.toString(constructor.getModifiers());
            if (modifier.length() > 0) {
                System.out.print(modifier + " ");
            }
            System.out.print(name + "(");
            Class[] paramTypes = constructor.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
            Annotation[] annotations = constructor.getAnnotations();
            for (int k = 0; k < annotations.length; k++) {
                System.out.println(" @" + annotations[k].annotationType() + "\n");
            }
        }
    }

    private void analyzeFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            String name = field.getName();
            System.out.print("  ");
            String modifier = Modifier.toString(field.getModifiers());
            if (modifier.length() > 0) {
                System.out.print(modifier + " ");
            }
            System.out.println(type.getName() + " " + name + ";");
            Annotation[] annotations = field.getAnnotations();
            for (int k = 0; k < annotations.length; k++) {
                System.out.println(" @" + annotations[k].annotationType() + "\n");
            }
        }
    }

    private void analyzeMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Class retType = method.getReturnType();
            String name = method.getName();
            Annotation[] annotations = method.getAnnotations();
            for (int k = 0; k < annotations.length; k++) {
                System.out.println("  @" + annotations[k].annotationType());
            }
            System.out.print("  ");
            String modifier = Modifier.toString(method.getModifiers());
            if (modifier.length() > 0) {
                System.out.print(modifier + " ");
            }
            System.out.print(retType.getName() + " " + name + "(");
            Class[] paramTypes = method.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");\n");
        }
    }
}
