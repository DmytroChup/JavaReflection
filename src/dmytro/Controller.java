package dmytro;

import dmytro.test.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        System.out.println("----------Task 1-------------");
        System.out.println(task1("java.io.FileReader"));
        Person person = new Person("Dima", 19, 0);
        System.out.println("----------------Task 2--------------:\n");
        task2(person);
        System.out.println("------------Task 3---------------");
        task3(person, "setName", "Andrew");
        task3(person, "setAge", 30);
        task3(person, "setAge", 25);
        task3(person, "setKids", 2);
        task3(person, "getAge", "fd");
    }

    public static void task3(Object o, String methodName, Object... params) {
        Class<?> cls = o.getClass();
        Class<?>[] parameterTypes = Arrays.stream(params)
                .map(Object::getClass).toList().toArray(new Class<?>[0]);
        try {
            Method method = cls.getDeclaredMethod(methodName, parameterTypes);
            System.out.println("State before: " + o);
            method.invoke(o, params);
            System.out.println("State after: " + o + "\n");
        } catch (NoSuchMethodException e) {
            System.out.println("NoSuchMethodException");
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void task2(Object o) {
        Class<?> cls = o.getClass();
        int ch, counter = 1;
        getFieldsOfClass(o);

        System.out.println("All methods of the class:\n");
        List<Method> methods = getPublicMethods(cls);
        for(Method method: methods) {
            System.out.println(method + "\n");
        }

        List<Method> methodsWithoutParam = new ArrayList<>();
        System.out.println("Methods without parameters:\n");
        for(Method method: methods) {
            if(method.getParameterCount() == 0) {
                methodsWithoutParam.add(method);
                System.out.println((counter++) + ". "  + method + "\n");
            }
        }
        System.out.println("Choose one of a methods: ");
        ch = input();
        while(ch > 0 && ch <= methodsWithoutParam.size()) {
            try {
                System.out.println(methodsWithoutParam.get(ch - 1) + "\nResult: " +
                        methodsWithoutParam.get(ch - 1).invoke(o));
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println("For close program input any number out of range");
            System.out.println("Choose one of a methods: ");
            ch = input();
        }
    }

    private static int input() {
        int ch = 0;
        boolean flag = true;
        while(flag) {
            flag = false;
            try {
                ch = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                flag = true;
                System.err.println("Mistake!");
            }
        }
        return ch;
    }

    private static List<Method> getPublicMethods(Class<?> cls) {
        return new ArrayList<>(List.of(cls.getDeclaredMethods())).stream()
                .filter(method -> Modifier.isPublic(method.getModifiers())).toList();
    }

    private static void getFieldsOfClass(Object o) {
        Class<?> cls = o.getClass();
        List<Field> fields = new ArrayList<>(List.of(cls.getDeclaredFields()));
        System.out.println("Fields in " + cls.getSimpleName() + ": ");
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                System.out.println(field.getType() + " " + field.getName() + " = " + field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        });
        fields.clear();
    }
    public static String task1(String className) {
        Class<?> nClass;
        try {
            nClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name of class you wrote: ").append(className).append("\n");

        Package pack = nClass.getPackage();
        int modifiers = nClass.getModifiers();
        Class<?> superClass = nClass.getSuperclass();

        stringBuilder.append("Package: ").append(pack).append("\nModifiers:").append(modifiers)
                .append("\nClass name: ").append(nClass.getSimpleName()).append("\nSuper Class: ")
                .append(superClass).append("\n");

        stringBuilder.append("\nImplemented Interfaces:\n");

        List<Class<?>> interfaces = new ArrayList<>(List.of(nClass.getInterfaces()));
        for(Class<?> checkingClass = nClass.getSuperclass(); checkingClass != Object.class; checkingClass = checkingClass.getSuperclass()) {
            interfaces.addAll(List.of(checkingClass.getInterfaces()));
        }
        for(Class<?> inter: interfaces) {
            stringBuilder.append(inter.getName()).append("\n");
        }

        stringBuilder.append("\nConstructors:\n");
        List<Constructor<?>> constructors = new ArrayList<>(List.of(nClass.getDeclaredConstructors()));
        for(Constructor<?> constructor: constructors) {
            stringBuilder.append(constructor).append("\n");
        }

        stringBuilder.append("\nMethods:\n");
        List<Method> methods = new ArrayList<>(List.of(nClass.getDeclaredMethods()));
        for(Class<?> checkingClass = nClass.getSuperclass(); checkingClass != Object.class; checkingClass = checkingClass.getSuperclass()) {
            methods.addAll(List.of(checkingClass.getDeclaredMethods()));
        }
        for(Method method: methods) {
            stringBuilder.append(method).append("\n");
        }

        stringBuilder.append("\nFields:\n");
        List<Field> fields = new ArrayList<>(List.of(nClass.getDeclaredFields()));
        for(Field field: fields) {
            stringBuilder.append(field).append("\n");
        }

        return stringBuilder.toString();
    }
}
