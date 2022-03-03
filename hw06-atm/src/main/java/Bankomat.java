import java.util.HashMap;

public interface Bankomat {
    void addMoney(HashMap<CashType, Integer> money);

    HashMap<CashType, Integer> getMoney(int money) throws Exception;

    int getBalance();
}
