import com.google.common.base.Throwables;

public class HelloOtus {
    public static void main(String[] args) {
        try {
            double a = 13 / 0;
        } catch (Throwable e) {
            Throwables.getCausalChain(e);
        }
    }
}
