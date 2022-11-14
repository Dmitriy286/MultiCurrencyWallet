package models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Wallet {
    private int userId;
    Map<Currency, Integer> currenciesAmountMap;

    public Wallet(int userId) {
        this.userId = userId;
        this.currenciesAmountMap = new LinkedHashMap<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Currency, Integer> getCurrenciesAmountMap() {
        return currenciesAmountMap;
    }

    public void setCurrenciesAmountMap(Map<Currency, Integer> currenciesAmountMap) {
        this.currenciesAmountMap = currenciesAmountMap;
    }

    public void addCurrency(String name) {

    }

    public void deposit(int amount) {
        Currency first = this.getCurrenciesAmountMap()
                .keySet()
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        int currentSum = this.getCurrenciesAmountMap().get(first);
        this.getCurrenciesAmountMap().put(first, currentSum + amount);

    }

    public void deposit(int amount, Currency currency) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            int currentSum = this.getCurrenciesAmountMap().get(currency);
            this.getCurrenciesAmountMap().put(currency, currentSum + amount);
        } else {
            this.getCurrenciesAmountMap().put(currency, amount);
        }

    }

    public void withdraw() {
        //todo аналогично deposit
    }

    public void showTotal() {

    }

    public void showTotal(String currencyName) {

    }

    //этот для метода show balance
    @Override
    public String toString() {
        return this.getCurrenciesAmountMap().toString();
    }
}
