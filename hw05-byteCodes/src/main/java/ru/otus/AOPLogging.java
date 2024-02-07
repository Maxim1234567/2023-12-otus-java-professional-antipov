package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class AOPLogging {

    private static final Logger log = LoggerFactory.getLogger(AOPLogging.class);

    private AOPLogging() {}

    public static TestLoggingInterface proxyTestLogging() {
        InvocationHandler handler = new ProxyInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(AOPLogging.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class ProxyInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface testLoggingInterface;

        private final CacheMap<Method, Boolean> cache;

        public ProxyInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;
            cache = new CacheMap<>();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (cache.getOrPut(method, () -> hasAnnotatedMethod(method))) {
                log.info("executed method: {}, param: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(testLoggingInterface, args);
        }

        @Override
        public String toString() {
            return "ProxyInvocationHandler{" + testLoggingInterface + "}";
        }

        private boolean hasAnnotatedMethod(Method method) {
            return Arrays.stream(testLoggingInterface.getClass().getMethods())
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
