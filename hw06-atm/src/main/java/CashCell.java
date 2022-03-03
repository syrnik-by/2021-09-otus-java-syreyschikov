public interface CashCell {
    int getCashCount();

    void addCash(int cashCount);

    void removeCash(int cashCount) throws Exception;

    CashType getCashType();
}
