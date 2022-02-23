import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        HashMap<CashType, Integer> money = new HashMap<>();
        money.put(CashType.Coin5000, 1);
        money.put(CashType.Coin1000, 20);
        money.put(CashType.Coin200, 5);
        money.put(CashType.Coin100, 4);
        money.put(CashType.Coin50, 5);
        CurrentBankomat atm = new CurrentBankomat(money);
        System.out.println(atm.getBalance());
        System.out.println(atm.getMoney(10500));
        System.out.println(atm.getBalance());
        System.out.println(atm.getMoney(15200));
        System.out.println(atm.getBalance());
        System.out.println(atm.getMoney(450));
        System.out.println(atm.getBalance());
    }
}
