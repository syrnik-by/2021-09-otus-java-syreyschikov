public class CashCellImpl implements CashCell {
    public CashCellImpl(CashType cashType, int cashCount) throws Exception {
        if (cashCount >= 0)
            this.cashCount = cashCount;
        else
            throw new Exception("Число банкнот не может быть меньше 0");
        this.cashType = cashType;
    }

    private int cashCount;
    private CashType cashType;

    @Override
    public int getCashCount() {
        return cashCount;
    }

    @Override
    public void addCash(int cashCount) {
        this.cashCount += cashCount;
    }

    @Override
    public void removeCash(int cashCount) throws Exception {
        if (this.cashCount >= cashCount)
            this.cashCount -= cashCount;
        else
            throw new Exception("Число банкнот в ячейке меньше запрашиваемого");
    }

    @Override
    public CashType getCashType() {
        return cashType;
    }
}
