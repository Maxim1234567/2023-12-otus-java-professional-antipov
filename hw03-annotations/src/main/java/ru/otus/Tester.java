package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Tester {
    //-------------------------run--------------------------

    public static Result run(Class<?> underTest) {
        Tester tester = new Tester();
        return tester.testing(underTest);
    }

    //-------------------------test-------------------------

    private Result testing(Class<?> classUnderTest) {
        Result result = new Result();

        Method beforeMethod = findMethod(classUnderTest, Before.class);
        Method afterMethod = findMethod(classUnderTest, After.class);

        for (Method testMethod: findTestMethods(classUnderTest)) {
            result.incrementTotal();

            try {
                Object obj = createClassUnderTest(classUnderTest);

                //Before
                callMethodClassUnderTest(obj, beforeMethod);

                //Test
                callMethodClassUnderTest(obj, testMethod);

                //After
                callMethodClassUnderTest(obj, afterMethod);

                result.incrementSuccessfully();
            } catch (Exception e) {
                result.incrementFailed();
            }
        }

        return result;
    }

    //-------------------------call-------------------------

    private Object createClassUnderTest(Class<?> classUnderTest) throws Exception {
        return findConstructorWithoutParams(classUnderTest).newInstance();
    }

    private void callMethodClassUnderTest(Object classUnderTest, Method method) throws Exception {
        if (Objects.isNull(method))
            return;

        method.setAccessible(true);
        method.invoke(classUnderTest);
    }

    //-------------------------init-------------------------

    private Constructor<?> findConstructorWithoutParams(Class<?> classUnderTest) throws Exception {
        return classUnderTest.getConstructor();
    }

    private List<Method> findTestMethods(Class<?> classUnderTest) {
        Method[] methods = classUnderTest.getMethods();

        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(Test.class))
                .toList();
    }

    private Method findMethod(Class<?> classUnderTest, Class<? extends Annotation> type) {
        Method[] methods = classUnderTest.getMethods();

        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(type))
                .findFirst()
                .orElse(null);
    }
}
