import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentBankomat implements Bankomat {

    private List<CashCellImpl> depository = new ArrayList<>();

    public CurrentBankomat() throws Exception {
        for (CashType CashType : CashType.values())
            depository.add(new CashCellImpl(CashType, 0));
    }

    public CurrentBankomat(HashMap<CashType, Integer> money) throws Exception {
        for (CashType CashType : CashType.values())
            depository.add(new CashCellImpl(CashType, 0));
        addMoney(money);
    }

    @Override
    // добавить пачку купюр
    public void addMoney(HashMap<CashType, Integer> money) {
        for (CashType CashType : money.keySet()) {
            addCash(CashType, money.get(CashType));
        }
    }

    @Override
    // выдать пачку купюр
    public HashMap<CashType, Integer> getMoney(int money) throws Exception {
        if (getBalance() < money)
            throw new Exception("Суммарный баланс меньше запрашиваемой суммы");
        if (money % getMinCashType().coin != 0)
            throw new Exception("Запрашиваемая сумма не кратна минимальному номиналу банкнот");
        HashMap<CashType, Integer> result = new HashMap<>();
        CashType neededCashType = getMaxCashType();
        int residue = money;
        for (CashType cashType : CashType.values()) {
            int cashCountNeeded = residue / neededCashType.coin;
            int cashCountExist = getCashCountFromCashCell(neededCashType);
            if (cashCountNeeded > 0 && cashCountExist > 0) {
                if (cashCountNeeded >= cashCountExist) {
                    result.put(neededCashType, cashCountExist);
                    residue = residue - (cashCountExist * neededCashType.coin);
                } else {
                    result.put(neededCashType, cashCountNeeded);
                    residue = residue - (cashCountNeeded * neededCashType.coin);
                }
            }
            neededCashType = getLessCashType(neededCashType);
        }
        if (residue > 0)
            throw new Exception("Невозможно выдать запрашиваемую сумму");
        else
            for (CashType CashType : result.keySet())
                removeCash(CashType, result.get(CashType));
        return result;
    }

    @Override
    /* получить суммарный баланс */
    public int getBalance() {
        int result = 0;
        for (CashCellImpl cashCellImpl : depository) {
            result += cashCellImpl.getCashCount() * cashCellImpl.getCashType().coin;
        }
        return result;
    }

    private void addCash(CashType CashType, int count) {
        for (CashCellImpl cashCellImpl : depository) {
            if (cashCellImpl.getCashType().equals(CashType)) {
                cashCellImpl.addCash(count);
            }
        }
    }

    private void removeCash(CashType CashType, int count) throws Exception {
        for (CashCellImpl cashCellImpl : depository) {
            if (cashCellImpl.getCashType().equals(CashType)) {
                cashCellImpl.removeCash(count);
            }
        }
    }

    private CashType getMaxCashType() {
        CashType maxCashType = depository.get(0).getCashType();
        for (int i = 1; i < depository.size(); i++) {
            if (depository.get(i).getCashType().coin > maxCashType.coin)
                maxCashType = depository.get(i).getCashType();
        }
        return maxCashType;
    }

    private CashType getMinCashType() {
        CashType minCashType = depository.get(0).getCashType();
        for (int i = 1; i < depository.size(); i++) {
            if (depository.get(i).getCashType().coin < minCashType.coin)
                minCashType = depository.get(i).getCashType();
        }
        return minCashType;
    }

    private CashType getLessCashType(CashType cashType) {
        CashType lessCashType = getMinCashType();
        for (CashCellImpl cashCellImpl : depository) {
            if (cashCellImpl.getCashType().coin < cashType.coin &&
                    cashCellImpl.getCashType().coin > lessCashType.coin)
                lessCashType = cashCellImpl.getCashType();
        }
        return lessCashType;
    }

    private int getCashCountFromCashCell(CashType cashType) {
        for (CashCellImpl cashCellImpl : depository) {
            if (cashCellImpl.getCashType().equals(cashType))
                return cashCellImpl.getCashCount();
        }
        return 0;
    }
}
