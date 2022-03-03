public enum CashType implements Comparable<CashType> {
    Coin50(50),
    Coin100(100),
    Coin200(200),
    Coin500(500),
    Coin1000(1000),
    Coin2000(2000),
    Coin5000(5000);
    public final int coin;

    private CashType(int coin) {
        this.coin = coin;
    }
}
