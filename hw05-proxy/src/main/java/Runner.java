public class Runner {
    public static void main(String[] classNames) throws NoSuchMethodException {
        TestLoggingInterface logging = TestLoggingProxy.createInstance();
        logging.calculation(1);
        logging.calculation(1, 2);
        logging.calculation(1, 2, "qwerty");
        logging.calculation(1, 2, 3);
    }
}
