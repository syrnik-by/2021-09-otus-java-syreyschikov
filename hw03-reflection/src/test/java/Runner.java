import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] classNames) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        runTests(Tests.class);
    }

    private static void runTests(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Run tests class: " + clazz.getName());

        int successTests = 0, failedTests = 0;

        for (Method method : getMethodsByAnnotation(clazz, Test.class)) {
            System.out.println("TEST: " + method.getName());
            Object clazzObject = clazz.getDeclaredConstructor().newInstance();
            boolean failed = false;

            if (runMethods(getMethodsByAnnotation(clazz, Before.class), clazzObject)) {
                if (!invokeMethod(method, clazzObject))
                    failed = true;
            } else {
                failed = true;
            }

            if (!runMethods(getMethodsByAnnotation(clazz, After.class), clazzObject))
                failed = true;

            if (!failed)
                successTests++;
            else
                failedTests++;
        }

        System.out.println("-----RESULTS-----");
        System.out.println("SUCCESS: " + successTests);
        System.out.println("FAILED: " + failedTests);
        System.out.println("ALL: " + (successTests + failedTests));
    }

    private static boolean runMethods(List<Method> methods, Object clazzObject) {
        for (Method methodAfter : methods) {
            if (!invokeMethod(methodAfter, clazzObject))
                return false;
        }
        return true;
    }

    private static List<Method> getMethodsByAnnotation(Class clazz, Class annotaton) {
        Method[] methods = clazz.getMethods();
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotaton))
                result.add(method);
        }
        return result;
    }

    private static boolean invokeMethod(Method method, Object object) {
        try {
            method.invoke(object);
            System.out.println(method.getName() + " OK");
            return true;
        } catch (Throwable e) {
            System.out.println(method.getName() + " FAIL: " + e.toString());
            return false;
        }
    }
}
