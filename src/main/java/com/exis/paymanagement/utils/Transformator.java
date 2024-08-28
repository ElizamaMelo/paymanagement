package com.exis.paymanagement.utils;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Service
public class Transformator {

    /*
    public <I, O> O transformToRequest(I input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> source = input.getClass();
        Class<?> target = Class.forName(source.getName() + "Request");
        O targetClass = (O) target.getDeclaredConstructor().newInstance();

        Field[] sourceFields = source.getDeclaredFields();
        Field[] targetFields = target.getDeclaredFields();

        Arrays.stream(sourceFields).forEach(sourceField ->
                Arrays.stream(targetFields).forEach(targetField -> {
                    validade(sourceField, targetField);
                    try {
                        targetField.set(targetClass, sourceField.get(input));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }));

        return targetClass;
    }

    private void validade(Field sourceField, Field targetField) {
        if (sourceField.getName().equals(targetField.getName()) && sourceField.getType().equals(targetField.getType())) {
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
        }
    }*/

    // Convert from one type to another (Entity to DTO or DTO to Entity)
    public static <T, U> U transform(T source, Class<U> targetClass) {
        try {
            // Create an instance of the target class
            U targetObject = targetClass.getDeclaredConstructor().newInstance();

            // Get all methods from source and target classes
            Method[] sourceMethods = source.getClass().getDeclaredMethods();
            Method[] targetMethods = targetClass.getDeclaredMethods();

            // Iterate through all source methods
            for (Method sourceMethod : sourceMethods) {
                // Check if the source method is a getter
                if (isGetter(sourceMethod)) {
                    // Find corresponding setter in the target class
                    String setterName = sourceMethod.getName().replace("get", "set");
                    Method targetMethod = findMethodByName(targetMethods, setterName);

                    if (targetMethod != null) {
                        // Invoke the getter method to get the value
                        Object value = sourceMethod.invoke(source);

                        // Invoke the corresponding setter method on the target object
                        targetMethod.invoke(targetObject, value);
                    }
                }
            }

            return targetObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to check if a method is a getter
    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && method.getReturnType() != void.class;
    }

    // Helper method to find a method by name
    private static Method findMethodByName(Method[] methods, String name) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name) && method.getParameterCount() == 1)
                .findFirst()
                .orElse(null);
    }
}
