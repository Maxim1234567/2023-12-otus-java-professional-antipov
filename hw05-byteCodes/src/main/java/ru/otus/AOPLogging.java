package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class AOPLogging {

    private static final Logger log = LoggerFactory.getLogger(AOPLogging.class);

    private AOPLogging() {}

    public static Object proxyLogging(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getConstructor();

        InvocationHandler handler = new ProxyInvocationHandler(constructor.newInstance());
        return Proxy.newProxyInstance(AOPLogging.class.getClassLoader(), clazz.getInterfaces(), handler);
    }

    static class ProxyInvocationHandler implements InvocationHandler {

        private final Object loggingObject;

        private final CacheMap<Method, Boolean> cache;

        public ProxyInvocationHandler(Object loggingObject) {
            this.loggingObject = loggingObject;
            cache = CacheFactory.create();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (cache.getOrPut(method, () -> hasAnnotatedMethod(method))) {
                log.info("executed method: {}, param: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(loggingObject, args);
        }

        @Override
        public String toString() {
            return "ProxyInvocationHandler{" + loggingObject + "}";
        }

        private boolean hasAnnotatedMethod(Method method) {
            return Arrays.stream(loggingObject.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .anyMatch(m -> equalsSignatureMethod(m, method));
        }

        private boolean equalsSignatureMethod(Method m1, Method m2) {
            if (m1.getName().equals(m2.getName())) {
                    if (!m1.getReturnType().equals(m2.getReturnType()))
                        return false;

                    return equalParamTypes(m1.getParameterTypes(), m2.getParameterTypes());
            }

            return false;
        }

        private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i])
                        return false;
                }

                return true;
            }

            return false;
        }
    }
}
