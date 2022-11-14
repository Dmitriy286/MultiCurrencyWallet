package models;

import java.util.*;

public class Wallet {
    private static int idCount = 1;
    private static List<Wallet> walletList;
    private final int walletId;
    private final String userName;
    Map<Currency, Integer> currenciesAmountMap;

    public static List<Wallet> getWalletList() {
        return walletList;
    }

    //todo проверка на наличие кошелька с таким юзернеймом
    public Wallet(String userName) {
        this.walletId = idCount;
        idCount++;
        if (walletList == null) {
            walletList = new ArrayList<>();
        }
        walletList.add(this);
        this.userName = userName;
        this.currenciesAmountMap = new LinkedHashMap<>();
    }

    public int getWalletId() {
        return walletId;
    }

    public String getUserName() {
        return userName;
    }

    public Map<Currency, Integer> getCurrenciesAmountMap() {
        return currenciesAmountMap;
    }

    //todo возможно надо удалить этот сеттер
    public void setCurrenciesAmountMap(Map<Currency, Integer> currenciesAmountMap) {
        this.currenciesAmountMap = currenciesAmountMap;
    }


    public void addCurrency(Currency currency) {
        this.getCurrenciesAmountMap().put(currency, 0);
    }

    private Currency lookForDefaultCurrency() {
        Currency defaultCurrency = this.getCurrenciesAmountMap()
                .keySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    System.out.println("There are no currencies in the current wallet");
                    return new RuntimeException();
                });

        return defaultCurrency;
    }

    public void deposit(int amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        int currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        this.getCurrenciesAmountMap().put(defaultCurrency, currentSum + amount);
    }

    public void deposit(int amount, Currency currency) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            int currentSum = this.getCurrenciesAmountMap().get(currency);
            this.getCurrenciesAmountMap().put(currency, currentSum + amount);
        } else {
            this.getCurrenciesAmountMap().put(currency, amount);
        }
    }

    public void withdraw(int amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        int currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        if (currentSum == 0 || currentSum <= amount) {
            this.getCurrenciesAmountMap().put(defaultCurrency, 0);
        } else {
            this.getCurrenciesAmountMap().put(defaultCurrency, currentSum - amount);
        }
    }

    public void withdraw(int amount, Currency currency) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            int currentSum = this.getCurrenciesAmountMap().get(currency);
            if (currentSum == 0 || currentSum <= amount) {
                this.getCurrenciesAmountMap().put(currency, 0);
            } else {
                this.getCurrenciesAmountMap().put(currency, currentSum - amount);
            }
        } else {
            System.out.println("You have tried to withdraw the " + currency + " amount. " +
                    "There is no such currency in the wallet.");
        }
    }
//todo добавить цвета для текста
    public void showTotal() {

    }

    public void showTotal(String currencyName) {

    }

    @Override
    public String toString() {
        return "\n" + "WalletId: " + this.walletId + "\n" +
                "userName: '" + this.userName + '\'' + "\n" +
                "currenciesAmountMap: " + this.currenciesAmountMap +
                '}';
    }
}
