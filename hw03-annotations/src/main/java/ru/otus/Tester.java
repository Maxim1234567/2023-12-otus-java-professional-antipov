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

    private int successfully;
    private int failed;
    private int total;

    private Class<?> classUnderTest;

    private Constructor<?> constructorUnderTest;

    private Method beforeMethodClassUnderTest;

    private List<Method> testMethodsClassUnderTest;

    private Method afterMethodClassUnderTest;

    //-------------------------run--------------------------

    public static Result run(Class<?> underTest) throws Exception {
        Tester tester = new Tester(underTest);
        return tester.testing();
    }

    private Tester(Class<?> classUnderTest) throws Exception {
        this.classUnderTest = classUnderTest;

        initConstructorUnderTest();
        initMethods();
    }

    //-------------------------test-------------------------

    private Result testing() {
        Result result = new Result();

        for (Method testMethod: testMethodsClassUnderTest) {
            result.incrementTotal();

            try {
                Object obj = createClassUnderTest();

                //Before
                callMethodClassUnderTest(obj, beforeMethodClassUnderTest);

                //Test
                callMethodClassUnderTest(obj, testMethod);

                //After
                callMethodClassUnderTest(obj, afterMethodClassUnderTest);

                result.incrementSuccessfully();
            } catch (Exception e) {
                result.incrementFailed();
            }
        }

        return result;
    }

    //-------------------------call-------------------------

    private Object createClassUnderTest() throws Exception {
        return constructorUnderTest.newInstance();
    }

    private void callMethodClassUnderTest(Object classUnderTest, Method method) throws Exception {
        if (Objects.isNull(method))
            return;

        method.setAccessible(true);
        method.invoke(classUnderTest);
    }

    //-------------------------init-------------------------

    private void initConstructorUnderTest() throws Exception {
        constructorUnderTest = classUnderTest.getConstructor();
    }

    private void initMethods() {
        Method[] methods = classUnderTest.getMethods();

        beforeMethodClassUnderTest = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(Before.class))
                .findFirst()
                .orElse(null);

        testMethodsClassUnderTest = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(Test.class))
                .toList();

        afterMethodClassUnderTest = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(After.class))
                .findFirst()
                .orElse(null);
    }
}
