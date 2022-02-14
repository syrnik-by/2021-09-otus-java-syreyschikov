import java.util.Random;

public class Tests {

    public Tests() {
        System.out.println("Im ready");
    }

    private int a;
    private int b;
    private Random random = new Random();

    @Before
    public void generateA() {
        a = random.nextInt(20) - 5;
        System.out.println("a = " + a);
    }

    @Before
    public void generateB() {
        b = random.nextInt(20) - 5;
        System.out.println("b = " + b);
    }

    @Test
    public void checkMultiplication() throws Exception {
        int result = Calculator.multiplication(a, b);
        if (a * b != result)
            throw new Exception("Exception Multiplication");
        System.out.println("a * b = " + result);
        System.out.println("Multiplication OK");
    }

    @Test
    public void checkAddition() throws Exception {
        int result = Calculator.addition(a, b);
        if (a + b != result)
            throw new Exception("Exception Addition");
        System.out.println("a + b = " + result);
        System.out.println("Addition OK");
    }

    @Test
    public void checkDivision() throws Exception {
        int result = Calculator.division(a, b);
        if (a / b != result)
            throw new Exception("Exception Division");
        System.out.println("a / b = " + result);
        System.out.println("Division OK");
    }

    @Test
    public void checkSubtraction() throws Exception {
        int result = Calculator.subtraction(a, b);
        if (a - b != result)
            throw new Exception("Exception Subtraction");
        System.out.println("a - b = " + result);
        System.out.println("Subtraction OK");
    }

    @Test
    public void checkDivisionZero() throws Exception {
        try {
            Calculator.division(a, 0);
            throw new Exception("Exception Division Zero");
        } catch (Exception e) {
            System.out.println("Division Zero OK");
        }
    }

    @Test
    public void checkBadDivisionZero() throws Exception {
        Calculator.division(a, 0);
    }

    @After
    public void end() {
        System.out.println("Im done");
    }
}
