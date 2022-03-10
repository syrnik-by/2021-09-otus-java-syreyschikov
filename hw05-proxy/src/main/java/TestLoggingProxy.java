import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TestLoggingProxy {
    public static TestLoggingInterface createInstance() throws NoSuchMethodException {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(TestLoggingProxy.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;
        private final List<Method> methods;
        private Logger logger = Logger.getLogger(TestLoggingInvocationHandler.class.getName());

        TestLoggingInvocationHandler(TestLoggingInterface testLoggingInterface) throws NoSuchMethodException {
            this.testLoggingInterface = testLoggingInterface;
            this.methods = getMethodsByAnnotation();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            return method.invoke(testLoggingInterface, args);
        }

        private List<Method> getMethodsByAnnotation() throws NoSuchMethodException {
            Method[] methodsInterface = TestLoggingInterface.class.getMethods();
            List<Method> result = new ArrayList<>();
            for(Method method : methodsInterface) {
                if(TestLogging.class.getMethod(method.getName(), method.getParameterTypes())
                        .isAnnotationPresent(Log.class))
                    result.add(method);
            }
            return result;
        }
    }
}